package eus.ehu.directorio;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;

import eus.ehu.directorio.graphdb.Util;


public class Directorio2GRAPHDB {

	public static void main(String[] args) {
		String urlGraphDB = DIRECTORIO2GRAPHDBConfig.urlGraphDB;
		String graphDBNORArepoName = DIRECTORIO2GRAPHDBConfig.graphDBDIRECTORIOrepoName;
		String NORANamedGraphURI = DIRECTORIO2GRAPHDBConfig.DIRECTORIONamedGraphURI;
		
		RepositoryManager repositoryManager = new RemoteRepositoryManager(urlGraphDB);
		Repository repository = repositoryManager.getRepository(graphDBNORArepoName);
		RepositoryConnection repositoryConnection = repository.getConnection();
		
		if(DIRECTORIO2GRAPHDBConfig.clearGraph) {
			Util.clearGraph(NORANamedGraphURI, repositoryConnection);
		}

	}

}
