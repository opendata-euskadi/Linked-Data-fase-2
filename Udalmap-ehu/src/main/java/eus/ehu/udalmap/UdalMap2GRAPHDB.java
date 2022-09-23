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
			System.out.println(indicadorurl.url);
			String valoresIndicador = getJSONStringFromURL(indicadorurl.url); 
//			String jsonValoresIndicador = valoresIndicador.replace("jsonCallback(", "{\"valores\":").replace(");", "}");
			
//			System.out.println(valoresIndicador);
						
			JSONObject valoresIndicadorJSONObject = new JSONObject("{\"datos\":[" + valoresIndicador + "]}");
			JSONArray valoresIndicadorJSONArray = (JSONArray) valoresIndicadorJSONObject.get("datos");
			System.out.println(valoresIndicadorJSONArray.length());
			// entity, municipality
			if(valoresIndicadorJSONArray.length() == 2) {
				System.out.println("Entity: " + valoresIndicadorJSONArray.get(0));
				System.out.println("Municipality: " + valoresIndicadorJSONArray.get(1));
			}
			// entity, region, municipality
			if(valoresIndicadorJSONArray.length() == 3) {
				System.out.println("Entity: " + valoresIndicadorJSONArray.get(0));
				System.out.println("Region: " + valoresIndicadorJSONArray.get(1));
				System.out.println("Municipality: " + valoresIndicadorJSONArray.get(2));
			}

			
//			for (String key : valoresIndicadorJSONObject.keySet()) {
//				System.out.println(key);
//				System.out.println(valoresIndicadorJSONObject.get(key));
//			}
			
//			System.out.println(valoresIndicadorJSONObject.get("title"));
//			System.out.println(valoresIndicadorJSONObject.get("entity"));
			
//			System.out.println(jo.get("title"));
//			System.out.println(jo.get("entity"));
//			System.out.println(jo.get("region"));
//			System.out.println(jo.get("municipality"));

//			break;
			
			
			
			
			
			
			
			
			
			// El valor 01100 etc siempre cambia asi que lo parseo a mano, sobre todo a nivel municipio y region
			
			// TODO: usar IDs ej. 01100 para enlazar con NORA!
			
//			System.out.println(jsonValoresIndicador);
			
//			Valores jsonValores = gson.fromJson(jsonValoresIndicador, Valores.class);
			
//			System.out.println(jsonValores.title);
			
//			for (Valor valor : jsonValores.valores) {
//				if (valor.title != null) {
//					String titulo_medidor = valor.title;
//					logger.info("--" + titulo_medidor);
//					String titulo_medidor_normalizado = normalize_string (titulo_medidor);
//					logger.info("!!!" + titulo_medidor_normalizado + "!!!");
//				}
//				
//				if (valor.entity != null) {
//					for (Map medicion : valor.entity) {
//						String medicion_original = medicion.toString();
//						com.google.gson.internal.LinkedTreeMap
//						System.out.println(medicion.entrySet());
//						Iterator itr = medicion.keySet().iterator();
//						while (itr.hasNext()){
//						    String key = (String) itr.next();
//						    String value = (String) medicion.get(key);
//						 
//						    System.out.println(key + "=" + value);
//						}
						
						
//						!!!!!! La medicion cambia del original, de : a = y quita comillas????
						
						
//						System.out.println(medicion_original);
//						System.out.println(medicion_original.indexOf("="));
//						System.out.println(medicion_original.substring(medicion_original.indexOf("=")+1, medicion_original.length()-1));
//						logger.info();
//					}
//				}
//				
//				if (valor.region != null) {
//					for (Map medicion : valor.region) {
//						logger.info(medicion.toString());
//					}
//				}
				
//				if (valor.municipality != null) {
//					for (Map medicion : valor.municipality) {
//						logger.info(medicion.toString());
//					}
//				}
				
//			}
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
}
