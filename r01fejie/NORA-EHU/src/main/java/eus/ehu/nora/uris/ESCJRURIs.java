package eus.ehu.nora.uris;

public enum ESCJRURIs {
	
	// Classes
	Via ("Via"),
	Portal ("Portal"),
	
    // Properties
    viaProp ("via")

    ; 

	private final String uri_name;
	private final String base = "http://vocab.linkeddata.es/datosabiertos/def/urbanismo-infraestructuras/callejero#";

    private ESCJRURIs(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}
