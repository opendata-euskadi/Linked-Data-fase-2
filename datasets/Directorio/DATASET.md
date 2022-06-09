# Dataset Directorio

## Ficha técnica

* Nombre: Directorio
* URI Named Graph: http://id.euskadi.eus/graph/Directory
* URI Named Graph enlaces: http://id.euskadi.eus/graph/Directory-links
* URI Named Graph ontologias: http://id.euskadi.eus/graph/Directory-vocabs
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

Posibles ontologías:

* [e-Government core vocabularies](https://joinup.ec.europa.eu/collection/semantic-interoperability-community-semic/solution/e-government-core-vocabularies/release/201)

Ontologias a incluir en triple store:

* `directorio.ttl`
* [ISA Programme Person Core Vocabulary](http://www.w3.org/ns/person).(`person.ttl`)

Para más detalles ver archivo `Directorio-metadata.ttl`:

* ...

## Consultas SPARQL

### ¿En qué localidad se encuentra Lehendakaritza?

```sparql
PREFIX dir: <http://id.euskadi.eus/def/directory#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX schema: <https://schema.org/>
PREFIX callejero: <http://vocab.linkeddata.es/datosabiertos/def/urbanismo-infraestructuras/callejero#>
PREFIX nora: <http://id.euskadi.eus/def/nora#>
PREFIX gn: <http://www.geonames.org/ontology#>

SELECT ?localidad_name 
WHERE { 
    ?equipment dir:equipmentOf ?organization .
    ?equipment schema:location ?portal .
    ?portal callejero:via ?street .
    ?street nora:localidad ?localidad .
    ?localidad gn:officialname ?localidad_name .
    ?organization rdfs:label ?org_label .
    FILTER CONTAINS(?org_label, "Lehendakaritza") .
}
```

### ¿Que suborganizaciones tiene la organizacion a la que pertenece Bingen Zupiria?

```sparql
PREFIX person: <http://www.w3.org/ns/person#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX schema: <https://schema.org/>

SELECT ?subOrg ?subOrg_label
WHERE { 
	  ?person rdf:type person:Person .
    ?person rdfs:label ?person_label .
    ?organization schema:member ?person .
    ?organization schema:subOrganization ?subOrg .
    ?subOrg rdfs:label ?subOrg_label .
    FILTER CONTAINS(?person_label, "Bingen Zupiria") .
}
```

### Obtener los curriculum de las personas que trabajan en entidades que incluyan el termino "Tecnología" en su nombre 

```sparql
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX schema: <https://schema.org/>
PREFIX dir: <http://id.euskadi.eus/def/directory#>

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