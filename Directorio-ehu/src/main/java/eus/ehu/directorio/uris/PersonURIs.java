package eus.ehu.directorio.uris;

public enum PersonURIs {
	
	// Classes
	Person ("Person"),
    // Properties
	birthName ("birthName")
    ; 

	private final String uri_name;
	private final String base = "http://www.w3.org/ns/person#";

    private PersonURIs(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}
