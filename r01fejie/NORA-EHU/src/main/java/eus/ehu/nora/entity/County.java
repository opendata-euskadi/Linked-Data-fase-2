package eus.ehu.nora.entity;

import org.eclipse.rdf4j.repository.RepositoryConnection;

import eus.ehu.nora.graphdb.AddRDFtoGraphDB;
import eus.ehu.nora.graphdb.Util;
import eus.ehu.nora.uris.ESADMURIs;
import eus.ehu.nora.uris.NORABaseURIs;

public class County extends Country implements AddRDFtoGraphDB {

	private String state_id;
	private String county_id;
	private String county_iri;

	public County(String rdftype, String county_iri,String countyID, String officialName, String esDEscripcion, String euDescripcion, String stateID) {
		super(rdftype, county_iri, countyID, officialName, esDEscripcion, euDescripcion);
		this.state_id = stateID;
		this.county_id = countyID;
		this.county_iri = county_iri;
	}
	
	@Override
	public void add(RepositoryConnection connection, String namedGraph) {
		super.add(connection, namedGraph);
				
		Util.addIRITriple(
				county_iri, 
				ESADMURIs.autonomiaProp.getURI(),
				NORABaseURIs.AUTONOMOUS_COMMUNITY.getURI()+state_id, 
				namedGraph, 
				connection);	
	}
}
