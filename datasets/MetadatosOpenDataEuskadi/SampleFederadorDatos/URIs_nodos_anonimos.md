# Patrón de URIS para evitar nodos anónimos

## Introducción

En este archivo se detalla el patrón de URIs a generar para evitar nodos anónimos en los DCAT de Open Data Euskadi. Se usan los archivos adjuntos `federador_datos_sample.rdf` y `tmp-7370673198265187561.rdf` como punto de partida y se ha generado el archivo `federador_datos_sample_URIs.rdf` y `tmp-7370673198265187561_URIs.rdf` con la URIs, aplicando el patrón que se detalla a continuación (Los archivos resultantes se han cargado en GraphDB y dan el resultado deseado sin problemas).

## Patrón de URIs para dataset (`dcat:Dataset`)

* `dct:distribution/dct:format`: 
  * Usar como base la URI del `dcat:Distribution`, en este caso `https://opendata.euskadi.eus/contenidos/ds_contrataciones/contratos_poder360_2019/es_contracc/adjuntos/doc_contratos_poder360_2019.xlsx`.
  * Añadir "format" a esa URI y añadir la URI resultante a `dct:IMT`: `<dct:IMT about="https://opendata.euskadi.eus/contenidos/ds_contrataciones/contratos_poder360_2019/es_contracc/adjuntos/doc_contratos_poder360_2019.xlsx/format/">`.
* `dcterms:temporal`:
  * Usar como base la URI del dataset (`dcat:Dataset`): `https://opendata.euskadi.eus/catalogo/-/registro-de-contratos-de-ayuntamiento-de-gorliz-del-2019/`.
  * Añadir "temporal" a esa URI y añadir la URI resultante a `time:Interval`: `<time:Interval about="https://opendata.euskadi.eus/catalogo/-/registro-de-contratos-de-ayuntamiento-de-gorliz-del-2019/temporal/">`.
* `dcterms:temporal/time:hasBeginning`:
  * Usar como base la URI del intervalo temporal (`time:Interval`): `https://opendata.euskadi.eus/catalogo/-/registro-de-contratos-de-ayuntamiento-de-gorliz-del-2019/temporal/`.
  * Añadir "beggining" a esa URI y añadir la URI resultante a `time:Instant`: `<time:Instant about="https://opendata.euskadi.eus/catalogo/-/registro-de-contratos-de-ayuntamiento-de-gorliz-del-2019/temporal/beggining/">`.
* `dcterms:temporal/time:hasEnd`:
  * Usar como base la URI del intervalo temporal (`time:Interval`): `https://opendata.euskadi.eus/catalogo/-/registro-de-contratos-de-ayuntamiento-de-gorliz-del-2019/temporal/`.
  * Añadir "end" a esa URI y añadir la URI resultante a `time:Instant`: `<time:Instant about="https://opendata.euskadi.eus/catalogo/-/registro-de-contratos-de-ayuntamiento-de-gorliz-del-2019/temporal/end/">`.
* `dct:accrualPeriodicity`:
  * Usar como base la URI del dataset (`dcat:Dataset`): `https://opendata.euskadi.eus/catalogo/-/ofertas-de-los-alojamientos-turisticos-de-euskadi/`.
  * Añadir "frequency" a esa URI y añadir la URI resultante a `dct:Frequency`: `<dct:Frequency about="https://opendata.euskadi.eus/catalogo/-/ofertas-de-los-alojamientos-turisticos-de-euskadi/frequency/">`.
* `dct:accrualPeriodicity/rdf:value`:
  * Usar como base la URI del dct:accrualPeriodicity (`dct:accrualPeriodicity`): `https://opendata.euskadi.eus/catalogo/-/ofertas-de-los-alojamientos-turisticos-de-euskadi/frequency/`.
  * Añadir "durationdescription" a esa URI y añadir la URI resultante a `time:DurationDescription`: `<time:DurationDescription about="https://opendata.euskadi.eus/catalogo/-/ofertas-de-los-alojamientos-turisticos-de-euskadi/frequency/durationdescription">`.

## Patrón de URIs para catalog (`dcat:Catalog`)

* `dct:extent`:
  * Usar como base la URI del catalog (`dcat:Catalog`), en este caso `https://opendata.euskadi.eus/como-reutilizar/contenidos/informacion/api_buscador_euskadinet/es_java/como_utilizar.html`.
  * Añadir "sizeorduration" a esa URI y añadir la URI resultante a `dct:SizeOrDuration`: `<dct:SizeOrDuration about="https://opendata.euskadi.eus/como-reutilizar/contenidos/informacion/api_buscador_euskadinet/es_java/como_utilizar.html/sizeorduration/">`
