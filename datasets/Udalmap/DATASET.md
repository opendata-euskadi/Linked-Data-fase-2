# Dataset Udalmap

## Ficha técnica

* Nombre: Udalmap
* URI Named Graph: http://id.euskadi.eus/graph/Udalmap
* URI Named Graph enlaces: http://id.euskadi.eus/graph/Udalmap-links
* URI Named Graph ontologias: http://id.euskadi.eus/graph/Udalmap-vocabs
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

Posibles ontologías:

* ...

Ontologias a incluir en triple store:

* Se crea una clase por cada indicador, y cada medicion es una instancia de esa clase. No hace falta incluir la ontologia.

Para más detalles ver archivo `Directorio-metadata.ttl`:

* ...

## Consultas SPARQL

