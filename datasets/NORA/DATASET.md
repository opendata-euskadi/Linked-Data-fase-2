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

Para más detalles ver archivo `NORA-metadata.ttl`:

* [NORA](nora.ttl). (Importante para consultas transitivas).
* [Territorio (ESADM)](http://vocab.linkeddata.es/datosabiertos/def/sector-publico/territorio), incluye GeoSPARQL (Importante para consultas transitivas). (`territorio.owl`).
* ...

## Consultas SPARQL

Todas las consultas se encuentran en `/r01fejie/NORA-EHU/src/test/resources`. Las más interesantes se citan a continuación.

### Consulta transitiva

Para que esta consulta funcione hay que cargar los archivos `nora.ttl` y `territorio.owl`. Además el repo GraphDB tiene que tener la inferencia activada, por ejemplo con la opción "OWL-Horst (Optimized)".

```sparql
PREFIX geosparql: <http://www.opengis.net/ont/geosparql#>
SELECT ?country
WHERE { 
   <http://id.euskadi.eus/public-sector/urbanism-territory/province/48> geosparql:sfWithin ?country .
}
```
