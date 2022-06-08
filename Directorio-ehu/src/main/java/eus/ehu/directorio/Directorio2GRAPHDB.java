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
import eus.ehu.directorio.json.Phone;
import eus.ehu.directorio.json.Link;
import eus.ehu.directorio.json.Email;
import eus.ehu.directorio.json.Email;
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
import jdk.internal.org.jline.utils.Log;
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
					logger.info(DIRECTORIO2GRAPHDBConfig.DIRECTORIO_API_PERSON + item.oid);
					Person person = (Person) (new JSONParser()).parseJSONItem(DIRECTORIO2GRAPHDBConfig.DIRECTORIO_API_PERSON + item.oid, new Person());
					String personURI = DIRECTORIOBaseURIs.PERSON.getURI() + person.oid;
					processBasics(person, personURI, PersonURIs.Person.getURI());
					if (person.description != null) {
						Util.addLiteralTriple(personURI, RDFS.COMMENT.stringValue(), person.description.replaceAll("<.*?>", ""), namedGraphURI, repositoryConnection);
					}
					processContactInfo(person, personURI);
					processGeo(person, personURI);
					if (person.curriculum.summary != null) {
						Util.addLiteralTriple(personURI, EuskadiURIs.curriculum.getURI(), extract_cv_url(person.curriculum.summary), namedGraphURI, repositoryConnection);
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
//					logger.info(DIRECTORIO2GRAPHDBConfig.DIRECTORIO_API_ENTITY + item.oid);
					Entity entity = (Entity) (new JSONParser()).parseJSONItem(DIRECTORIO2GRAPHDBConfig.DIRECTORIO_API_ENTITY + item.oid, new Entity ());
					String entityURI = DIRECTORIOBaseURIs.ENTITY.getURI() + entity.oid;
					processBasics(entity, entityURI, SchemaURIs.GovernmentOrganization.getURI());
					processContactInfo(entity, entityURI);					
					if (entity.geoPosition != null) {
						processGeo (entity, entityURI);
					}
					
					if (entity.competences != null) {
						Util.addLiteralTriple(entityURI, RDFS.COMMENT.stringValue(), entity.competences.replaceAll("<.*?>", ""), namedGraphURI, repositoryConnection);
					}
					
					if (entity.legislature != null) {
						Util.addLiteralTriple(entityURI, EuskadiURIs.legislature.getURI(), entity.legislature, namedGraphURI, repositoryConnection);
					}
					
					if (entity.scope != null) {
						Util.addLiteralTriple(entityURI, EuskadiURIs.scope.getURI(), entity.scope, namedGraphURI, repositoryConnection);
					}
					
					if (entity.publicEntityType != null) {
						Util.addLiteralTriple(entityURI, EuskadiURIs.publicEntityType.getURI(), entity.publicEntityType, namedGraphURI, repositoryConnection);
					}
										
					Util.addIRITriple(entityURI, SchemaURIs.mainEntityOfPage.getURI(),entity._links.mainEntityOfPage, namedGraphURI, repositoryConnection);
					
					if(entity._links.legalFramework != null) {
						int i = 0;
						for (Link weblink : entity._links.legalFramework) {
							String webLinkURI = entityURI + "/webLink/" + i ;
							i++;
							Util.addIRITriple(entityURI, EuskadiURIs.webLink.getURI(), webLinkURI, namedGraphURI, repositoryConnection);
							Util.addLiteralTriple(webLinkURI, SchemaURIs.url.getURI(), weblink.href, namedGraphURI, repositoryConnection);
							Util.addLiteralTriple(webLinkURI, RDFS.COMMENT.stringValue(), weblink.name, namedGraphURI, repositoryConnection);
						}
					}
							
					if(entity._links.entitesAbove != null) {
						for (Link link : entity._links.entitesAbove) {
							Util.addIRITriple(entityURI, SchemaURIs.parentOrganization.getURI(),
									DIRECTORIOBaseURIs.ENTITY.getURI() + link.href.replace("https://api.euskadi.eus/directory/entities/", ""), 
									namedGraphURI, repositoryConnection);
						}
					}
					
					if(entity._links.entitiesBelow != null) {
						for (Link link : entity._links.entitiesBelow) {
							Util.addIRITriple(entityURI, SchemaURIs.subOrganization.getURI(),
									DIRECTORIOBaseURIs.ENTITY.getURI() + link.href.replace("https://api.euskadi.eus/directory/entities/", ""), 
									namedGraphURI, repositoryConnection);
						}
					}
					
					if(entity._links.people != null) {
						for (Link link : entity._links.people) {
							logger.info(entityURI);
							Util.addIRITriple(entityURI, SchemaURIs.member.getURI(),
									DIRECTORIOBaseURIs.PERSON.getURI() + link.href.replace("https://api.euskadi.eus/directory/people/", ""), 
									namedGraphURI, repositoryConnection);
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
	
	private static void processEquipments () {
		int itemAt = 0;
		for (;; itemAt += 10) {
			try {
				String api_call_url = DIRECTORIO2GRAPHDBConfig.DIRECTORIO_API_EQUIPMENTS + String.valueOf(itemAt);
				JSONCollection json_collection = (new JSONParser()).parseJSONCollection(api_call_url);
				for (JSONitem item : json_collection.pageItems) {
					logger.info(DIRECTORIO2GRAPHDBConfig.DIRECTORIO_API_EQUIPMENT + item.oid);
					Equipment equipment = (Equipment) (new JSONParser()).parseJSONItem(DIRECTORIO2GRAPHDBConfig.DIRECTORIO_API_EQUIPMENT + item.oid, new Equipment ());	
					String equipmentURI = DIRECTORIOBaseURIs.EQUIPMENT.getURI() + equipment.oid;
					
					processBasics(equipment, equipmentURI, EuskadiURIs.Equipment.getURI());
					
					if (equipment.description != null) {
						Util.addLiteralTriple(equipmentURI, RDFS.COMMENT.stringValue(), equipment.description.replaceAll("<.*?>", ""), namedGraphURI, repositoryConnection);
					}

					processContactInfo(equipment, equipmentURI);
					if (equipment.geoPosition != null) {
						processGeo (equipment, equipmentURI);
					}
					
					Util.addIRITriple(equipmentURI, SchemaURIs.mainEntityOfPage.getURI(),equipment._links.mainEntityOfPage, namedGraphURI, repositoryConnection);
					
					List <Link> links = equipment._links.entitiesLinks;
					if (links != null) {
						for (Link link : links) {
							Util.addIRITriple(equipmentURI, EuskadiURIs.equipmentOf.getURI(),
									DIRECTORIOBaseURIs.ENTITY.getURI() + link.href.replace("https://api.euskadi.eus/directory/entities/", ""), 
									namedGraphURI, repositoryConnection);
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
	
	private static void processBasics (JSONitem item, String itemURI, String typeURI) {
		Util.addIRITriple(itemURI, RDF.TYPE.stringValue(), typeURI, namedGraphURI, repositoryConnection);
		Util.addLiteralTriple(itemURI, RDFS.LABEL.stringValue(), item.name, namedGraphURI, repositoryConnection);
		Util.addLiteralTriple(itemURI, SchemaURIs.identifier.getURI(), item.id, namedGraphURI, repositoryConnection);
	}
	
	private static void processGeo (JSONitem item, String itemURI) {
		if (item.geoPosition.position2D != null) {
			Util.addLiteralTriple(itemURI, GeoURIs.xETRS89.getURI(), item.geoPosition.position2D.x, namedGraphURI, repositoryConnection);
			Util.addLiteralTriple(itemURI, GeoURIs.yETRS89.getURI(), item.geoPosition.position2D.y, namedGraphURI, repositoryConnection);
		}
		
		// !!!!!!!!!!!!!
		// Enlace a NORA
		// !!!!!!!!!!!!!
		
		if (item.geoPosition.portal != null && item.geoPosition.street != null) {
			String portal_oid = item.geoPosition.portal.get("oid");
			String street_oid = item.geoPosition.street.get("oid");			
			Util.addIRITriple(NORABaseURIs.DOORWAY.getURI() + portal_oid, ESCJRURIs.viaProp.getURI(),NORABaseURIs.STREET.getURI() + street_oid, namedGraphURI, repositoryConnection);
		}
		
		if (item.geoPosition.address != null) {
			Util.addLiteralTriple(itemURI, SchemaURIs.address.getURI(), item.geoPosition.address.replaceAll("<.*?>", ""), namedGraphURI, repositoryConnection);
		}
	}
	
	private static void processContactInfo(JSONitem item, String itemURI) {
		if (item.contactInfo.phones != null) {
			for (Phone phone : item.contactInfo.phones) {
				Util.addLiteralTriple(itemURI, SchemaURIs.telephone.getURI(), phone.number, namedGraphURI, repositoryConnection);
			}
		}
		
		if (item.contactInfo.emails != null) {
			for (Email email : item.contactInfo.emails) {
				Util.addLiteralTriple(itemURI, SchemaURIs.email.getURI(), email.email, namedGraphURI, repositoryConnection);
			}
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
