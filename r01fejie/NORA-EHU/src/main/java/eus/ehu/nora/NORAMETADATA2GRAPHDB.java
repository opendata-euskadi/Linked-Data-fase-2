package eus.ehu.nora;

import java.io.InputStream;

import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;
import org.eclipse.rdf4j.rio.RDFFormat;

import eus.ehu.nora.graphdb.Util;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NORAMETADATA2GRAPHDB {

	public static void main(String[] args) {
		
		String urlGraphDB = NORA2GRAPHDBConfig.urlGraphDB;
		System.out.println(urlGraphDB);
//		log.info("Loading metadata file into GraphDB ... ");
//		log.info("Add metadata to Named Graph");
//		
//		log.info("Connecting to GraphDB ... ");
//		RepositoryManager repositoryManager = new RemoteRepositoryManager(urlGraphDB);
//		Repository repository = repositoryManager.getRepository(graphDBNORArepoName);
//		RepositoryConnection repositoryConnection = repository.getConnection();
//		
//		log.info("Clear former NORA graph");
//		Util.clearGraph(NORANamedGraphURI, repositoryConnection);
//		
//		InputStream input = NORA2GRAPHDB.class.getResourceAsStream("/" + MetadataFile);
//		
//		repositoryConnection.add(input, RDFFormat.TURTLE, Values.iri(NORAMetadataGraphURI));
//		
//		log.info("Disconnecting from GraphDB ... ");
//		repositoryConnection.close();
//		repository.shutDown();
//		repositoryManager.shutDown();


	}

}
