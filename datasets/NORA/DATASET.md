# Dataset NORA

## Ficha técnica

* Nombre: NORA
* URI Named Graph: http://id.euskadi.eus/graph/NORA
* URI Named Graph enlaces: http://id.euskadi.eus/graph/NORA-links
* URI Named Graph ontologias: http://id.euskadi.eus/graph/NORA-vocabs
* Origen: https://www.eustat.eus/productosServicios/idioma_c/kale_formu.html
* DCAT/Metadatos: `NORA-metadata.ttl`
* Prototipo: `NORA-abstract.ttl`
* Patrón de URIs:
  * Sector: public-sector
  * Domain: urbanism-territory
  * ClassName: ESADM (Traducido al inglés)
  * Identifier: NORA-ID (en el caso de municipios, como comparten el mismo ID municipios de diferentes provincias, se añade el ID de la provincia primero)

## Ontologías

Para más detalles ver archivo `NORA-metadata.ttl`. Ontologias a incluir en triple store:

* [NORA](nora.ttl). (Importante para consultas transitivas).
* [Territorio (ESADM)](http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio), incluye GeoSPARQL (Importante para consultas transitivas). (`territorio.owl`).
* [Callejero] (`callejero.owl.rdf`)

## Consultas SPARQL

Todas las consultas se encuentran en `/r01fejie/NORA-EHU/src/test/resources/*.rq`. Las más interesantes se citan a continuación.

### Contar los municipios de Araba

Esta simple consulta nos da el numero de municipios de Araba.

```sparql
PREFIX esadm: <http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT  (COUNT(?municipioAraba) as ?count) 
WHERE { 
  ?municipioAraba esadm:provincia ?provincia .
  ?provincia rdfs:label "Araba"@eu .
} 
```

### Consulta transitiva

Esta consulta aprovecha el razonamineto automático para conseguir entidades que no están directamente relacionadas entre sí. 

Para que esta consulta funcione hay que cargar los archivos `nora.ttl` y `territorio.owl` en el grafo `NORA-vocabs`. Además el repo GraphDB `NORA` tiene que tener la inferencia activada, por ejemplo con la opción "OWL-Horst (Optimized)".

```sparql
PREFIX geosparql: <http://www.opengis.net/ont/geosparql#>
SELECT ?country
WHERE { 
   <http://id.euskadi.eus/public-sector/urbanism-territory/province/48> geosparql:sfWithin ?country .
}
```

### Consulta federada

Esta consulta demuestra la utilidad de los enlaces existentes. A partir de esta consulta básica, se pueden combinar datos de WikiData, DBPedia, etc, explotando la potencia de Linked Data.

```sparql
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX gn: <http://www.geonames.org/ontology#>

SELECT ?entity ?euskoLabel ?wikidataEntity ?wikidataLabel ?dbpediaEntity ?dbpediaLabel

WHERE {
    GRAPH <http://id.euskadi.eus/graph/NORA> {
    	?entity gn:officialname ?euskoLabel .
    }
    GRAPH <http://id.euskadi.eus/graph/NORA-links> {
    	?entity owl:sameAs ?wikidataEntity .
    }
    SERVICE <https://query.wikidata.org/sparql> { 
       ?wikidataEntity rdfs:label ?wikidataLabel . 
    } 
    SERVICE <https://dbpedia.org/sparql> { 
       ?dbpediaEntity owl:sameAs ?wikidataEntity .
       ?dbpediaEntity rdfs:label ?dbpediaLabel . 
    } 
}
```
