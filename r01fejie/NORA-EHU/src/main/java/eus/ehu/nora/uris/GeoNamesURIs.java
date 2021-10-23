package eus.ehu.nora.uris;

public enum GeoNamesURIs {
	
	// Properties
    featureCode ("featureCode"),  
    featureClass ("featureCode"),
    officialname ("officialname"),
    
    // Entities
    admd ("A.ADMD"),
    a ("A"),
    pppl("P.PPL"),
    p("P"),
    rst("R.ST"),
    r("R")
    ; 

	private final String uri_name;
	private final String base = "http://www.geonames.org/ontology#";

    private GeoNamesURIs(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}
