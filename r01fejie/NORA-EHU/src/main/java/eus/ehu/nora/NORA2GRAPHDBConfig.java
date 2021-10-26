package eus.ehu.nora;

public abstract class NORA2GRAPHDBConfig  {

	public final static String urlGraphDB = "http://127.0.0.1:7200";
	public final static String graphDBNORArepoName = "NORA";
	public final static String NORANamedGraphURI = "http://id.euskadi.eus/graph/NORA";
	public final static String NORALinksNamedGraphURI = "http://id.euskadi.eus/graph/NORA-links";
	public final static String NORAVocabsNamedGraphURI = "http://id.euskadi.eus/graph/NORA-vocabs";
	public final static String NORAMetadataGraphURI = "http://id.euskadi.eus/graph/NORA-metadata";
	public final static String MetadataFile = "NORA-metadata.ttl"; // cp ../../../../../datasets/NORA/NORA-metadata.ttl .
	public final static String LinksFile = "NORA-links.ttl"; // cp ../../../../../datasets/NORA/NORA-links.ttl . 
	
	public final static boolean clearGraph = true;

}
