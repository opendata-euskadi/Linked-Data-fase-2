package eus.ehu.directorio;

import java.io.IOException;

import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.eclipse.rdf4j.repository.manager.RepositoryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eus.ehu.directorio.graphdb.Util;
import eus.ehu.directorio.json.Person;
import eus.ehu.directorio.json.JSONParser;
import eus.ehu.directorio.json.People;
import eus.ehu.directorio.json.Person;
import eus.ehu.directorio.uris.DIRECTORIOBaseURIs;
import eus.ehu.directorio.uris.PersonURIs;

public class Directorio2GRAPHDB {
	
	private static Logger logger = LoggerFactory.getLogger(Directorio2GRAPHDB.class);

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
		for (;; itemAt += 10) {
			try {
				People people = (new JSONParser()).parsePeople(DIRECTORIO2GRAPHDBConfig.DIRECTORIO_API_PEOPLE + String.valueOf(itemAt));
				for (Person person : people.pageItems) {
					Person full_person = (new JSONParser()).parsePerson(DIRECTORIO2GRAPHDBConfig.DIRECTORIO_API_PERSON + person.oid);	
					String personURI = DIRECTORIOBaseURIs.PERSON.getURI() + full_person.oid;
					Util.addIRITriple(personURI, RDF.TYPE.stringValue(), PersonURIs.Person.getURI(), namedGraphURI, repositoryConnection);
					Util.addLiteralTriple(personURI, PersonURIs.birthName.getURI(), full_person.name, namedGraphURI, repositoryConnection);
				}
			}
			catch (IOException e) {
				logger.info("Pages out of range: " + e.getMessage());
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
