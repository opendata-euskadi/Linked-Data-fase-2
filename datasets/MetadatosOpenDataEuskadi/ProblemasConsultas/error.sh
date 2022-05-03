curl -X POST https://id.euskadi.eus/sparql/ \
	-H 'accept: application/sparql-results+json,*/*;q=0.9' \
	-H 'content-type: application/x-www-form-urlencoded' \
	-d query='PREFIX+dcat:+<http://www.w3.org/ns/dcat#>PREFIX+rdf:+<http://www.w3.org/1999/02/22-rdf-syntax-ns#>PREFIX+dct:+<http://purl.org/dc/terms/>PREFIX+xsd:+<http://www.w3.org/2001/XMLSchema#>SELECT+?dataset+?title+?date_modified+FROM+<http://id.euskadi.eus/graph/DCATOpenDataEuskadi>WHERE+{+++?dataset+rdf:type+dcat:Dataset+.++?dataset+dct:modified+?date_modified+.++?dataset+dct:title+?title+.++FILTER+(?date_modified+<+"2022-01-01T00:00:00"^^xsd:dateTime+&&+?date_modified+>+"2021-01-01T00:00:00"^^xsd:dateTime)}ORDER+BY+?date_modified'



	# PREFIX+dcat:+<http://www.w3.org/ns/dcat#>PREFIX+rdf:+<http://www.w3.org/1999/02/22-rdf-syntax-ns#>PREFIX+dct:+<http://purl.org/dc/terms/>PREFIX+xsd:+<http://www.w3.org/2001/XMLSchema#>SELECT+?dataset+?title+?date_modified+FROM+<http://id.euskadi.eus/graph/DCATOpenDataEuskadi>WHERE+{+++?dataset+rdf:type+dcat:Dataset+.++?dataset+dct:modified+?date_modified+.++?dataset+dct:title+?title+.++FILTER+(?date_modified+<+"2022-01-01T00:00:00"++++^^xsd:dateTime+&&++++?date_modified+>+"2021-01-01T00:00:00"^^xsd:dateTime)}ORDER+BY+?date_modified


	# PREFIX+dcat:+<http://www.w3.org/ns/dcat#>PREFIX+rdf:+<http://www.w3.org/1999/02/22-rdf-syntax-ns#>PREFIX+dct:+<http://purl.org/dc/terms/>PREFIX+xsd:+<http://www.w3.org/2001/XMLSchema#>SELECT+?dataset+?title+?date_modified+FROM+<http://id.euskadi.eus/graph/DCATOpenDataEuskadi>WHERE+{+++?dataset+rdf:type+dcat:Dataset+.++?dataset+dct:modified+?date_modified+.++?dataset+dct:title+?title+.++FILTER+(?date_modified+<+"2022-01-01T00:00:00"^^xsd:dateTime+&&+?date_modified+>+"2021-01-01T00:00:00"^^xsd:dateTime)}ORDER+BY+?date_modified