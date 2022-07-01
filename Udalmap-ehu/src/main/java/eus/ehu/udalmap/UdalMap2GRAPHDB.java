package eus.ehu.udalmap;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;

import com.google.gson.Gson;

import eus.ehu.udalmap.graphdb.Util;
import eus.ehu.udalmap.json.IndicadorURL;
import eus.ehu.udalmap.json.Indice;

public class UdalMap2GRAPHDB {
	
	static String urlGraphDB = UDALMAP2GRAPHDBConfig.urlGraphDB;
	static String graphDBrepoName = UDALMAP2GRAPHDBConfig.graphDBUDALMAPrepoName;
	static String namedGraphURI = UDALMAP2GRAPHDBConfig.UDALMAPNamedGraphURI;

    static Util util = new Util();
    
	public static void main(String[] args) throws IOException {
		
		RemoteRepositoryManager repositoryManager = new RemoteRepositoryManager(urlGraphDB);
		repositoryManager.setUsernameAndPassword("admin", "root");		
		Repository repository = repositoryManager.getRepository(graphDBrepoName);
		RepositoryConnection repositoryConnection = repository.getConnection();
		
		if(UDALMAP2GRAPHDBConfig.clearGraph) {
			util.clearGraph(namedGraphURI, repositoryConnection);
		}
		
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet("https://www.opendata.euskadi.eus/contenidos/estadistica/udalmap_grupo_m/es_def/adjuntos/indice.json");
            CloseableHttpResponse response = httpClient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    String jsonResult = result.replace("jsonCallback(", "{\"indicadores\":").replace(");", "}");
                    System.out.println();
                    // Remove
                    // "jsonCallback("
                    // ");"

            		Gson gson = new Gson();
            		Indice index = gson.fromJson(jsonResult, Indice.class);
            		for (IndicadorURL indicadorurl : index.indicadores) {
            			System.out.println(indicadorurl.url);
            			util.addIRITriple(indicadorurl.url, RDF.TYPE.stringValue(), "http://example.com/uri", namedGraphURI, repositoryConnection);
            		}
                }

            } catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        
		FileOutputStream output = new FileOutputStream(UDALMAP2GRAPHDBConfig.RDFfileBackupPath);
		util.flushModel(output);
	}
}
