package eus.ehu.nora;


import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;


import eus.ehu.nora.entity.Country;
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
import r01f.types.geo.GeoCountry;
import r01f.types.geo.GeoCounty;
import r01f.types.geo.GeoState;
import r01f.types.url.Url;


@Slf4j
public class NORA2GRAPHDB {

	private static final NORAServiceConfig cfg = new NORAServiceConfig(
			Url.from("http://svc.integracion.euskadi.net/ctxweb/t17iApiWS"));

	private final static String urlGraphDB = "http://127.0.0.1:7200";
	private final static String graphDBNORArepoName = "NORA";
	private final static String NORANamedGraphURI = "http://id.euskadi.eus/graph/NORA";
	private final static String NORALinksNamedGraphURI = "http://id.euskadi.eus/graph/NORA-links";
	private final static String NORAVocabsNamedGraphURI = "http://id.euskadi.eus/graph/NORA-vocabs";

	public static void main(String[] args) throws Exception {
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
		(new Country(
				ESADMURIs.PAIS.getURI(),
				NORABaseURIs.COUNTRY.getURI() + spain.getId().asString(),
				spain.getId().asString(),
				spain.getOfficialName(),
				"España",
				"Espainia")
				).add(repositoryConnection, NORANamedGraphURI);
		
		log.info("... Euskal herri nerea ezin zaitut maiteeeee ...");
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
		
//		nora.getServicesForRegions().
		
		
		
		// TODO metadata
		log.info("Add metadata to Named Graph");
		
		log.info("Disconnecting from GraphDB ... ");
		repositoryConnection.close();
		repository.shutDown();
		repositoryManager.shutDown();
	}
}
