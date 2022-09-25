package eus.ehu.udalmap.uris;

public enum EuskadiURIs {
	
	// Classes
	Indicador ("")
	
	// Properties
    ; 

	private final String uri_name;
	private final String base = "http://id.euskadi.eus/def/udalmap#";

    private EuskadiURIs(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}
