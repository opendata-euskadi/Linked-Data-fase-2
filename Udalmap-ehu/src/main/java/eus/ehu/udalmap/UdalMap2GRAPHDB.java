package eus.ehu.udalmap;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import eus.ehu.udalmap.graphdb.Util;
import eus.ehu.udalmap.json.IndicadorURL;
import eus.ehu.udalmap.json.Indice;
import eus.ehu.udalmap.json.Valor;
import eus.ehu.udalmap.json.Valores;

public class UdalMap2GRAPHDB {
	
	private static Logger logger = LoggerFactory.getLogger(UdalMap2GRAPHDB.class);
	
	static String urlGraphDB = UDALMAP2GRAPHDBConfig.urlGraphDB;
	static String graphDBrepoName = UDALMAP2GRAPHDBConfig.graphDBUDALMAPrepoName;
	static String namedGraphURI = UDALMAP2GRAPHDBConfig.UDALMAPNamedGraphURI;

    static Util util = new Util();
    
	public static void main(String[] args) throws IOException {
		
		RemoteRepositoryManager repositoryManager = new RemoteRepositoryManager(urlGraphDB);
		repositoryManager.setUsernameAndPassword(UDALMAP2GRAPHDBConfig.graphDBUser, UDALMAP2GRAPHDBConfig.graphDBPassword);		
		Repository repository = repositoryManager.getRepository(graphDBrepoName);
		RepositoryConnection repositoryConnection = repository.getConnection();
		
		if(UDALMAP2GRAPHDBConfig.clearGraph) {
			util.clearGraph(namedGraphURI, repositoryConnection);
		}
		String indicadores = getJSONStringFromURL(UDALMAP2GRAPHDBConfig.URLIndiceIndicadores);
		String jsonIndicadores = indicadores.replace("jsonCallback(", "{\"indicadores\":").replace(");", "}");
		Gson gson = new Gson();
		Indice index = gson.fromJson(jsonIndicadores, Indice.class);
		for (IndicadorURL indicadorurl : index.indicadores) {
			String valoresIndicador = getJSONStringFromURL(indicadorurl.url); 
			
			// El valor 01100 etc siempre cabia asi que lo parseo a mano
			
			
			
			
			
//			String jsonValoresIndicador = valoresIndicador.replace("jsonCallback(", "{\"valores\":").replace(");", "}");
//			Valores jsonValores = gson.fromJson(jsonValoresIndicador, Valores.class);
//			for (Valor valor : jsonValores.valores) {
//				if (valor.title != null) {
//					logger.info(valor.title);
//				}
//			}
			
			
			
//			logger.info(jsonValores.valores.get(0).title);
//			util.addIRITriple(indicadorurl.url, RDF.TYPE.stringValue(), "http://example.com/uri", namedGraphURI, repositoryConnection);
		}
		
		
		

        
		FileOutputStream output = new FileOutputStream(UDALMAP2GRAPHDBConfig.RDFfileBackupPath);
		util.flushModel(output);
	}
	
	private static String getJSONStringFromURL (String url) {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);               
                }

            } catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
                response.close();
            }
        } catch (ClientProtocolException e1) {
        	logger.error(e1.getMessage());
		} catch (IOException e1) {
			logger.error(e1.getMessage());
		} finally {
            try {
				httpClient.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
        }
        return result;
	}
}
