package eus.ehu.udalmap.uris;

public enum UDALMAPBaseURIs {
	
    INDICADORMUNICIPALSOSTENIBILIDAD ("indicadormunicipalsostenbilidad/")
    ; 

	private final String uri_name;
	private final String base = "https://id.euskadi.eus/public-sector/udalmap/";

    private UDALMAPBaseURIs(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}
