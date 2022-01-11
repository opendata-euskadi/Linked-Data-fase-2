package eus.ehu.directorio.uris;

public enum DIRECTORIOBaseURIs {
	
    PERSON ("person/")
    ; 

	private final String uri_name;
	private final String base = "http://id.euskadi.eus/public-sector/directory/";

    private DIRECTORIOBaseURIs(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}
