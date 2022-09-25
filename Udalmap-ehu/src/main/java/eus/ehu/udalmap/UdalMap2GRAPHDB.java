package eus.ehu.udalmap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.apache.commons.lang3.StringUtils;

import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

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
			
						
			JSONObject valoresIndicadorJSONObject = new JSONObject("{\"datos\":[" + valoresIndicador + "]}");
			JSONArray valoresIndicadorJSONArray = (JSONArray) valoresIndicadorJSONObject.get("datos");
			
			// TODO: usar IDs ej. 01100 para enlazar con NORA!
			
			// indicadores municipales: entity con solo title, municipality con datos
			if(valoresIndicadorJSONArray.length() == 2) {
				JSONObject tituloJSONObject = (JSONObject) valoresIndicadorJSONArray.get(0);
				System.out.println(normalize_string((String)tituloJSONObject.get("title")));
				process_municipality((JSONObject) valoresIndicadorJSONArray.get(1));

			}
			// entity, region, municipality
//			if(valoresIndicadorJSONArray.length() == 3) {
//				System.out.println("Entity: " + valoresIndicadorJSONArray.get(0));
//				System.out.println("Region: " + valoresIndicadorJSONArray.get(1));
//				System.out.println("Municipality: " + valoresIndicadorJSONArray.get(2));
//			}

//			break;
			
//			util.addIRITriple(indicadorurl.url, RDF.TYPE.stringValue(), "http://example.com/uri", namedGraphURI, repositoryConnection);
		}		
		FileOutputStream output = new FileOutputStream(UDALMAP2GRAPHDBConfig.RDFfileBackupPath);
		util.flushModel(output);
	}
	
	private static String normalize_string(String target_string) {
		return StringUtils.stripAccents(
				target_string
				.toLowerCase()
				.replace("indicadores municipales de sostenibilidad: ", "")
				.replaceAll("%", "porcentaje")
				.replaceAll("/", "por")
				.replaceAll("\\.", "")
				.replaceAll("\\(", "")
				.replaceAll("\\)", "")
				.replaceAll("km²", "km2")
				.replaceAll("m²","-")
				.replaceAll("&#x2030;", "")
				.replaceAll("&#x20ac;", "")
				.replaceAll("nº", "")
				.replaceAll(",", "")
				.replaceAll(":", "")
				.replaceAll("=", "")
				.replaceAll("ª", "")
				.trim()
				.strip()
				.replaceAll("\\s","-")
				);
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
	
	private static void process_municipality(JSONObject municipalityJSONObject) {
		Iterator valuesJSONArrayIterator = ((JSONArray) municipalityJSONObject.get("municipality")).iterator();
		while (valuesJSONArrayIterator.hasNext()) {
			JSONObject valueJSONObject = (JSONObject) valuesJSONArrayIterator.next();
			String municipality_id = (String) valueJSONObject.names().get(0);
			// 48001 http://id.euskadi.eus/public-sector/urbanism-territory/municipality/48-001
			JSONObject medicionesJSONObject = valueJSONObject.getJSONObject(municipality_id);
			JSONObject medicionesValoresJSONObject = ((JSONObject) medicionesJSONObject.getJSONArray("values").get(0));
			Iterator medicionesValoresIterator = medicionesValoresJSONObject.keys();
			while(medicionesValoresIterator.hasNext()) {
				System.out.println(medicionesValoresIterator.next() + "--" + medicionesValoresJSONObject.get((String)medicionesValoresIterator.next()));
			}
		}
	}
}
