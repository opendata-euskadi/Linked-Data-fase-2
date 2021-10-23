package eus.ehu.nora.uris;

public enum EuskadiURIs {
	
	// Properties
	identifier ("identifier"),
	localidad ("localidad"),
	
	// Classes
	Localidad ("Localidad"),
    ; 

	private final String uri_name;
	private final String base = "http://id.euskadi.eus/def/nora#";

    private EuskadiURIs(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}
