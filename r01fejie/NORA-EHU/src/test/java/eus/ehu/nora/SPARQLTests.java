package eus.ehu.nora;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.BooleanQuery;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class SPARQLTests {

	private final static String urlGraphDB = "http://127.0.0.1:7200";
	private final static String graphDBNORArepoName = "NORA";
	private final static String NORANamedGraphURI = "http://id.euskadi.eus/graph/NORA";
	private final static String NORALinksNamedGraphURI = "http://id.euskadi.eus/graph/NORA-links";
	private final static String NORAVocabsNamedGraphURI = "http://id.euskadi.eus/graph/NORA-vocabs";

	private static RepositoryManager repositoryManager;
	private static Repository repository;
	private static RepositoryConnection repositoryConnection;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		repositoryManager = new RemoteRepositoryManager(urlGraphDB);
		repository = repositoryManager.getRepository(graphDBNORArepoName);
		repositoryConnection = repository.getConnection();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		repositoryConnection.close();
		repository.shutDown();
		repositoryManager.shutDown();
	}

	// Upload Linked-Data-fase-2/datasets/NORA/territorio.owl to graph
	// http://id.euskadi.eus/graph/NORA-vocabs for inference to work
	
	// TODO: varios niveles
	@Test
	void subPropInference() {
		TupleQuery tupleQuery = repositoryConnection.prepareTupleQuery(QueryLanguage.SPARQL,
				"PREFIX geosparql: <http://www.opengis.net/ont/geosparql#> " + "SELECT ?country WHERE { "
						+ "<http://id.euskadi.eus/public-sector/urbanism-territory/autonomous_community/16> geosparql:sfWithin ?country . "
						+ "} ");
		
		String expectedCountry = "http://id.euskadi.eus/public-sector/urbanism-territory/country/108";
		String obtainedCountry = "";

		TupleQueryResult result = tupleQuery.evaluate();
		while (result.hasNext()) {
			BindingSet bindingSet = result.next();
			Value valueOfcountry = bindingSet.getValue("country");
			obtainedCountry = valueOfcountry.toString();
		}
		
		assertEquals(expectedCountry, obtainedCountry);
	}

//	@Test
//	void townNumber() {
//		fail("Not yet implemented");
//	}

}
