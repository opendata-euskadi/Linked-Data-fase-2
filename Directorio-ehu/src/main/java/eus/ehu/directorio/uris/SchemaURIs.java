package eus.ehu.directorio.uris;

public enum SchemaURIs {
	
	// Properties
	identifier ("identifier"),
	address ("address"),
	telephone ("telephone"),
	email ("email"),
	url ("url"),
	mainEntityOfPage ("mainEntityOfPage"),
	member ("member"),
	memberOf ("memberOf"),
	parentOrganization ("parentOrganization"), 
	subOrganization ("subOrganization"),
	location ("location"),
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
