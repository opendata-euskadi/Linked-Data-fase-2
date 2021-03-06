package eus.ehu.directorio.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JSONParser {

	public JSONCollection parseJSONCollection (String url) throws JsonSyntaxException, IOException {
		Gson gson = new Gson();
		return gson.fromJson(JSONfromURL2string(url), JSONCollection.class);
	}
	
	public JSONitem parseJSONItem (String url, JSONitem json_item_type) throws JsonSyntaxException, IOException {
		Gson gson = new Gson();
		return gson.fromJson(JSONfromURL2string(url), json_item_type.getClass());
	}
		
	private  String JSONfromURL2string (String url) throws IOException {
		StringBuilder builder = new StringBuilder();		
		URL personURL = new URL(url);
        BufferedReader reader;
		reader = new BufferedReader(new InputStreamReader(personURL.openStream()));
		String line = null;
		while ((line = reader.readLine()) != null) {
		    builder.append(line);
		}
		reader.close();
		return builder.toString();
	}
}


