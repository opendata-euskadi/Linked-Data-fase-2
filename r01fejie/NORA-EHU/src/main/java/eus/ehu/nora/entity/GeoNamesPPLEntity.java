package eus.ehu.nora.entity;

import org.eclipse.rdf4j.repository.RepositoryConnection;

import eus.ehu.nora.graphdb.AddRDFtoGraphDB;
import eus.ehu.nora.graphdb.Util;
import eus.ehu.nora.uris.GeoNamesURIs;


public class GeoNamesPPLEntity extends Basic implements AddRDFtoGraphDB{
	
	public GeoNamesPPLEntity (String entity_rdftype, String entity_iri, String entity__id, String officialName,
			String esDescripcion, String euDescripcion) {
			super(entity_rdftype, entity_iri, entity__id, officialName, esDescripcion, euDescripcion);
	}

	@Override
	public void add(RepositoryConnection connection, String namedGraph) {
		super.add(connection, namedGraph);
		
		Util.addIRITriple(
				entity_iri,
				GeoNamesURIs.featureCode.getURI(),
				GeoNamesURIs.pppl.getURI(),
				namedGraph,
				connection);
		
		Util.addIRITriple(
				entity_iri,
				GeoNamesURIs.featureClass.getURI(),
				GeoNamesURIs.p.getURI(),
				namedGraph,
				connection);
		
//        geo:xETRS89            "dxEtrs89"^^xsd:double ;
//        geo:yETRS89            "dyEtrs89"^^xsd:double ;
	}
}
