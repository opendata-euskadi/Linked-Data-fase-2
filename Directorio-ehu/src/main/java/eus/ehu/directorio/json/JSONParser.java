package eus.ehu.directorio.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JSONParser {

	public People parsePeople (String url) throws JsonSyntaxException, IOException {
		Gson gson = new Gson();
		People people = gson.fromJson(JSONfromURL2string(url), People.class);
		return people;
	}
	
	public Person parsePerson (String url) throws JsonSyntaxException, IOException {
		Gson gson = new Gson ();
		Person accountableperson = gson.fromJson(JSONfromURL2string(url), Person.class);
		return accountableperson;
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


//{
//  "name": "mkyong",
//  "age": 35,
//  "position": [
//    "Founder",
//    "CTO",
//    "Writer"
//  ],
//  "skills": [
//    "java",
//    "python",
//    "node",
//    "kotlin"
//  ],
//  "salary": {
//    "2018": 14000,
//    "2012": 12000,
//    "2010": 10000
//  }
//}
//
//
//
//private String name;
//private int age;
//private String[] position;              
//private List<String> skills;            
//private Map<String, BigDecimal> salary; 
