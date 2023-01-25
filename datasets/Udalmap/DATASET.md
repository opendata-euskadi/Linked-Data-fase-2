# Dataset Udalmap

## Ficha técnica

* Nombre: Udalmap
* URI Named Graph: https://id.euskadi.eus/graph/Udalmap
* URI Named Graph enlaces: https://id.euskadi.eus/graph/Udalmap-links
* URI Named Graph ontologias: https://id.euskadi.eus/graph/Udalmap-vocabs
* Origen:
  * Indice general: https://www.opendata.euskadi.eus/contenidos/estadistica/udalmap_grupo_m/es_def/adjuntos/indice.json
  * ECONOMÍA / COMPETITIVIDAD: https://www.opendata.euskadi.eus/catalogo/-/indicadores-municipales-de-sostenibilidad-economia-competitividad/
  * COHESIÓN SOCIAL / CALIDAD DE VIDA: https://www.opendata.euskadi.eus/catalogo/-/indicadores-municipales-de-sostenibilidad-cohesion-social-calidad-de-vida/
  * MEDIO AMBIENTE Y MOVILIDAD: https://www.opendata.euskadi.eus/catalogo/-/indicadores-municipales-de-sostenibilidad-medioambiente-y-movilidad/
* DCAT/Metadatos: `Udalmap-metadata.ttl`
* Prototipo: `Udalmap-abstract.ttl`
* Patrón de URIs:
  * Sector: public-sector
  * Domain: udalmap
  * ClassName: indicadormunicipalsostenbilidad
  * Identifier: nombre de indicador normalizado + localizacion (entity|region|municipality) + periodo + valor
* Enlaces a/desde otros datasets:
  * NORA: URI NORA mediante localizacion (entity|region|municipality)
* Notas:
  * Se crea una clase por cada indicador, y cada medicion es una instancia de esa clase

## Ontologías

Ontologías usadas:

* [RDF Data Cube Vocabulary](https://www.w3.org/TR/vocab-data-cube/).
* [WGS84](https://www.w3.org/2003/01/geo/wgs84_pos#).

Ontologias a incluir en triple store:

* Se crea una clase por cada indicador, y cada medicion es una instancia de esa clase. No hace falta incluir la ontologia.

## Consultas SPARQL

### Lista indicadores

```sparql
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
SELECT DISTINCT ?nombre_indicador
FROM <https://id.euskadi.eus/graph/Udalmap>
WHERE { 
	?medicion rdf:type ?indicador .
  ?indicador rdfs:label ?nombre_indicador
} 
```

### Valores de indicador que contenga la palabra "agro" ordenados por años

```sparql
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT DISTINCT ?nombre_indicador

FROM <https://id.euskadi.eus/graph/Udalmap>

WHERE { 
	?medicion rdf:type ?indicador .
    ?medicion <http://purl.org/linked-data/sdmx/2009/measure#obsValue> ?value .
    ?medicion <http://purl.org/linked-data/sdmx/2009/dimension#refPeriod> ?year .
    ?indicador rdfs:label ?nombre_indicador .
    FILTER CONTAINS(?nombre_indicador, "agro") .
}
```

### Valores de indicador que contenga la palabra "agro" de municipios de Alava (Y su enlace a WikiData), ordenados por años 

```sparql
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX wgs: <http://www.w3.org/2003/01/geo/wgs84_pos#>
PREFIX gn: <https://www.geonames.org/ontology#>
PREFIX territorio: <http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio#>
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX measure: <http://purl.org/linked-data/sdmx/2009/measure#>
PREFIX dimension: <http://purl.org/linked-data/sdmx/2009/dimension#>

SELECT DISTINCT ?nombre_indicador ?year ?value ?location_name

WHERE { 
    GRAPH <https://id.euskadi.eus/graph/Udalmap>{
		  ?medicion rdf:type ?indicador .
    	?medicion measure:obsValue ?value .
    	?medicion dimension:refPeriod ?year .
    	?medicion wgs:location ?location .
      ?indicador rdfs:label ?nombre_indicador .
      FILTER CONTAINS(?nombre_indicador, "agro") .
    }
    GRAPH <https://id.euskadi.eus/graph/NORA> {
    	?location gn:officialname ?location_name .
      ?location territorio:provincia ?provincia .
      ?provincia rdfs:label "Araba"@eu .
	  }
    GRAPH <https://id.euskadi.eus/graph/NORA-links> {
      ?location owl:sameAs ?location_wikidata .
    }
}
ORDER BY ?nombre_indicador ?location_name ?year
```

### Valores de indicador que contenga la palabra "agro" de municipios de Alava (Y su enlace a WikiData), ordenados por años, junto con su area (Sacada de la DBPedia)

```sparql
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX wgs: <http://www.w3.org/2003/01/geo/wgs84_pos#>
PREFIX gn: <https://www.geonames.org/ontology#>
PREFIX territorio: <http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio#>
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX measure: <http://purl.org/linked-data/sdmx/2009/measure#>
PREFIX dimension: <http://purl.org/linked-data/sdmx/2009/dimension#>
PREFIX dbo: <http://dbpedia.org/ontology/>

SELECT DISTINCT ?nombre_indicador ?year ?value ?location_name ?area

WHERE { 
    GRAPH <https://id.euskadi.eus/graph/Udalmap>{
		?medicion rdf:type ?indicador .
    	?medicion measure:obsValue ?value .
    	?medicion dimension:refPeriod ?year .
    	?medicion wgs:location ?location .
        ?indicador rdfs:label ?nombre_indicador .
        FILTER CONTAINS(?nombre_indicador, "agro") .
    }
    GRAPH <https://id.euskadi.eus/graph/NORA> {
    	?location gn:officialname ?location_name .
        ?location territorio:provincia ?provincia .
        ?provincia rdfs:label "Araba"@eu .
	}
    GRAPH <https://id.euskadi.eus/graph/NORA-links> {
        ?location owl:sameAs ?location_wikidata .
    }
    SERVICE <https://dbpedia.org/sparql> {
        ?location_dbpedia owl:sameAs ?location_wikidata .
        ?location_dbpedia dbo:areaTotal ?area .
    }
}
ORDER BY ?nombre_indicador ?location_name ?year
```