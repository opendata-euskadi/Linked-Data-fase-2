package eus.ehu.directorio.uris;

public enum OrganizationURIs {
	
	// Properties
	identifier ("identifier"),
	// Classes
	Organization ("Organization")
    ; 

	private final String uri_name;
	private final String base = "http://www.w3.org/ns/org#";

    private OrganizationURIs(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}
