package eus.ehu.nora;

import static org.junit.Assume.assumeNoException;

import java.util.Collection;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;

import com.google.common.collect.Iterables;

import eus.ehu.nora.entity.Country;
import eus.ehu.nora.entity.State;
import eus.ehu.nora.uris.ESADMURIs;

// Programa para convertir datos de NORA a RDF y subirlos a GraphDB, incluyendo enlaces

// Programa muy mejorable, se añade aquí simplemente para demostrar el proceso

// NOTA IMPORTANTE: asume que los IDs de NORA son constantes!

// Para poner GraphDB en funcionamiento, ejecutar "docker-compose up" en graphdb-silk-docker/

import lombok.extern.slf4j.Slf4j;
import r01f.ejie.nora.NORAService;
import r01f.ejie.nora.NORAServiceConfig;
import r01f.types.geo.GeoCountry;
import r01f.types.geo.GeoState;
import r01f.types.geo.GeoOIDs.GeoCountryID;
import r01f.types.geo.GeoOIDs.GeoStateID;
import r01f.types.url.Url;
import r01f.util.types.collections.CollectionUtils;

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
		
		log.info("Drop former Named Graph");
		
		log.info("Connecting to NORA ... ");
		NORAService nora = new NORAService(cfg);
		
		log.info("Spain");
		GeoCountry country = nora.getServicesForCountries().getCountry(GeoCountryID.forId("108"));
		(new Country
				(
				ESADMURIs.PAIS.getURI(),
				country.getId().asString(),
				country.getOfficialName(),
				"España",
				"Espainia")
				).add(repositoryConnection, NORANamedGraphURI);
		
		log.info("... Euskal herri nerea ezin zaitut maiteeeee ...");
		GeoState state = nora.getServicesForStates().getState(GeoStateID.forId("16")); 
		(new State
				(
				ESADMURIs.COMUNIDAD_AUTONOMA.getURI(),
				state.getId().asString(),
				state.getOfficialName(),
				"Comunidad Autonomoa del País Vasco",
				"Euskadi",
				country.getId().asString())
				).add(repositoryConnection, NORANamedGraphURI);
		
		log.info("Add metadata to Named Graph");
		
		log.info("Disconnecting from GraphDB ... ");
		repositoryConnection.close();
		repository.shutDown();
		repositoryManager.shutDown();
	}
}
