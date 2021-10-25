package eus.ehu.nora.entity;

import org.eclipse.rdf4j.repository.RepositoryConnection;

import eus.ehu.nora.graphdb.AddRDFtoGraphDB;
import eus.ehu.nora.graphdb.Util;
import eus.ehu.nora.uris.ESADMURIs;
import eus.ehu.nora.uris.NORABaseURIs;

public class Locality extends GeoNamesPPLEntity implements AddRDFtoGraphDB {

	private String municipalityID;
	private String countyID;

	public Locality(String entity_rdftype, String entity_iri, String entity__id, String officialName,
			String esDescripcion, String euDescripcion, String municipalityID, String countyID) {
		super(entity_rdftype, entity_iri, entity__id, officialName,
				esDescripcion, euDescripcion);
		this.municipalityID = municipalityID;
		this.countyID = countyID;
	}
	
	@Override
	public void add(RepositoryConnection connection, String namedGraph) {
		super.add(connection, namedGraph);
				
		Util.addIRITriple(
				entity_iri, 
				ESADMURIs.municipioProp.getURI(),
				NORABaseURIs.MUNICIPALITY.getURI()+ countyID + "-" + municipalityID, namedGraph, 
				connection);
		
	}
}

