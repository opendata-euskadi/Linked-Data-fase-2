package eus.ehu.nora;

import java.util.Collection;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;

import eus.ehu.nora.entity.GeoNamesADMDEntity;
import eus.ehu.nora.entity.Locality;
import eus.ehu.nora.entity.Municipality;
import eus.ehu.nora.entity.Portal;
import eus.ehu.nora.entity.County;
import eus.ehu.nora.entity.State;
import eus.ehu.nora.entity.Street;
import eus.ehu.nora.graphdb.Util;
import eus.ehu.nora.uris.ESADMURIs;
import eus.ehu.nora.uris.ESCJRURIs;
import eus.ehu.nora.uris.EuskadiURIs;
import eus.ehu.nora.uris.NORABaseURIs;

import lombok.extern.slf4j.Slf4j;
import r01f.ejie.nora.NORAGeoIDs;
import r01f.ejie.nora.NORAService;
import r01f.ejie.nora.NORAServiceConfig;
import r01f.locale.Language;
import r01f.types.geo.GeoCountry;
import r01f.types.geo.GeoCounty;
import r01f.types.geo.GeoLocality;
import r01f.types.geo.GeoMunicipality;
import r01f.types.geo.GeoPortal;
import r01f.types.geo.GeoState;
import r01f.types.geo.GeoStreet;
import r01f.types.url.Url;

// Programa para convertir datos de NORA a RDF y subirlos a GraphDB, incluyendo enlaces

// Programa muy mejorable, se añade aquí simplemente para demostrar el proceso

// NOTA IMPORTANTE: asume que los IDs de NORA son constantes!

// Para poner GraphDB en funcionamiento, ejecutar "docker-compose up" en graphdb-silk-docker/

@Slf4j
public class NORA2GRAPHDB {

	private static final NORAServiceConfig cfg = new NORAServiceConfig(
			Url.from("http://svc.integracion.euskadi.net/ctxweb/t17iApiWS"));

	public static void main(String[] args) throws Exception {
		// Config
		String urlGraphDB = NORA2GRAPHDBConfig.urlGraphDB;
		String graphDBNORArepoName = NORA2GRAPHDBConfig.graphDBNORArepoName;
		String NORANamedGraphURI = NORA2GRAPHDBConfig.NORANamedGraphURI;
		
		RepositoryManager repositoryManager = new RemoteRepositoryManager(urlGraphDB);
		Repository repository = repositoryManager.getRepository(graphDBNORArepoName);
		RepositoryConnection repositoryConnection = repository.getConnection();
		
		if(NORA2GRAPHDBConfig.clearGraph) {
			log.info("Clear former NORA graph");
			Util.clearGraph(NORANamedGraphURI, repositoryConnection);
		}

		log.info("Connecting to NORA ... ");
		NORAService nora = new NORAService(cfg);
		
		log.info("Spain");
		GeoCountry spain = nora.getServicesForCountries().getCountry(NORAGeoIDs.SPAIN);
		(new GeoNamesADMDEntity(
				ESADMURIs.PAIS.getURI(),
				NORABaseURIs.COUNTRY.getURI() + spain.getId().asString(),
				spain.getId().asString(),
				spain.getOfficialName(),
				"España",
				"Espainia")
				).add(repositoryConnection, NORANamedGraphURI);
		
		log.info("Euskadi");
		GeoState euskadi = nora.getServicesForStates().getState(NORAGeoIDs.EUSKADI); 
		(new State(
				ESADMURIs.COMUNIDAD_AUTONOMA.getURI(),
				NORABaseURIs.AUTONOMOUS_COMMUNITY.getURI() + euskadi.getId().asString(),
				euskadi.getId().asString(),
				euskadi.getOfficialName(),
				"Comunidad Autonomoa del País Vasco",
				"Euskadi",
				spain.getId().asString())
				).add(repositoryConnection, NORANamedGraphURI);
		
		log.info("Araba");
		GeoCounty araba = nora.getServicesForCounties().getCounty(euskadi.getId(), NORAGeoIDs.ARABA);
		(new County(
				ESADMURIs.PROVINCIA.getURI(),
				NORABaseURIs.PROVINCE.getURI() + araba.getId().asString(),
				araba.getId().asString(),
				araba.getOfficialName(),
				"Álava",
				"Araba",
				euskadi.getId().asString())										
				).add(repositoryConnection, NORANamedGraphURI);
		
		processTowns(araba,repositoryConnection,nora,NORANamedGraphURI);
		
		log.info("Bizkaia");
		GeoCounty bizkaia = nora.getServicesForCounties().getCounty(euskadi.getId(), NORAGeoIDs.BIZKAIA);
		(new County(
				ESADMURIs.PROVINCIA.getURI(),
				NORABaseURIs.PROVINCE.getURI() + bizkaia.getId().asString(), 
				bizkaia.getId().asString(),
				bizkaia.getOfficialName(),
				"Vizcaya",
				"Bizkaia",
				euskadi.getId().asString())										
				).add(repositoryConnection, NORANamedGraphURI);
		
		processTowns(bizkaia,repositoryConnection,nora,NORANamedGraphURI);
		
		log.info("Gipuzkoa");
		GeoCounty gipuzkoa = nora.getServicesForCounties().getCounty(euskadi.getId(), NORAGeoIDs.GIPUZKOA);
		(new County(
				ESADMURIs.PROVINCIA.getURI(),
				NORABaseURIs.PROVINCE.getURI() + gipuzkoa.getId().asString(), 
				gipuzkoa.getId().asString(),
				gipuzkoa.getOfficialName(),
				"Guipúzcoa",
				"Gipuzkoa",
				euskadi.getId().asString())										
				).add(repositoryConnection, NORANamedGraphURI);
		
		processTowns(gipuzkoa,repositoryConnection,nora,NORANamedGraphURI);
						
		repositoryConnection.close();
		repository.shutDown();
		repositoryManager.shutDown();
	}
	
