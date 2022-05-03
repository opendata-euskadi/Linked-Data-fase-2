package eus.ehu.directorio.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JSONParser {

	public AccountablePerson parseAccountablePerson () throws JsonSyntaxException, IOException {
		Gson gson = new Gson ();
		AccountablePerson accountableperson = gson.fromJson(JSON2string (), AccountablePerson.class);
		return accountableperson;
	}
	
	public Persons parsePersons () throws JsonSyntaxException, IOException {
		Gson gson = new Gson();
		Persons persons = gson.fromJson(JSON2string (), Persons.class);
		return persons;
	}
	private  String JSON2string () throws IOException {
		StringBuilder builder = new StringBuilder();		
//		BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/persons.json")));
		BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/7804BBD9-CDBE-4E07-B16A-F9D08287521C.json")));
		String line = null;
		while ((line = reader.readLine()) != null) {
		    builder.append(line);
		}
		reader.close();
		return builder.toString();
	}
}
