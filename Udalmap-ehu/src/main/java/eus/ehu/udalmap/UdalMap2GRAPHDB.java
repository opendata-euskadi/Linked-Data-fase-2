package eus.ehu.udalmap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Year;
import java.util.Iterator;

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
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import eus.ehu.udalmap.graphdb.Util;
import eus.ehu.udalmap.json.IndicadorURL;
import eus.ehu.udalmap.json.Indice;
import eus.ehu.udalmap.uris.DataCubeURIs;
import eus.ehu.udalmap.uris.EuskadiURIs;
import eus.ehu.udalmap.uris.UDALMAPBaseURIs;
import eus.ehu.udalmap.uris.WGS84URIS;

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
						
			// indicadores municipales: entity con solo title, municipality con datos
			if(valoresIndicadorJSONArray.length() == 2) {
				JSONObject tituloJSONObject = (JSONObject) valoresIndicadorJSONArray.get(0);
				String titulo_bruto = (String)tituloJSONObject.get("title");
				String URI_indicador = process_indicador_title(titulo_bruto,repositoryConnection);
				process_municipality((JSONObject) valoresIndicadorJSONArray.get(1), URI_indicador, repositoryConnection);

			}
			// entity, region, municipality
			if(valoresIndicadorJSONArray.length() == 3) {
				JSONObject tituloJSONObject = (JSONObject) valoresIndicadorJSONArray.get(0);
				String titulo_bruto = (String)tituloJSONObject.get("title");
				System.out.println(titulo_bruto);
				String URI_indicador = process_indicador_title(titulo_bruto,repositoryConnection);
				process_municipality((JSONObject) valoresIndicadorJSONArray.get(2), URI_indicador, repositoryConnection);
//				System.out.println("Region: " + valoresIndicadorJSONArray.get(1));
			}

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
	
	private static void process_municipality(JSONObject municipalityJSONObject, String URI_indicador, RepositoryConnection repositoryConnection) {
		Iterator valuesJSONArrayIterator = ((JSONArray) municipalityJSONObject.get("municipality")).iterator();
		while (valuesJSONArrayIterator.hasNext()) {
			JSONObject valueJSONObject = (JSONObject) valuesJSONArrayIterator.next();
			String municipality_id = (String) valueJSONObject.names().get(0);
			String proper_municipality_id = municipality_id.substring(0,2) + "-" + municipality_id.substring(2,5);
			// 48001 https://id.euskadi.eus/public-sector/urbanism-territory/municipality/48-001
			JSONObject medicionesJSONObject = valueJSONObject.getJSONObject(municipality_id);
			JSONObject medicionesValoresJSONObject = ((JSONObject) medicionesJSONObject.getJSONArray("values").get(0));
			Iterator medicionesValoresIterator = medicionesValoresJSONObject.keys();
			while(medicionesValoresIterator.hasNext()) {
				String year = (String) medicionesValoresIterator.next();
				Year year_proper = Year.parse(year);
				String URI_medicion = UDALMAPBaseURIs.INDICADORMUNICIPALSOSTENIBILIDAD.getURI() 
									+ URI_indicador.replace(EuskadiURIs.Indicador.getURI(), "") + "-" 
									+ municipality_id + "-"
									+ year + "-" 
									+ medicionesValoresJSONObject.get(year).toString().replace(".", "");
				util.addIRITriple(URI_medicion, RDF.TYPE.stringValue(), URI_indicador, namedGraphURI, repositoryConnection);
				util.addIRITriple(URI_medicion, WGS84URIS.location.getURI(), "https://id.euskadi.eus/public-sector/urbanism-territory/municipality/" + proper_municipality_id, namedGraphURI, repositoryConnection);
				util.addLiteralTriple(URI_medicion, DataCubeURIs.refPeriod.getURI(), year_proper, namedGraphURI, repositoryConnection);
				if(medicionesValoresJSONObject.get(year).getClass() == java.math.BigDecimal.class) {
					Double o = ((BigDecimal)medicionesValoresJSONObject.get(year)).doubleValue();
					util.addLiteralTriple(URI_medicion, DataCubeURIs.obsValue.getURI(), o, namedGraphURI, repositoryConnection);
				}
				else {
					util.addLiteralTriple(URI_medicion, DataCubeURIs.obsValue.getURI(), medicionesValoresJSONObject.get(year).toString(), namedGraphURI, repositoryConnection);
				}
			}
		}
	}
	
	private static String process_indicador_title (String title, RepositoryConnection repositoryConnection) {
		String URI_indicador = EuskadiURIs.Indicador.getURI() + normalize_string(title);
		util.addLiteralTripleLang(URI_indicador, RDFS.LABEL.stringValue(), title, "es", namedGraphURI, repositoryConnection);
		return URI_indicador;
	}
}
