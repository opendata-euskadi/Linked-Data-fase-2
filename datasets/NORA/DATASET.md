# Dataset NORA

## Ficha técnica

* Nombre: NORA
* URI Named Graph: http://id.euskadi.eus/graph/NORA
* URI Named Graph enlaces: http://id.euskadi.eus/graph/NORA-links
* URI Named Graph ontologias: http://id.euskadi.eus/graph/NORA-vocabs
* Origen: API NORA (**URL!**)
* DCAT: **To do**
* Prototipo: `NORA-abstract.ttl`
* Patrón de URIs:
  * Sector: public-sector
  * Domain: urbanism-territory
  * ClassName: ESADM (Traducido al inglés)
  * Identifier: NORA-ID

## Ontologías

* [NORA](nora.ttl). (Importante para consultas transitivas).
* [Territorio (ESADM)](http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio), incluye GeoSPARQL (Importante para consultas transitivas). (`territorio.owl`).
* [DBPedia ontology](http://dbpedia.org/ontology/).
* [Schema](https://schema.org/).
* Dublin Core.
* **Wikidata**?

## Consultas SPARQL

### Consulta transitiva

```sparql
PREFIX geosparql: <http://www.opengis.net/ont/geosparql#>
SELECT ?country
WHERE { 
   <http://id.euskadi.eus/public-sector/urbanism-territory/province/48> geosparql:sfWithin ?country .
}
```
