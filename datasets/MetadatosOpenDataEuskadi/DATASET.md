# Dataset NORA

## Ficha técnica

* Nombre: Metadatos Open Data Euskadi
* URI Named Graph: http://id.euskadi.eus/graph/DCATOpenDataEuskadi
* Origen: Todos los DCAT de Open Data Euskadi
* DCAT/Metadatos: No aplica
* Prototipo: No aplica
* Patrón de URIs: No aplica

## Ontologías

No aplica.

## Consultas SPARQL

¿Cuantos conjuntos de datos hay en Open Data Euskadi?

```sparql
PREFIX dcat: <http://www.w3.org/ns/dcat#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>

SELECT (COUNT(?dataset) AS ?cantidad_datasets)
FROM <http://id.euskadi.eus/graph/DCATOpenDataEuskadi>
WHERE { 
	?dataset rdf:type dcat:Dataset .
}
```

https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0A%0ASELECT%20%3Fdataset%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%7D%0A

Conjuntos de datos modificados en 2021, ordenados por fecha de modificación (Primero la más antigua):

```sparql
PREFIX dcat: <http://www.w3.org/ns/dcat#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX dct: <http://purl.org/dc/terms/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>

SELECT ?dataset ?title ?date_modified
FROM <http://id.euskadi.eus/graph/DCATOpenDataEuskadi>
WHERE { 
	?dataset rdf:type dcat:Dataset .
    ?dataset dct:modified ?date_modified .
	?dataset dct:title ?title .
    FILTER (?date_modified < "2022-01-01T00:00:00"^^xsd:dateTime && ?date_modified > "2021-01-01T00:00:00"^^xsd:dateTime)
}
ORDER BY ?date_modified

```

https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0APREFIX%20xsd%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0A%0ASELECT%20%3Fdataset%20%3Ftitle%20%3Fdate_modified%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%3Fdataset%20dct%3Amodified%20%3Fdate_modified%20.%0A%09%3Fdataset%20dct%3Atitle%20%3Ftitle%20.%0A%20%20%20%20FILTER%20(%3Fdate_modified%20%3C%20%222022-01-01T00%3A00%3A00%22%5E%5Exsd%3AdateTime%20%26%26%20%3Fdate_modified%20%3E%20%222021-01-01T00%3A00%3A00%22%5E%5Exsd%3AdateTime)%0A%7D%0AORDER%20BY%20%3Fdate_modified%0A

Formatos disponibles:

```sparql
PREFIX dcat:<http://www.w3.org/ns/dcat#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX dct: <http://purl.org/dc/terms/>

SELECT DISTINCT ?format_value
FROM <http://id.euskadi.eus/graph/DCATOpenDataEuskadi>
WHERE { 
	?dataset rdf:type dcat:Dataset .
    ?dataset dcat:distribution ?distribution .
    ?distribution dct:format ?format .
    ?format rdf:value ?format_value . 
}
```

Conjuntos de datos disponibles en CSV:

```sparql
PREFIX dcat:<http://www.w3.org/ns/dcat#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX dct: <http://purl.org/dc/terms/>

SELECT DISTINCT ?dataset
FROM <http://id.euskadi.eus/graph/DCATOpenDataEuskadi>
WHERE { 
	?dataset rdf:type dcat:Dataset .
    ?dataset dcat:distribution ?distribution .
    ?distribution dct:format ?format .
    ?format rdf:value "text/csv" 
}
```

https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0A%0ASELECT%20DISTINCT%20%3Fdataset%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%3Fdataset%20dcat%3Adistribution%20%3Fdistribution%20.%0A%20%20%20%20%3Fdistribution%20dct%3Aformat%20%3Fformat%20.%0A%20%20%20%20%3Fformat%20rdf%3Avalue%20%22text%2Fcsv%22%20%0A%7D%0A

Conjuntos de datos sobre coronavirus:

```sparql
PREFIX dcat:<http://www.w3.org/ns/dcat#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX dct: <http://purl.org/dc/terms/>

SELECT DISTINCT ?dataset
FROM <http://id.euskadi.eus/graph/DCATOpenDataEuskadi>
WHERE { 
	?dataset rdf:type dcat:Dataset .
    ?dataset dcat:keyword ?keywords .
    FILTER CONTAINS(?keywords, "coronavirus") .
}
```

https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0A%0ASELECT%20DISTINCT%20%3Fdataset%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%3Fdataset%20dcat%3Akeyword%20%3Fkeywords%20.%0A%20%20%20%20FILTER%20CONTAINS(%3Fkeywords%2C%20%22coronavirus%22)%20.%0A%7D%0A

Temas sobre los que tratan los conjuntos de datos actualmente disponibles:

```sparql
PREFIX dcat:<http://www.w3.org/ns/dcat#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX dct: <http://purl.org/dc/terms/>

SELECT DISTINCT ?tema
FROM <http://id.euskadi.eus/graph/DCATOpenDataEuskadi>
WHERE { 
	?dataset rdf:type dcat:Dataset .
    ?dataset dcat:theme ?tema .
}
```

