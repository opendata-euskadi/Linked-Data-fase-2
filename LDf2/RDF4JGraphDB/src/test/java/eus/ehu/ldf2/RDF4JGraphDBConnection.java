package eus.ehu.ldf2;

import static org.junit.Assert.*;


import static org.eclipse.rdf4j.model.util.Statements.statement;
import static org.eclipse.rdf4j.model.util.Values.iri;
import static org.eclipse.rdf4j.model.util.Values.literal;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;


import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RDF4JGraphDBConnection {

	@Test
	public void testRemoteRepo() {
		RepositoryManager repositoryManager = new RemoteRepositoryManager("http://127.0.0.1:7200");
		
		// Get the repository from repository manager
		Repository repository = repositoryManager.getRepository("Test-RDF4J");
		
//		http://10.107.17.182:7200/repositories/Test-RDF4J

		// Open a connection to this repository
		RepositoryConnection repositoryConnection = repository.getConnection();
		
		// Create statement (Triple)
		ValueFactory factory = SimpleValueFactory.getInstance();
		IRI bob = iri(factory, "http://example.org/bob");
		IRI name = iri(factory,"http://example.org/name");
		Literal bobsName = literal(factory, "Bob");
		Statement nameStatement = statement(factory, bob, name, bobsName);




		// Shutdown connection, repository and manager
		repositoryConnection.close();
		repository.shutDown();
		repositoryManager.shutDown();

	}

}