	private static void processTowns (GeoCounty county, RepositoryConnection repositoryConnection, NORAService nora, String NORANamedGraphURI) {
		Collection <GeoMunicipality> municipalities = nora.getServicesForMunicipalities().getMunicipalitiesOf(NORAGeoIDs.EUSKADI, county.getId());
		for(GeoMunicipality muni : municipalities) {
			double x = 0.0;
			double y = 0.0;
			// e.g. Badaiako mendizerra/Sierra Brava de Badaya: http://id.euskadi.eus/public-sector/urbanism-territory/municipality/01-501
			if(muni.getPosition2D() != null) {
				x = muni.getPosition2D().getX();
				y = muni.getPosition2D().getY();
			}
			log.info(muni.getOfficialName());
			log.info(muni.getNameIn(Language.SPANISH)); 
			log.info(muni.getNameIn(Language.BASQUE));
			(new Municipality(
				ESADMURIs.MUNICIPIO.getURI(), 
				NORABaseURIs.MUNICIPALITY.getURI() + county.getId().asString() + "-" + muni.getId().asString(), 
				muni.getId().asString(), 
				muni.getOfficialName(),
				muni.getNameIn(Language.SPANISH), 
				muni.getNameIn(Language.BASQUE), 
				county.getId().asString(), 
				x, 
				y)
				).add(repositoryConnection, NORANamedGraphURI);
				processLocalities(muni, repositoryConnection, nora, NORANamedGraphURI);
		}
	}
	
	private static void processLocalities (GeoMunicipality municipality, RepositoryConnection repositoryConnection, NORAService nora, String NORANamedGraphURI) {
		Collection <GeoLocality> localities = nora.getServicesForLocalities().getLocalitiesOf(NORAGeoIDs.EUSKADI, municipality.getCountyId(), municipality.getId());
		for (GeoLocality locality : localities) {
			(new Locality(
					EuskadiURIs.Localidad.getURI(), 
					NORABaseURIs.LOCALITY.getURI() + locality.getId().asString(),
					locality.getId().asString(), 
					locality.getOfficialName(),
					locality.getNameIn(Language.SPANISH), 
					locality.getNameIn(Language.BASQUE), 
					municipality.getId().asString(),
					municipality.getCountyId().asString())
					).add(repositoryConnection, NORANamedGraphURI);
			Collection<GeoStreet> calles = nora.getServicesForStreets().getStreetsOf(NORAGeoIDs.EUSKADI, municipality.getCountyId(), municipality.getId(), locality.getId());
			for (GeoStreet calle : calles) {
				double x = 0.0;
				double y = 0.0;
				if(calle.getPosition2D() != null) {
					x = calle.getPosition2D().getX();
					y = calle.getPosition2D().getY();
				}
				(new Street(
						ESCJRURIs.Via.getURI(), 
						NORABaseURIs.STREET.getURI() + calle.getId().asString(), 
						calle.getId().asString(), 
						calle.getOfficialName(), 
						calle.getNameIn(Language.SPANISH), 
						calle.getNameIn(Language.BASQUE),
						locality.getId().asString(), 
						x, 
						y)
				).add(repositoryConnection, NORANamedGraphURI);
								
//				Collection <GeoPortal> portales = nora.getServicesForPortal().getPortalsOf(NORAGeoIDs.EUSKADI, NORAGeoIDs.BIZKAIA, NORAGeoIDs.BILBAO, locality.getLocalityId(), calle.getId());
//				for (GeoPortal portal : portales) {
//					
//					double x_portal = 0.0;
//					double y_portal = 0.0;
//					if(portal.getPosition2D() != null) {
//						x = portal.getPosition2D().getX();
//						y = portal.getPosition2D().getY();
//					}
//					
//					(new Portal(
//							ESCJRURIs.Portal.getURI(), 
//							NORABaseURIs.DOORWAY.getURI() + portal.getId().asString(), 
//							portal.getId().asString(), 
//							portal.getOfficialName(), 
//							portal.getNameIn(Language.SPANISH), 
//							portal.getNameIn(Language.BASQUE),
//							portal.getId().asString(), 
//							x_portal, 
//							y_portal)
//					).add(repositoryConnection, NORANamedGraphURI);
//					
//				}
			}
		}
	}
}
