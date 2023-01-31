# Dataset Directorio

## Ficha técnica

* Nombre: Directorio
* URI Named Graph: https://id.euskadi.eus/graph/Directory
* URI Named Graph enlaces: https://id.euskadi.eus/graph/Directory-links
* URI Named Graph ontologias: https://id.euskadi.eus/graph/Directory-vocabs
* Origen: https://www.opendata.euskadi.eus/webopd00-apidirectory/es?api=directory
* DCAT/Metadatos: `Directorio-metadata.ttl`
* Prototipo: `Directorio-abstract.ttl`
* Patrón de URIs:
  * Sector: public-sector
  * Domain: directory
  * ClassName: person|organization|equipment
  * Identifier: oid de JSON
* Enlaces a/desde otros datasets:
  * NORA: los equipamientos incluyen un portal que se enlaza a una calle de NORA mediante el predicado `http://vocab.linkeddata.es/datosabiertos/def/urbanismo-infraestructuras/callejero#via`
* Notas:

## Ontologías

Ontologías usadas:

* [https://id.euskadi.eus/def/directory](directorio.ttl). Ontología creada para este dataset con algunas entidades específicas como `Equipment` o `legislature` (**Subir `directorio.ttl` a GraphDB**).
* [ISA Programme Person Core Vocabulary](http://www.w3.org/ns/person). (**Subir `person.ttl` a GraphDB**).
* [Schema](https://schema.org).
* [IGN Geo](https://datos.ign.es/def/geo_core#).
* [Callejero](http://vocab.linkeddata.es/datosabiertos/def/urbanismo-infraestructuras/callejero).
* [Organization](www.w3.org/ns/org#).

## Consultas SPARQL

### ¿En qué localidad se encuentra Lehendakaritza?

```sparql
PREFIX dir: <https://id.euskadi.eus/def/directory#>
PREFIX callejero: <http://vocab.linkeddata.es/datosabiertos/def/urbanismo-infraestructuras/callejero#>
PREFIX gn: <https://www.geonames.org/ontology#>

PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX schema: <https://schema.org/>
PREFIX nora: <https://id.euskadi.eus/def/nora#>

SELECT *
WHERE { 
    GRAPH <https://id.euskadi.eus/graph/Directorio> {
    	?equipment dir:equipmentOf ?organization .
    	?equipment schema:location ?portal .
    	?portal callejero:via ?street .
        ?organization rdfs:label ?org_label .
        FILTER CONTAINS(?org_label, "Lehendakaritza") .
    }
    GRAPH <https://id.euskadi.eus/graph/NORA> {
    	?street nora:localidad ?localidad .
    	?localidad gn:officialname ?localidad_name .
    }
}
```

### ¿Que suborganizaciones tiene la organizacion a la que pertenece Bingen Urkullu?

```sparql
PREFIX person: <http://www.w3.org/ns/person#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX schema: <https://schema.org/>
SELECT * WHERE {
	GRAPH <https://id.euskadi.eus/graph/Directorio> {
		?person rdf:type person:Person .
		?person rdfs:label ?person_label .
		?person schema:memberOf ?organization  .
		?organization schema:subOrganization ?subOrg .
		?subOrg rdfs:label ?subOrg_label .
		FILTER CONTAINS(?person_label, "Urkullu") .
	}
}
```

### Obtener los curriculum de las personas que trabajan en entidades que incluyan el termino "Tecnología" en su nombre 

```sparql
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX schema: <https://schema.org/>
PREFIX dir: <https://id.euskadi.eus/def/directory#>

SELECT ?org_label ?person_label ?cv_url 
WHERE { 
    ?organization rdf:type schema:GovernmentOrganization .
    ?organization rdfs:label ?org_label .
    ?organization schema:member ?person .
    ?person rdfs:label ?person_label .
    ?person dir:curriculum ?cv_url .
    FILTER CONTAINS(?org_label, "Tecnología") .
} 
```