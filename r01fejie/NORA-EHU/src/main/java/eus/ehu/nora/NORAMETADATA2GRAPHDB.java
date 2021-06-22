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
public class NORAMETADATA2GRAPHDB {

	public static void main(String[] args) throws RDFParseException, RepositoryException, IllegalArgumentException, IOException {
		// Config
		String urlGraphDB = NORA2GRAPHDBConfig.urlGraphDB;
		String graphDBNORArepoName = NORA2GRAPHDBConfig.graphDBNORArepoName;
		String NORAMetadataGraphURI = NORA2GRAPHDBConfig.NORAMetadataGraphURI;
		String MetadataFile = NORA2GRAPHDBConfig.MetadataFile;
		
		RepositoryManager repositoryManager = new RemoteRepositoryManager(urlGraphDB);
		Repository repository = repositoryManager.getRepository(graphDBNORArepoName);
		RepositoryConnection repositoryConnection = repository.getConnection();
		
		Util.clearGraph(NORAMetadataGraphURI, repositoryConnection);
		
		InputStream input = NORAMETADATA2GRAPHDB.class.getResourceAsStream("/" + MetadataFile);
		
		repositoryConnection.add(input, RDFFormat.TURTLE, Values.iri(NORAMetadataGraphURI));
		
		repositoryConnection.close();
		repository.shutDown();
		repositoryManager.shutDown();
	}
}
