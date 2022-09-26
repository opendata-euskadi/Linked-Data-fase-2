package eus.ehu.udalmap.uris;

public enum WGS84URIS {
	
	// Properties
	location ("location")
    ; 

	private final String uri_name;
	private final String base = "http://www.w3.org/2003/01/geo/wgs84_pos#";

    private WGS84URIS(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}
