package eus.ehu.directorio;

public abstract class DIRECTORIO2GRAPHDBConfig  {

	public final static String urlGraphDB = "http://127.0.0.1:7200";
	// De momento uso el mismo repo que NORA, total en LOD euskadi tendran uno propio
	public final static String graphDBDIRECTORIOrepoName = "NORA";
	public final static String DIRECTORIONamedGraphURI = "http://id.euskadi.eus/graph/Directorio";
	public final static String DIRECTORIOLinksNamedGraphURI = "http://id.euskadi.eus/graph/Directorio-links";
	public final static String DIRECTORIOVocabsNamedGraphURI = "http://id.euskadi.eus/graph/Directorio-vocabs";
	public final static String DIRECTORIOMetadataGraphURI = "http://id.euskadi.eus/graph/Directorio-metadata";
	public final static String MetadataFile = "DIRECTORIO-metadata.ttl"; // cp ../../../../../datasets/DIRECTORIO/DIRECTORIO-metadata.ttl .
	public final static String LinksFile = "DIRECTORIO-links.ttl"; // cp ../../../../../datasets/DIRECTORIO/DIRECTORIO-links.ttl . 
	
	public final static String DIRECTORIO_API_PEOPLE = "https://api.euskadi.eus/directory/people?fromItemAt=";
	public final static String DIRECTORIO_API_PERSON = "https://api.euskadi.eus/directory/people/person/";
	
	public final static String DIRECTORIO_API_ENTITIES = "https://api.euskadi.eus/directory/entities?fromItemAt=";
	public final static String DIRECTORIO_API_ENTITY = "https://api.euskadi.eus/directory/entities/entity/";
	
	public final static String DIRECTORIO_API_EQUIPMENTS = "https://api.euskadi.eus/directory/equipments?fromItemAt=";
	public final static String DIRECTORIO_API_EQUIPMENT = "https://api.euskadi.eus/directory/equipments/equipment/";
	
	public final static boolean clearGraph = true;

}
