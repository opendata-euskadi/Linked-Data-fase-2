package eus.ehu.directorio;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import eus.ehu.directorio.json.PhoneChannel;
import eus.ehu.directorio.json.Relation;
import eus.ehu.directorio.json.AbstractByLang;
import eus.ehu.directorio.json.EmailChannel;
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
import eus.ehu.directorio.uris.GeoURIs;
import eus.ehu.directorio.uris.ESCJRURIs;
import eus.ehu.directorio.uris.NORABaseURIs;

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
//		processPeople ();
//		processEntities ();
		processEquipments ();

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
					
					processDescription(personURI, person.description.get("SPANISH"),"es");
					processDescription(personURI, person.description.get("BASQUE"),"eu");
					
					String cv_link_es = person.curriculum.abstractByLang.get("SPANISH");
					Util.addLiteralTripleLang(personURI, EuskadiURIs.curriculum.getURI(), extract_cv_url(cv_link_es), "es", namedGraphURI, repositoryConnection);
					String cv_link_eu = person.curriculum.abstractByLang.get("BASQUE");
					Util.addLiteralTripleLang(personURI, EuskadiURIs.curriculum.getURI(), extract_cv_url(cv_link_eu), "eu", namedGraphURI, repositoryConnection);
										
					String addrss = person.contactInfo.geoPosition.address;
					if (addrss != null) {
						Util.addLiteralTriple(personURI, SchemaURIs.address.getURI(), addrss.replaceAll("<.*?>", ""), namedGraphURI, repositoryConnection);
					}
					
					processContactInfo (person,personURI);
					
//					processRelations(person, "ENTITY", personURI, SchemaURIs.memberOf.getURI());
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
					processDescription(entityURI, entity.description.get("SPANISH"),"es");
					processDescription(entityURI, entity.description.get("BASQUE"),"eu");
					processContactInfo(entity, entityURI);
										
//					processRelations(entity, "ENTITY", entityURI, "partOf");
//					processRelations(entity, "PERSON", entityURI, "organizationOf");
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
					
					Util.addLiteralTripleLang(equipmentURI, RDFS.LABEL.stringValue(), equipment.name.get("SPANISH"), "es", namedGraphURI, repositoryConnection);
					Util.addLiteralTripleLang(equipmentURI, RDFS.LABEL.stringValue(), equipment.name.get("BASQUE"), "eu", namedGraphURI, repositoryConnection);
					
					Util.addLiteralTripleLang(equipmentURI, OrganizationURIs.identifier.getURI(), equipment.shortName.get("SPANISH"), "es", namedGraphURI, repositoryConnection);
					Util.addLiteralTripleLang(equipmentURI, OrganizationURIs.identifier.getURI(), equipment.shortName.get("BASQUE"), "eu", namedGraphURI, repositoryConnection);
										
					processDescription(equipmentURI, equipment.description.get("SPANISH"),"es");
					processDescription(equipmentURI, equipment.description.get("BASQUE"),"eu");
										
					String portal_oid = equipment.contactInfo.geoPosition.portal.get("oid");
					String street_oid = equipment.contactInfo.geoPosition.street.get("oid");
										
					Util.addIRITriple(
							NORABaseURIs.DOORWAY.getURI() + portal_oid, 
							ESCJRURIs.viaProp.getURI(),
							NORABaseURIs.STREET.getURI() + street_oid, 
							namedGraphURI, 
							repositoryConnection);
										
					processContactInfo(equipment, equipmentURI);
					
//					processRelations(equipment, "ENTITY", equipmentURI, "locationOf");
				}
			}
			catch (IOException e) {
				logger.info("Pages out of range: " + e.getMessage());
				break;
			}
		}

	}
	
//	private static void processRelations (JSONitem jsonitem, String itemtype, String sbj, String predicate) {
//		Iterator <Relation> relationOrderintIterator = jsonitem.relationsOrdering.iterator();
//		while (relationOrderintIterator.hasNext()) {
//			Relation relation = relationOrderintIterator.next();
//			if (relation.relations != null) {
//				if(relation.targetObjType.equals(itemtype)) {
//					Iterator <String> relationValueIterator = relation.relations.iterator();
//					while (relationValueIterator.hasNext()) {
//						String obj = relationValueIterator.next();
//						Util.addIRITriple(sbj, predicate, DIRECTORIOBaseURIs.ENTITY.getURI() + obj, namedGraphURI, repositoryConnection);
//					}
//				}
//			}
//		}
//	}
	
	private static void processContactInfo(JSONitem item, String itemURI) {
		if (item.contactInfo.geoPosition.position2D != null) {
			Util.addLiteralTriple(itemURI, GeoURIs.xETRS89.getURI(), item.contactInfo.geoPosition.position2D.x, namedGraphURI, repositoryConnection);
			Util.addLiteralTriple(itemURI, GeoURIs.yETRS89.getURI(), item.contactInfo.geoPosition.position2D.y, namedGraphURI, repositoryConnection);
		}
		
		if (item.contactInfo.phoneChannels != null) {
			for (PhoneChannel phonechannel : item.contactInfo.phoneChannels) {
				Util.addLiteralTriple(itemURI, SchemaURIs.telephone.getURI(), phonechannel.number, namedGraphURI, repositoryConnection);
			}
		}
		
		if (item.contactInfo.emailChannels != null) {
			for (EmailChannel emailchannel : item.contactInfo.emailChannels) {
				Util.addLiteralTriple(itemURI, SchemaURIs.email.getURI(), emailchannel.addr, namedGraphURI, repositoryConnection);
			}
		}
		
	}
	
	private static void processDescription(String item_uri, String descr, String lang) {
		if (descr != null) {
			Util.addLiteralTripleLang(item_uri, RDFS.COMMENT.stringValue(), descr.replaceAll("<.*?>", ""), lang, namedGraphURI, repositoryConnection);
		}
	}

	private static String extract_cv_url(String cv_link) {
		Pattern p = Pattern.compile("href=\"(.*?)\"", Pattern.DOTALL);
		Matcher m = p.matcher(cv_link);
		if (m.find()) {
			return m.group(1);
		}
		else {
			return null;
		}
	}
}
