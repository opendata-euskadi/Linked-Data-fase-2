# Dataset Metadatos Open Data Euskadi

## Ficha técnica

* Nombre: Metadatos Open Data Euskadi
* URI Named Graph: http://id.euskadi.eus/graph/DCATOpenDataEuskadi
* Origen: Todos los DCAT de Open Data Euskadi
* DCAT/Metadatos: No aplica
* Prototipo: No aplica
* Patrón de URIs: No aplica

## Ontologías

* DCAT.
* Dublin Core.
* ...

## Consultas SPARQL

### ¿Cuantos datasets hay en Open Data Euskadi?

```sparql
PREFIX dcat: <http://www.w3.org/ns/dcat#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>

SELECT (COUNT(?dataset) AS ?cantidad_datasets)
FROM <http://id.euskadi.eus/graph/DCATOpenDataEuskadi>
WHERE { 
	?dataset rdf:type dcat:Dataset .
}
```
URL: https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0A%0ASELECT%20%3Fdataset%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%7D%0A

### Datasets modificados en 2021, ordenados por fecha de modificación (Primero la más antigua)

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

URL: https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0APREFIX%20xsd%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0A%0ASELECT%20%3Fdataset%20%3Ftitle%20%3Fdate_modified%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%3Fdataset%20dct%3Amodified%20%3Fdate_modified%20.%0A%09%3Fdataset%20dct%3Atitle%20%3Ftitle%20.%0A%20%20%20%20FILTER%20(%3Fdate_modified%20%3C%20%222022-01-01T00%3A00%3A00%22%5E%5Exsd%3AdateTime%20%26%26%20%3Fdate_modified%20%3E%20%222021-01-01T00%3A00%3A00%22%5E%5Exsd%3AdateTime)%0A%7D%0AORDER%20BY%20%3Fdate_modified%0A

### Formatos disponibles de distribuciones de datasets

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

URL: https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0A%0ASELECT%20DISTINCT%20%3Fformat_value%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%3Fdataset%20dcat%3Adistribution%20%3Fdistribution%20.%0A%20%20%20%20%3Fdistribution%20dct%3Aformat%20%3Fformat%20.%0A%20%20%20%20%3Fformat%20rdf%3Avalue%20%3Fformat_value%20.%20%0A%7D%0A

### Datasets que tengan distribuciones en formato CSV

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

URL: https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0A%0ASELECT%20DISTINCT%20%3Fdataset%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%3Fdataset%20dcat%3Adistribution%20%3Fdistribution%20.%0A%20%20%20%20%3Fdistribution%20dct%3Aformat%20%3Fformat%20.%0A%20%20%20%20%3Fformat%20rdf%3Avalue%20%22text%2Fcsv%22%20%0A%7D%0A

### Datasets sobre coronavirus

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

URL: https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0A%0ASELECT%20DISTINCT%20%3Fdataset%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%3Fdataset%20dcat%3Akeyword%20%3Fkeywords%20.%0A%20%20%20%20FILTER%20CONTAINS(%3Fkeywords%2C%20%22coronavirus%22)%20.%0A%7D%0A

### Temas sobre los que tratan los datasets actualmente disponibles

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

URL: https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0A%0ASELECT%20DISTINCT%20%3Ftema%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%3Fdataset%20dcat%3Atheme%20%3Ftema%20.%0A%7D%0A

### Datasets sobre urbanismo ordenados cronológicamente

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

URL: https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0APREFIX%20sector%3A%20%3Chttps%3A%2F%2Fdatos.gob.es%2Fkos%2Fsector-publico%2Fsector%2F%3E%0A%0ASELECT%20%3Fdataset%20%3Fdate_modified%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%3Fdataset%20dcat%3Atheme%20sector%3Aurbanismo-infraestructuras%20.%0A%20%20%20%20%3Fdataset%20dct%3Amodified%20%3Fdate_modified%20.%0A%20%20%20%20%3Fdataset%20dct%3Adescription%20%3Fdescription%20.%0A%7D%0AORDER%20BY%20%3Fdate_modified%0A

### Datasets sobre urbanismo que contengan la palabra "Biosfera" en la descripción ordenados cronológicamente

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

URL: https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0APREFIX%20sector%3A%20%3Chttps%3A%2F%2Fdatos.gob.es%2Fkos%2Fsector-publico%2Fsector%2F%3E%0A%0ASELECT%20%3Fdataset%20%3Fdescription%20%3Fdate_modified%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%3Fdataset%20dcat%3Atheme%20sector%3Aurbanismo-infraestructuras%20.%0A%20%20%20%20%3Fdataset%20dct%3Amodified%20%3Fdate_modified%20.%0A%20%20%20%20%3Fdataset%20dct%3Adescription%20%3Fdescription%20.%0A%20%20%20%20FILTER%20CONTAINS(%3Fdescription%2C%20%22Biosfera%22)%20.%0A%7D%0AORDER%20BY%20%3Fdate_modified%0A

