package eus.ehu.udalmap;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class UdalMap2GRAPHDB {

	public static void main(String[] args) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet("https://www.opendata.euskadi.eus/contenidos/estadistica/udalmap_grupo_m/es_def/adjuntos/indice.json");
            CloseableHttpResponse response = httpClient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    System.out.println(result);
                    // Remove
                    // "jsonCallback("
                    // ");"

            		Gson gson = new Gson();
//            		gson.fromJson(result, CLASSINDICE);
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
	}
}
