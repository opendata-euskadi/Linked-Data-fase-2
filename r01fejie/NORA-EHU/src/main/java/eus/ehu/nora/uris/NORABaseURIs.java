package eus.ehu.nora.uris;

public enum NORABaseURIs {
	
	// NORA
    COUNTRY ("country/"),  
    AUTONOMOUS_COMMUNITY("autonomous_community/"), 
    PROVINCE("province/"),
    REGION("region/"),
    MUNICIPALITY("municipality/"),
    LOCALITY("locality/"),
    NEIGHBOURHOOD("neighbourhood/"),
    DISTRICT("district/"),
    SECTION("section/"),
    // Callejero
    STREET("street/"),
    STREETTYPE("streettype/"),
    DOORWAY("doorway/"),
    ; 

	private final String uri_name;
	private final String base = "https://id.euskadi.eus/public-sector/urbanism-territory/";

    private NORABaseURIs(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}