### Datasets que cubran un rango de tiempo en los datos propiamente dichos entre 2018 y 2022

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
    FILTER (?end_time < "2022-01-01T00:00:00"^^xsd:dateTime && ?beggining_time > "2018-01-01T00:00:00"^^xsd:dateTime)
}
```

URL: https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0APREFIX%20xsd%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0APREFIX%20time%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F2006%2Ftime%23%3E%0A%0ASELECT%20%3Fdataset%20%3Ftitle%20%3Fbeggining_time%20%3Fend_time%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%3Fdataset%20dct%3Atitle%20%3Ftitle%20.%0A%20%20%20%20%3Fdataset%20dct%3Atemporal%20%3Ftemporal%20.%0A%20%20%20%20%3Ftemporal%20time%3AhasBeginning%20%3Fbeggining%20.%0A%20%20%20%20%3Fbeggining%20time%3AinXSDDateTime%20%3Fbeggining_time%20.%0A%20%20%20%20%3Ftemporal%20time%3AhasEnd%20%3Fend%20.%0A%20%20%20%20%3Fend%20time%3AinXSDDateTime%20%3Fend_time%20.%0A%20%20%20%20FILTER%20(%3Fend_time%20%3C%20%222022-01-01T00%3A00%3A00%22%5E%5Exsd%3AdateTime%20%26%26%20%3Fbeggining_time%20%3E%20%222018-01-01T00%3A00%3A00%22%5E%5Exsd%3AdateTime)%0A%7D%0A

### Datasets que se obtienen con una periodicidad de 3 meses

```sparql
PREFIX dcat: <http://www.w3.org/ns/dcat#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX dct: <http://purl.org/dc/terms/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
PREFIX time: <http://www.w3.org/2006/time#>

SELECT ?dataset ?title 
FROM <http://id.euskadi.eus/graph/DCATOpenDataEuskadi>
WHERE { 
	?dataset rdf:type dcat:Dataset .
    ?dataset dct:title ?title .
    ?dataset dct:accrualPeriodicity ?periodicity .
    ?periodicity rdf:value ?periodicity_value .
    ?periodicity_value time:months "3"^^xsd:integer .
}
```

URL: https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0APREFIX%20xsd%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0APREFIX%20time%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F2006%2Ftime%23%3E%0A%0ASELECT%20%3Fdataset%20%3Ftitle%20%0AFROM%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%0AWHERE%20%7B%20%0A%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%3Fdataset%20dct%3Atitle%20%3Ftitle%20.%0A%20%20%20%20%3Fdataset%20dct%3AaccrualPeriodicity%20%3Fperiodicity%20.%0A%20%20%20%20%3Fperiodicity%20rdf%3Avalue%20%3Fperiodicity_value%20.%0A%20%20%20%20%3Fperiodicity_value%20time%3Amonths%20%223%22%5E%5Exsd%3Ainteger%20.%0A%7D%0A

### Federación básica de SPARQL endpoints

```sparql
PREFIX dcat: <http://www.w3.org/ns/dcat#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX dct: <http://purl.org/dc/terms/>
PREFIX sector: <https://datos.gob.es/kos/sector-publico/sector/>
PREFIX sector_gob: <http://datos.gob.es/kos/sector-publico/sector/>

SELECT ?dataset ?dataset_gob
WHERE { 
    GRAPH <http://id.euskadi.eus/graph/DCATOpenDataEuskadi> {
		?dataset rdf:type dcat:Dataset .
    	?dataset dcat:theme sector:medio-ambiente .
    }
    SERVICE <https://datos.gob.es/virtuoso/sparql> {
        	?dataset_gob rdf:type dcat:Dataset .
        	?dataset_gob dcat:theme sector_gob:medio-ambiente .   
    }
}
```

URL: https://services.euskadi.eus/graphdb/sparql?name=&infer=true&sameAs=true&query=PREFIX%20dcat%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2Fns%2Fdcat%23%3E%0APREFIX%20rdf%3A%20%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX%20dct%3A%20%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2F%3E%0APREFIX%20sector%3A%20%3Chttps%3A%2F%2Fdatos.gob.es%2Fkos%2Fsector-publico%2Fsector%2F%3E%0APREFIX%20sector_gob%3A%20%3Chttp%3A%2F%2Fdatos.gob.es%2Fkos%2Fsector-publico%2Fsector%2F%3E%0A%0ASELECT%20%3Fdataset%20%3Fdataset_gob%0AWHERE%20%7B%20%0A%20%20%20%20GRAPH%20%3Chttp%3A%2F%2Fid.euskadi.eus%2Fgraph%2FDCATOpenDataEuskadi%3E%20%7B%0A%09%09%3Fdataset%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%09%3Fdataset%20dcat%3Atheme%20sector%3Amedio-ambiente%20.%0A%20%20%20%20%7D%0A%20%20%20%20SERVICE%20%3Chttps%3A%2F%2Fdatos.gob.es%2Fvirtuoso%2Fsparql%3E%20%7B%0A%20%20%20%20%20%20%20%20%09%3Fdataset_gob%20rdf%3Atype%20dcat%3ADataset%20.%0A%20%20%20%20%20%20%20%20%09%3Fdataset_gob%20dcat%3Atheme%20sector_gob%3Amedio-ambiente%20.%20%20%20%0A%20%20%20%20%7D%0A%7D%0A
