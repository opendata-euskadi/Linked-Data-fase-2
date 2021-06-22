package eus.ehu.nora;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParseException;

import eus.ehu.nora.graphdb.Util;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NORALINKS2GRAPHDB {

	public static void main(String[] args) throws RDFParseException, RepositoryException, IllegalArgumentException, IOException {
		// Config
		String urlGraphDB = NORA2GRAPHDBConfig.urlGraphDB;
		String graphDBNORArepoName = NORA2GRAPHDBConfig.graphDBNORArepoName;
		String NORALinksGraphURI = NORA2GRAPHDBConfig.NORALinksNamedGraphURI;
		String LinksFile = NORA2GRAPHDBConfig.LinksFile;
		
		RepositoryManager repositoryManager = new RemoteRepositoryManager(urlGraphDB);
		Repository repository = repositoryManager.getRepository(graphDBNORArepoName);
		RepositoryConnection repositoryConnection = repository.getConnection();
		
		Util.clearGraph(NORALinksGraphURI, repositoryConnection);
		
		InputStream input = NORAMETADATA2GRAPHDB.class.getResourceAsStream("/" + LinksFile);
		
		repositoryConnection.add(input, RDFFormat.TURTLE, Values.iri(NORALinksGraphURI));
		
		repositoryConnection.close();
		repository.shutDown();
		repositoryManager.shutDown();
		


	}

}
