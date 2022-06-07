package eus.ehu.directorio.uris;

public enum EuskadiURIs {
	
	// Classes
	Equipment ("Equipment"),
	
	// Properties
	curriculum ("curriculum"),
	equipmentOf("equipmentOf"),
	webLink ("webLink"), 
	publicEntityType ("publicEntityType"),
	scope ("scope"),
	legislature ("legislature")
    ; 

	private final String uri_name;
	private final String base = "http://id.euskadi.eus/def/directory#";

    private EuskadiURIs(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}
