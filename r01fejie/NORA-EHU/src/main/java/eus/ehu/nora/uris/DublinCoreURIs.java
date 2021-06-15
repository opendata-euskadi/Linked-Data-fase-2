package eus.ehu.nora.uris;

public enum DublinCoreURIs {
	
	// Properties
	identifier ("identifier")
    ; 

	private final String uri_name;
	private final String base = "http://purl.org/dc/elements/1.1/";

    private DublinCoreURIs(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}
