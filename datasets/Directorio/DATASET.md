# Dataset Directorio

## Ficha técnica

* Nombre: Directorio
* URI Named Graph: http://id.euskadi.eus/graph/Directorio
* URI Named Graph enlaces: http://id.euskadi.eus/graph/Directorio-links
* URI Named Graph ontologias: http://id.euskadi.eus/graph/Directorio-vocabs
 Origen:
  * Acceso provisional a directorio (Logearse con Google): https://platea.cms.sandbox.euskadi.eus/directory
  * Origen definitivo API euskadi.eus:
    * Buscar todas las entidades: https://api.euskadi.eus/directory/entities
    * Buscar una entidad: https://api.euskadi.eus/directory/entities/entity/2EB8C7B8-6386-489F-9875-0FB4975D5B56
    * Buscar todas las personas: https://api.euskadi.eus/directory/people
    * Buscar una persona: https://api.euskadi.eus/directory/people/person/7804BBD9-CDBE-4E07-B16A-F9D08287521C
    * Buscar todos los equipamientos: https://api.euskadi.eus/directory/equipments
    * Buscar un equipamiento: https://api.euskadi.eus/directory/equipments/equipment/1C31174D-9553-452F-B554-5E5C29110FC5
    * Buscar todas las entidades públicas: https://api.euskadi.eus/directory/entities?qry=objSubType.MUST.beEqualTo(PUBLIC_SECTOR)
    * Buscar todas las entidades públicas y asociaciones: https://api.euskadi.eus/directory/entities?qry=objSubType.MUST.beWithin(PUBLIC_SECTOR,ASSOCIATION)
    * Buscar todas las entidades públicas del sector "Gobierno y Administración Pública": https://api.euskadi.eus/directory/entities?qry=objSubType.MUST.beEqualTo(PUBLIC_SECTOR);labelsCatalog.MUST.beWithin(r01e00000ff26d461e3a470b8181621308b9c4fdf/1)
    * Buscar todas las entidades públicas del sector "Gobierno y Administración Pública"y "Acción exterior": https://api.euskadi.eus/directory/entities?qry=objSubType.MUST.beEqualTo(PUBLIC_SECTOR);labelsCatalog.MUST.beWithin(r01e00000ff26d461e3a470b8181621308b9c4fdf/1,r01e00000ff26d461e3a470b89602cd52e9b70e15/1)
    * Buscar todas las entidades públicas del sector "Gobierno y Administración Pública"y "Acción exterior" que contenga el texto "vicelehendakari": https://api.euskadi.eus/directory/entities?qry=objSubType.MUST.beEqualTo(PUBLIC_SECTOR);fullTextSummary.MUST.contain(vicelehendakari%20AT%20FULL);labelsCatalog.MUST.beWithin(r01e00000ff26d461e3a470b8181621308b9c4fdf/1,r01e00000ff26d461e3a470b89602cd52e9b70e15/1)
* DCAT/Metadatos: `Directorio-metadata.ttl`
* Prototipo: `Directorio-abstract.ttl`
* Patrón de URIs:
  * Sector: public-sector
  * Domain: directory
  * ClassName: person
  * Identifier: alfanumerico de directorio?
* Enlaces a/desde otros datasets:
  * NORA (Para ubicacion de entidades y personas, mediante portal)
* Notas:
  * Las URIs de directorio y de NORA tienen que ser las mismas, o enlazar solo al portal?

## Ontologías

Posibles ontologías:

* [e-Government core vocabularies](https://joinup.ec.europa.eu/collection/semantic-interoperability-community-semic/solution/e-government-core-vocabularies/release/201)

Ontologias a incluir en triple store:

* [ISA Programme Person Core Vocabulary](http://www.w3.org/ns/person).(`person.ttl`)

Para más detalles ver archivo `Directorio-metadata.ttl`:

* ...

## Consultas SPARQL

A definir.