package eus.ehu.ldf2;

import static org.junit.Assert.*;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.query.BooleanQuery;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;


import org.junit.Test;

public class RDF4JGraphDBConnection {

	@Test
	public void testAddTriple() {
		RepositoryManager repositoryManager = new RemoteRepositoryManager("http://127.0.0.1:7200");
		
		Repository repository = repositoryManager.getRepository("Test-RDF4J");
		
		RepositoryConnection repositoryConnection = repository.getConnection();
		
		Namespace ex = Values.namespace("ex", "http://example.org/");
		IRI john = Values.iri(ex, "john");
		IRI namedgraph = Values.iri("http://id.euskadi.eus/graph/rdf4j-test");
		
		repositoryConnection.add(john, RDF.TYPE, FOAF.PERSON, namedgraph);
		repositoryConnection.add(john, RDFS.LABEL, Values.literal("John"), namedgraph);
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>");
		queryBuilder.append("PREFIX ex: <http://example.org/>");
		queryBuilder.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>");
		queryBuilder.append("PREFIX foaf: <http://xmlns.com/foaf/0.1/>");
		queryBuilder.append("ASK");
		queryBuilder.append("WHERE { GRAPH <http://id.euskadi.eus/graph/rdf4j-test> {ex:john rdfs:label \"John\" ;");
		queryBuilder.append("rdf:type foaf:Person . }}");
			      
		BooleanQuery query = repositoryConnection.prepareBooleanQuery(QueryLanguage.SPARQL, queryBuilder.toString());
		
		assertTrue(query.evaluate());

		repositoryConnection.close();
		repository.shutDown();
		repositoryManager.shutDown();
	}
}
