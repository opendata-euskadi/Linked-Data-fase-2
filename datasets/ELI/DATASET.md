# Dataset NORA

## Ficha técnica

* Nombre: European Legislation Identifier (ELI)
* URI Named Graph: http://id.euskadi.eus/graph/bopv-european-legislation-identifier-eli
* Origen: Archivos JSON-LD que se inyectan en páginas de legegunea. Por ejemplo en https://www.legegunea.euskadi.eus/eli/es-pv/res/2022/01/14/(2)/dof/spa/html/, en el codigo fuente, buscar `<script type="application/ld+json">`
* DCAT/Metadatos: No aplica
* Prototipo: No aplica
* Patrón de URIs:
  * [ELI](https://eur-lex.europa.eu/eli-register/about.html)
  * [ELI](https://op.europa.eu/en/web/eu-vocabularies/eli)


## Ontologías

* [Schema](https://schema.org).
* [ELI ontology](https://op.europa.eu/en/web/eu-vocabularies/eli).

## Consultas SPARQL

Obtener la página de legegunea a partir de una la URI de un Recurso Legal:

```sparql
PREFIX eli:<https://data.europa.eu/eli/ontology#>

SELECT ?url
FROM <http://id.euskadi.eus/graph/bopv-european-legislation-identifier-eli>
WHERE {
    <http://id.euskadi.eus/eli/es-pv/res/2018/10/18/(3)/dof> eli:is_realized_by ?LegalExpression .
    ?format eli:embodies ?LegalExpression .
    ?format eli:published_in_format ?url .
}
```

https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20eli%3A%3Chttps%3A%2F%2Fdata.europa.eu%2Feli%2Fontology%23%3E%0A%0ASELECT%20%3Furl%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2Fbopv-european-legislation-identifier-eli%3E%0AWHERE%20%7B%0A%20%20%20%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Feli%2Fes-pv%2Fres%2F2018%2F10%2F18%2F(3)%2Fdof%3E%20eli%3Ais_realized_by%20%3FLegalExpression%20.%0A%20%20%20%20%3Fformat%20eli%3Aembodies%20%3FLegalExpression%20.%0A%20%20%20%20%3Fformat%20eli%3Apublished_in_format%20%3Furl%20.%0A%7D%0A

Paǵinas web de leyes que se hayan publicado en 2021 que se relacionen con la salud, ordenadas por fechas:

```sparql
PREFIX eli:<https://data.europa.eu/eli/ontology#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>

SELECT ?url ?date_publication
FROM <http://id.euskadi.eus/graph/bopv-european-legislation-identifier-eli>
WHERE {
	?LegalResource rdf:type eli:LegalResource .
    ?LegalResource eli:is_about <https://id.euskadi.eus/kos/public-sector/sector/salud> .
    ?LegalResource eli:is_realized_by ?LegalExpression .
    ?format eli:embodies ?LegalExpression .
    ?format eli:published_in_format ?url .
    ?LegalResource eli:date_publication ?date_publication .
    FILTER (?date_publication < "2022-01-01"^^xsd:date && ?date_publication > "2021-01-01"^^xsd:date)
}
ORDER BY ?date_publication
```
https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20eli%3A%3Chttps%3A%2F%2Fdata.europa.eu%2Feli%2Fontology%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20xsd%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0A%0ASELECT%20%3Furl%20%3Fdate_publication%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2Fbopv-european-legislation-identifier-eli%3E%0AWHERE%20%7B%0A%09%3FLegalResource%20rdf%3Atype%20eli%3ALegalResource%20.%0A%20%20%20%20%3FLegalResource%20eli%3Ais_about%20%3Chttps%3A%2F%2Fid.euskadi.eus%2Fkos%2Fpublic-sector%2Fsector%2Fsalud%3E%20.%0A%20%20%20%20%3FLegalResource%20eli%3Ais_realized_by%20%3FLegalExpression%20.%0A%20%20%20%20%3Fformat%20eli%3Aembodies%20%3FLegalExpression%20.%0A%20%20%20%20%3Fformat%20eli%3Apublished_in_format%20%3Furl%20.%0A%20%20%20%20%3FLegalResource%20eli%3Adate_publication%20%3Fdate_publication%20.%0A%20%20%20%20FILTER%20(%3Fdate_publication%20%3C%20%222022-01-01%22%5E%5Exsd%3Adate%20%26%26%20%3Fdate_publication%20%3E%20%222021-01-01%22%5E%5Exsd%3Adate)%0A%7D%0AORDER%20BY%20%3Fdate_publication%0A
