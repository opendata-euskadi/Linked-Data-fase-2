package eus.ehu.nora;


import java.io.InputStream;
import java.util.Collection;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;
import org.eclipse.rdf4j.rio.RDFFormat;

import eus.ehu.nora.entity.GeoNamesADMDEntity;
import eus.ehu.nora.entity.Municipality;
import eus.ehu.nora.entity.County;
import eus.ehu.nora.entity.State;
import eus.ehu.nora.graphdb.Util;
import eus.ehu.nora.uris.ESADMURIs;
import eus.ehu.nora.uris.NORABaseURIs;

// Programa para convertir datos de NORA a RDF y subirlos a GraphDB, incluyendo enlaces

// Programa muy mejorable, se añade aquí simplemente para demostrar el proceso

// NOTA IMPORTANTE: asume que los IDs de NORA son constantes!

// Para poner GraphDB en funcionamiento, ejecutar "docker-compose up" en graphdb-silk-docker/

import lombok.extern.slf4j.Slf4j;
import r01f.ejie.nora.NORAGeoIDs;
import r01f.ejie.nora.NORAService;
import r01f.ejie.nora.NORAServiceConfig;
import r01f.locale.Language;
import r01f.types.geo.GeoCountry;
import r01f.types.geo.GeoCounty;
import r01f.types.geo.GeoMunicipality;
import r01f.types.geo.GeoRegion;
import r01f.types.geo.GeoState;
import r01f.types.url.Url;


@Slf4j
public class NORA2GRAPHDB {

	private static final NORAServiceConfig cfg = new NORAServiceConfig(
			Url.from("http://svc.integracion.euskadi.net/ctxweb/t17iApiWS"));



	public static void main(String[] args) throws Exception {
		// Config
		String urlGraphDB = NORA2GRAPHDBConfig.urlGraphDB;
		String graphDBNORArepoName = NORA2GRAPHDBConfig.graphDBNORArepoName;
		String NORANamedGraphURI = NORA2GRAPHDBConfig.NORANamedGraphURI;
		
		log.info("Connecting to GraphDB ... ");
		RepositoryManager repositoryManager = new RemoteRepositoryManager(urlGraphDB);
		Repository repository = repositoryManager.getRepository(graphDBNORArepoName);
		RepositoryConnection repositoryConnection = repository.getConnection();
		
		log.info("Clear former NORA graph");
		Util.clearGraph(NORANamedGraphURI, repositoryConnection);

		log.info("Connecting to NORA ... ");
		NORAService nora = new NORAService(cfg);
		
		log.info("Spain");
		GeoCountry spain = nora.getServicesForCountries().getCountry(NORAGeoIDs.SPAIN);
		log.info(spain.getOfficialName());
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
						
		log.info("Disconnecting from GraphDB ... ");
		repositoryConnection.close();
		repository.shutDown();
		repositoryManager.shutDown();
	}
	
	private static void processTowns (GeoCounty county, RepositoryConnection repositoryConnection, NORAService nora, String NORANamedGraphURI) {
		Collection <GeoMunicipality> municipalitiesOfAraba = nora.getServicesForMunicipalities().getMunicipalitiesOf(NORAGeoIDs.EUSKADI, county.getId());
		for(GeoMunicipality muni : municipalitiesOfAraba) {
			if(muni.getPosition2D() != null) {
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
					muni.getPosition2D().getX(), 
					muni.getPosition2D().getY())
					).add(repositoryConnection, NORANamedGraphURI);
			}
			// e.g. Badaiako mendizerra/Sierra Brava de Badaya
			else {
				log.info(muni.getOfficialName() + " lacks 2D position! ");
			}
		}
	}
}