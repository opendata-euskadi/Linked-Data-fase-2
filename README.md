# Producción de Datos Enlazados para Open Data Euskadi

## Introducción

Este es el repositorio del Linked Data Fase 2. El objetivo del proyecto es convertir ciertos DataSets de gran valor a Linked Data y utilizar la infraestructura ya instalada en [Open Data Euskadi](https://opendata.euskadi.eus/lod/-/linked-open-data/) para publicarlos (Mayormente la Triple Store [GraphDB](https://graphdb.ontotext.com/)).

A grandes rasgos este repositorio contiene los siguientes elementos:

* Gestión:
  * Documentación (En el [Wiki](https://github.com/opendata-euskadi/Produccion-Datos-Enlazados-contrato-2105015/wiki) o mediante archivos MarkDown).
  * Issues para las tareas.
  * GitHub projects para Kanban, hitos etc.
* Productos:
  * Programas para:
    * Convertir datos a RDF, incluyendo el esquema de URIs ya definido.
    * Descubrir enlaces.
    * Gestionar los metadataos y Named Graphs con respecto a DCAT.
  * Datos RDF propiamente dichos.
  * Consultas SPARQL sobre los datos RDF.
  * Ontologías reusadas o creadas.  

## DataSets

Estos son lo datos que se espera convertir a Linked Data:

* NORA. NORA es el callejero de la Comunidad Autónoma del País Vasco que es mantenido por el Instituto Vasco de Estadística - EUSTAT.
* UDALMAP. UDALMAP es un sistema de información municipal, cuya finalidad es mostrar la realidad socio-económica de los municipios de Euskadi y el diseño y evaluación de políticas públicas.
* Directorio Gobierno Vasco: Personas, entidades, y equipamientos.
* Datasets Open Data Euskadi. Archivos DCAT(RDF) de DataSets.
* Datos Normativos en formato ELI (European Legislation Identifier). Datos de LegeGunea en JSON-LD (Obtenido de las páginas web).

## Directorios

Directorios actuales y su contenido:

* `datasets`: datasets procesados, inluyendog ficha, ontologías, ejemplos de RDF, etc.
* `graphdb-silk-docker`: proyecto docker compose para levantar servicios GraphDB y Silk en local para pruebas de desarrollo.
* `metadatos`: ejemplo de metadatos en DCAT.
* `r01fejie`: proyecto Maven para convertir NORA a Linked Data.
