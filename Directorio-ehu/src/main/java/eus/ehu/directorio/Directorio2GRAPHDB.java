package eus.ehu.directorio;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

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
import eus.ehu.directorio.json.Relation;
import eus.ehu.directorio.json.Entity;
import eus.ehu.directorio.json.Equipment;
import eus.ehu.directorio.json.JSONCollection;
import eus.ehu.directorio.json.JSONParser;
import eus.ehu.directorio.json.JSONitem;
import eus.ehu.directorio.json.Person;
import eus.ehu.directorio.uris.DIRECTORIOBaseURIs;
import eus.ehu.directorio.uris.EuskadiURIs;
import eus.ehu.directorio.uris.OrganizationURIs;
import eus.ehu.directorio.uris.PersonURIs;
import eus.ehu.directorio.uris.SchemaURIs;

public class Directorio2GRAPHDB {
	
	private static Logger logger = LoggerFactory.getLogger(Directorio2GRAPHDB.class);
	
	static String urlGraphDB = DIRECTORIO2GRAPHDBConfig.urlGraphDB;
	static String graphDBrepoName = DIRECTORIO2GRAPHDBConfig.graphDBDIRECTORIOrepoName;
	static String namedGraphURI = DIRECTORIO2GRAPHDBConfig.DIRECTORIONamedGraphURI;
	static RepositoryManager repositoryManager = new RemoteRepositoryManager(urlGraphDB);
	static Repository repository = repositoryManager.getRepository(graphDBrepoName);
	static RepositoryConnection repositoryConnection = repository.getConnection();

	public static void main(String[] args) throws IOException {		
		if(DIRECTORIO2GRAPHDBConfig.clearGraph) {
			Util.clearGraph(namedGraphURI, repositoryConnection);
		}
		processPeople ();
//		processEntities ();
//		processEquipments ();

	}
	
	private static void processPeople () {
		int itemAt = 0;
		for (;; itemAt += 10) {
			try {
				String api_call_url = DIRECTORIO2GRAPHDBConfig.DIRECTORIO_API_PEOPLE + String.valueOf(itemAt);
				JSONCollection json_collection = (new JSONParser()).parseJSONCollection(api_call_url);
				for (JSONitem item : json_collection.pageItems) {
					Person person = (Person) (new JSONParser()).parseJSONItem(DIRECTORIO2GRAPHDBConfig.DIRECTORIO_API_PERSON + item.oid, new Person());
					String personURI = DIRECTORIOBaseURIs.PERSON.getURI() + person.oid;
					Util.addIRITriple(personURI, RDF.TYPE.stringValue(), PersonURIs.Person.getURI(), namedGraphURI, repositoryConnection);
					Util.addLiteralTriple(personURI, PersonURIs.birthName.getURI(), person.name, namedGraphURI, repositoryConnection);

					Iterator relationOrderintIterator = person.relationsOrdering.iterator();
					while (relationOrderintIterator.hasNext()) {
						Relation relation = (Relation) relationOrderintIterator.next();
						if (relation.relations != null) {
							logger.info(relation.relations.get(0));
							logger.info(relation.targetObjType);
						}
					}
				}
			}
			catch (IOException e) {
				logger.info("Pages out of range: " + e.getMessage());
				break;
			}
		}
	}
	
	private static void processEntities () {
		int itemAt = 0;
		for (;; itemAt += 10) {
			try {
				String api_call_url = DIRECTORIO2GRAPHDBConfig.DIRECTORIO_API_ENTITIES + String.valueOf(itemAt);
				JSONCollection json_collection = (new JSONParser()).parseJSONCollection(api_call_url);
				for (JSONitem item : json_collection.pageItems) {
					Entity entity = (Entity) (new JSONParser()).parseJSONItem(DIRECTORIO2GRAPHDBConfig.DIRECTORIO_API_ENTITY + item.oid, new Entity ());	
					String entityURI = DIRECTORIOBaseURIs.ENTITY.getURI() + entity.oid;
					Util.addIRITriple(entityURI, RDF.TYPE.stringValue(), SchemaURIs.GovernmentOrganization.getURI(), namedGraphURI, repositoryConnection);
					Util.addLiteralTripleLang(entityURI, RDFS.LABEL.stringValue(), entity.name.get("SPANISH"), "es", namedGraphURI, repositoryConnection);
					Util.addLiteralTripleLang(entityURI, RDFS.LABEL.stringValue(), entity.name.get("BASQUE"), "eu", namedGraphURI, repositoryConnection);
					
					Util.addLiteralTripleLang(entityURI, OrganizationURIs.identifier.getURI(), entity.shortName.get("SPANISH"), "es", namedGraphURI, repositoryConnection);
					Util.addLiteralTripleLang(entityURI, OrganizationURIs.identifier.getURI(), entity.shortName.get("BASQUE"), "eu", namedGraphURI, repositoryConnection);
					
					Util.addLiteralTripleLang(entityURI, RDFS.COMMENT.stringValue(), entity.description.get("SPANISH"), "es", namedGraphURI, repositoryConnection);
					Util.addLiteralTripleLang(entityURI, RDFS.COMMENT.stringValue(), entity.description.get("BASQUE"), "eu", namedGraphURI, repositoryConnection);
				}
			}
			catch (IOException e) {
				logger.info("Pages out of range: " + e.getMessage());
				break;
			}
		}
	}
	
	private static void processEquipments () {
		int itemAt = 0;
		for (;; itemAt += 10) {
			try {
				String api_call_url = DIRECTORIO2GRAPHDBConfig.DIRECTORIO_API_EQUIPMENTS + String.valueOf(itemAt);
				JSONCollection json_collection = (new JSONParser()).parseJSONCollection(api_call_url);
				for (JSONitem item : json_collection.pageItems) {
					Equipment equipment = (Equipment) (new JSONParser()).parseJSONItem(DIRECTORIO2GRAPHDBConfig.DIRECTORIO_API_EQUIPMENT + item.oid, new Equipment ());	
					String equipmentURI = DIRECTORIOBaseURIs.EQUIPMENT.getURI() + equipment.oid;
					Util.addIRITriple(equipmentURI, RDF.TYPE.stringValue(), EuskadiURIs.Equipment.getURI(), namedGraphURI, repositoryConnection);
				}
			}
			catch (IOException e) {
				logger.info("Pages out of range: " + e.getMessage());
				break;
			}
		}

	}
}
