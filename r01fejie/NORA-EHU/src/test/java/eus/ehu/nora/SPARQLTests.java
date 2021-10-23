package eus.ehu.nora;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
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

	private final static String urlGraphDB = NORA2GRAPHDBConfig.urlGraphDB;
	private final static String graphDBNORArepoName = NORA2GRAPHDBConfig.graphDBNORArepoName;

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

	// Upload the following ontologies to Named Graph http://id.euskadi.eus/graph/NORA-vocabs for inference to work:
	// Linked-Data-fase-2/datasets/NORA/territorio.owl
	// Linked-Data-fase-2/datasets/NORA/nora.ttl

	@Test
	void subPropInference() throws IOException {
		String query = getQueryFromResource("/SubPropInference.rq");
		ArrayList<String> results = execSingleVariableQuery ("country", query);
		
		String expectedCountry = "http://id.euskadi.eus/public-sector/urbanism-territory/country/108";
		String expectedAutonomousComunity = "http://id.euskadi.eus/public-sector/urbanism-territory/autonomous_community/16";
		
		assertTrue(results.contains(expectedCountry));
		assertTrue(results.contains(expectedAutonomousComunity));
	}

	@Test
	void townNumberAraba() throws IOException {
		String query = getQueryFromResource("/CountMunicipiosAraba.rq");
		ArrayList<String> results = execSingleVariableQuery ("count", query);
		assertTrue(results.contains("50"));  // en realidad son 51!
	}
	
	@Test
	void townNumberBizkaia() throws IOException {
		String query = getQueryFromResource("/CountMunicipiosBizkaia.rq");
		ArrayList<String> results = execSingleVariableQuery ("count", query);
		assertTrue(results.contains("112"));  
	}
	
	@Test
	void townNumberGipuzkoa() throws IOException {
		String query = getQueryFromResource("/CountMunicipiosGipuzkoa.rq");
		ArrayList<String> results = execSingleVariableQuery ("count", query);
		assertTrue(results.contains("88")); 
	}
	
	@Test
	void localityNoMunicipality() throws IOException {
		String query = getQueryFromResource("/LocalityNoMunicipality.rq");
		assertFalse(execBooleanQuery(query));
	}
	
	@Test 
	void federatedQuery () {
		
	}
	
	private ArrayList<String> execSingleVariableQuery (String varName, String query){
		ArrayList <String> obtainedResults = new ArrayList<String>();
		TupleQuery tupleQuery = repositoryConnection.prepareTupleQuery(QueryLanguage.SPARQL,query);
		TupleQueryResult result = tupleQuery.evaluate();
		while (result.hasNext()) {
			BindingSet bindingSet = result.next();
			Value valueOfcount = bindingSet.getValue(varName);
			obtainedResults.add(valueOfcount.stringValue());
		}
		return obtainedResults;
	}
	
	private boolean execBooleanQuery (String query) {
		BooleanQuery booleanQuery = repositoryConnection.prepareBooleanQuery(QueryLanguage.SPARQL,query);
		return booleanQuery.evaluate();
	}
	
	private String getQueryFromResource (String queryResourceFileName) throws IOException {
		return IOUtils.toString(this.getClass().getResourceAsStream(queryResourceFileName),"UTF-8");
	}

}
