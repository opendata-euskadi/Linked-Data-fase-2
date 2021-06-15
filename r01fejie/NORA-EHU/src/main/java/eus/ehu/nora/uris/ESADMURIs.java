package eus.ehu.nora.uris;

public enum ESADMURIs {
	
	// Classes
    PAIS ("Pais"),  
    COMUNIDAD_AUTONOMA("ComunidadAutonoma"),
    PROVINCIA("Provincia"),
    MUNICIPIO("Municipio"),
    
    // Properties
    paisProp ("pais"),
    autonomiaProp ("autonomia"),
    provinciaProp ("provincia")
    ; 

	private final String uri_name;
	private final String base = "http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio#";

    private ESADMURIs(String uri_name) {
        this.uri_name = base+uri_name;
    }
    
    public String getURI () {
    	return this.uri_name;
    }

}
