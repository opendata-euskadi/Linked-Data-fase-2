package eus.ehu.nora.uris;

public enum GeoURIs {
	
	// Properties
	xETRS89 ("xETRS89"),  
	yETRS89 ("yETRS89"),
    ; 

	private final String uri_name;
	private final String base = "https://datos.ign.es/def/geo_core#";

    private GeoURIs(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}
