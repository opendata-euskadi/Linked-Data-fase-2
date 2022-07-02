package eus.ehu.udalmap;

public abstract class UDALMAP2GRAPHDBConfig  {

	public final static String urlGraphDB = "http://127.0.0.1:7200";
	// De momento uso el mismo repo que NORA, total en LOD euskadi tendran uno propio
	public final static String graphDBUDALMAPrepoName = "NORA";
	public final static String UDALMAPNamedGraphURI = "http://id.euskadi.eus/graph/Udalmap";
	public final static String UDALMAPLinksNamedGraphURI = "http://id.euskadi.eus/graph/Udalmap-links";
	public final static String UDALMAPVocabsNamedGraphURI = "http://id.euskadi.eus/graph/Udalmap-vocabs";
	public final static String UDALMAPMetadataGraphURI = "http://id.euskadi.eus/graph/Udalmap-metadata";
	public final static String MetadataFile = "Udalmap-metadata.ttl"; // cp ../../../../../datasets/Udalmap/Udalmap-metadata.ttl .
	public final static String LinksFile = "Udalmap-links.ttl"; // cp ../../../../../datasets/Udalmap/Udalmap-links.ttl . 
	
	public final static String graphDBUser = "admin";
	public final static String graphDBPassword = "root";
	
	public final static String URLIndiceIndicadores = "https://www.opendata.euskadi.eus/contenidos/estadistica/udalmap_grupo_m/es_def/adjuntos/indice.json";
	
	public final static boolean clearGraph = true;
	public static final String RDFfileBackupPath = "/home/mikel/EHU-LSI/Investigacion/TransferenciaConocimientoEuskoiker/EJIE-LDf2-2105015/Linked-Data-fase-2/Udalmap-ehu/udalmap.ttl";

}
