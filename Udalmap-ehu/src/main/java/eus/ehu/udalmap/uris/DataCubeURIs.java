package eus.ehu.udalmap.uris;

public enum DataCubeURIs {
	
	// Properties
	obsValue ("measure#obsValue"),  
	refPeriod ("dimension#refPeriod"),
    ; 

	private final String uri_name;
	private final String base = "http://purl.org/linked-data/sdmx/2009/";

    private DataCubeURIs(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}