https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0A%0ASELECT%20DISTINCT%20%3Ftema%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%3Fdataset%20dcat%3Atheme%20%3Ftema%20.%0A%7D%0A

Conjuntos de datos sobre urbanismo, ordenados cronologicamente:

```sparql
PREFIX dcat: <http://www.w3.org/ns/dcat#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX dct: <http://purl.org/dc/terms/>
PREFIX sector: <https://datos.gob.es/kos/sector-publico/sector/>

SELECT ?dataset ?date_modified
FROM <http://id.euskadi.eus/graph/DCATOpenDataEuskadi>
WHERE { 
	?dataset rdf:type dcat:Dataset .
    ?dataset dcat:theme sector:urbanismo-infraestructuras .
    ?dataset dct:modified ?date_modified .
    ?dataset dct:description ?description .
}
ORDER BY ?date_modified
```

https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0APREFIX%20sector%3A%20%3Chttps%3A%2F%2Fdatos.gob.es%2Fkos%2Fsector-publico%2Fsector%2F%3E%0A%0ASELECT%20%3Fdataset%20%3Fdate_modified%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%3Fdataset%20dcat%3Atheme%20sector%3Aurbanismo-infraestructuras%20.%0A%20%20%20%20%3Fdataset%20dct%3Amodified%20%3Fdate_modified%20.%0A%20%20%20%20%3Fdataset%20dct%3Adescription%20%3Fdescription%20.%0A%7D%0AORDER%20BY%20%3Fdate_modified%0A

Conjuntos de datos sobre urbanismo que contengan la palabra "Biosfera" en la descripción, ordenados cronologicamente:

```sparql
PREFIX dcat: <http://www.w3.org/ns/dcat#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX dct: <http://purl.org/dc/terms/>
PREFIX sector: <https://datos.gob.es/kos/sector-publico/sector/>

SELECT ?dataset ?description ?date_modified
FROM <http://id.euskadi.eus/graph/DCATOpenDataEuskadi>
WHERE { 
	?dataset rdf:type dcat:Dataset .
    ?dataset dcat:theme sector:urbanismo-infraestructuras .
    ?dataset dct:modified ?date_modified .
    ?dataset dct:description ?description .
    FILTER CONTAINS(?description, "Biosfera") .
}
ORDER BY ?date_modified
```

https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0APREFIX%20sector%3A%20%3Chttps%3A%2F%2Fdatos.gob.es%2Fkos%2Fsector-publico%2Fsector%2F%3E%0A%0ASELECT%20%3Fdataset%20%3Fdescription%20%3Fdate_modified%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%3Fdataset%20dcat%3Atheme%20sector%3Aurbanismo-infraestructuras%20.%0A%20%20%20%20%3Fdataset%20dct%3Amodified%20%3Fdate_modified%20.%0A%20%20%20%20%3Fdataset%20dct%3Adescription%20%3Fdescription%20.%0A%20%20%20%20FILTER%20CONTAINS(%3Fdescription%2C%20%22Biosfera%22)%20.%0A%7D%0AORDER%20BY%20%3Fdate_modified%0A

Conjuntos de datos que cubran un rango de tiempo en los datos propiamente dichos entre 2018 y 2022:

```sparql
PREFIX dcat: <http://www.w3.org/ns/dcat#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX dct: <http://purl.org/dc/terms/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
PREFIX time: <http://www.w3.org/2006/time#>

SELECT ?dataset ?title ?beggining_time ?end_time
FROM <http://id.euskadi.eus/graph/DCATOpenDataEuskadi>
WHERE { 
	?dataset rdf:type dcat:Dataset .
    ?dataset dct:title ?title .
    ?dataset dct:temporal ?temporal .
    ?temporal time:hasBeginning ?beggining .
    ?beggining time:inXSDDateTime ?beggining_time .
    ?temporal time:hasEnd ?end .
    ?end time:inXSDDateTime ?end_time .
    #FILTER (?end_time < "2022-01-01T00:00:00"^^xsd:dateTime && ?beggining_time > "2018-01-01T00:00:00"^^xsd:dateTime)
}
```

Federacion????

```sparql
PREFIX dcat: <http://www.w3.org/ns/dcat#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX dct: <http://purl.org/dc/terms/>
PREFIX sector: <https://datos.gob.es/kos/sector-publico/sector/>

SELECT ?dataset ?dataset_gob
WHERE { 
    GRAPH <http://id.euskadi.eus/graph/DCATOpenDataEuskadi> {
		?dataset rdf:type dcat:Dataset .
    	?dataset dcat:theme sector:medio-ambiente .
    }
    SERVICE <https://datos.gob.es/virtuoso/sparql> {
        GRAPH <http://datos.gob.es/catalogo> {
        	?dataset_gob rdf:type dcat:Dataset .
        	?dataset_gob dcat:theme sector:medio-ambiente .
        }
    }
}
```

dct:accrualPeriodicity???