package eus.ehu.directorio.uris;

public enum SchemaURIs {
	
	// Properties
	identifier ("identifier"),
	// Classes
	GovernmentOrganization ("GovernmentOrganization")
    ; 

	private final String uri_name;
	private final String base = "https://schema.org/";

    private SchemaURIs(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}