package eus.ehu.directorio;

import java.io.IOException;

import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;

import eus.ehu.directorio.graphdb.Util;
import eus.ehu.directorio.json.Person;
import eus.ehu.directorio.json.JSONParser;
import eus.ehu.directorio.json.People;
import eus.ehu.directorio.json.Person;
import eus.ehu.directorio.uris.DIRECTORIOBaseURIs;
import eus.ehu.directorio.uris.PersonURIs;

public class Directorio2GRAPHDB {

	public static void main(String[] args) throws IOException {
		String urlGraphDB = DIRECTORIO2GRAPHDBConfig.urlGraphDB;
		String graphDBrepoName = DIRECTORIO2GRAPHDBConfig.graphDBDIRECTORIOrepoName;
		String namedGraphURI = DIRECTORIO2GRAPHDBConfig.DIRECTORIONamedGraphURI;
		
		RepositoryManager repositoryManager = new RemoteRepositoryManager(urlGraphDB);
		Repository repository = repositoryManager.getRepository(graphDBrepoName);
		RepositoryConnection repositoryConnection = repository.getConnection();
		
		if(DIRECTORIO2GRAPHDBConfig.clearGraph) {
			Util.clearGraph(namedGraphURI, repositoryConnection);
		}
		
		int itemAt = 0;
		for (itemAt = 0;; itemAt += 10) {
			try {
				People people = (new JSONParser()).parsePeople("https://api.euskadi.eus/directory/people?fromItemAt=" + String.valueOf(itemAt));
				for (Person person : people.pageItems) {
					System.out.println(person.oid);
					Person full_person = (new JSONParser()).parsePerson("https://api.euskadi.eus/directory/people/person/" + person.oid);	
					System.out.println(full_person.oid);
					System.out.println(full_person.name);
				}
			}
			catch (Exception e) {
				System.out.println("Pages out of range: " + e.getMessage());
				break;
			}
		}
		
				

		
		
		
		
//		AccountablePerson accountableperson = (new JSONParser()).parseAccountablePerson();
//		String accountablepersonURI = DIRECTORIOBaseURIs.PERSON.getURI() + accountableperson.oid;
//		Util.addIRITriple(accountablepersonURI, RDF.TYPE.stringValue(), PersonURIs.Person.getURI(), namedGraphURI, repositoryConnection);
//		Util.addLiteralTriple(accountablepersonURI, PersonURIs.birthName.getURI(), accountableperson.name, namedGraphURI,repositoryConnection);
		
		
		
//		Persons persons = (new JSONParser()).parsePersons();
//		for (Person person : persons.persons) {
//			System.out.println(person.id + person.description);
//			Util.addLiteralTripleLang(DIRECTORIOBaseURIs.PERSON.getURI() + person.id, RDFS.COMMENT.stringValue(), person.description, "es", namedGraphURI,
//					repositoryConnection);
//			Util.addIRITriple(DIRECTORIOBaseURIs.PERSON.getURI() + person.id, RDF.TYPE.stringValue(), PersonURIs.Person.getURI(), namedGraphURI, repositoryConnection);
//		}
	}
}
