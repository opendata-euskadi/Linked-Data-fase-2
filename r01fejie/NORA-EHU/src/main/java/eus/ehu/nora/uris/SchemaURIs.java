package eus.ehu.nora.uris;

public enum SchemaURIs {
	
	// Properties
	identifier ("identifier")
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
