package eus.ehu.nora.entity;

import org.eclipse.rdf4j.repository.RepositoryConnection;

import eus.ehu.nora.graphdb.AddRDFtoGraphDB;
import eus.ehu.nora.graphdb.Util;
import eus.ehu.nora.uris.ESADMURIs;
import eus.ehu.nora.uris.NORABaseURIs;

public class State extends Country implements AddRDFtoGraphDB {

	private String country_id;
	private String state_id;
	private String state_iri;

	public State(String rdftype, String state_iri, String state_id, String officialName, String esDEscripcion, String euDescripcion, String countryID) {
		super(rdftype, state_iri, state_id, officialName, esDEscripcion, euDescripcion);
		this.country_id = countryID;
		this.state_id = state_id;
		this.state_iri = state_iri;
	}
	
	@Override
	public void add(RepositoryConnection connection, String namedGraph) {
		super.add(connection, namedGraph);
				
		Util.addIRITriple(
				state_iri, 
				ESADMURIs.paisProp.getURI(),
				NORABaseURIs.COUNTRY.getURI()+country_id, 
				namedGraph, 
				connection);	
	}
}
