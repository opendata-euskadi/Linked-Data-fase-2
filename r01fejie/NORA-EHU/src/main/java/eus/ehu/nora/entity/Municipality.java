package eus.ehu.nora.entity;

import org.eclipse.rdf4j.repository.RepositoryConnection;

import eus.ehu.nora.graphdb.AddRDFtoGraphDB;
import eus.ehu.nora.graphdb.Util;
import eus.ehu.nora.uris.ESADMURIs;
import eus.ehu.nora.uris.GeoURIs;
import eus.ehu.nora.uris.NORABaseURIs;

public class Municipality extends GeoNamesPPLEntity implements AddRDFtoGraphDB {

	private String countyID;
	private double xetrs89;
	private double yetrs89;

	public Municipality(String entity_rdftype, String entity_iri, String entity__id, String officialName,
			String esDescripcion, String euDescripcion, String countyID, double xetrs89, double yetrs89) {
		super(entity_rdftype, entity_iri, entity__id, officialName,
				esDescripcion, euDescripcion);
		this.countyID = countyID;
		this.xetrs89 = xetrs89;
		this.yetrs89 = yetrs89;
	}
	
	@Override
	public void add(RepositoryConnection connection, String namedGraph) {
		super.add(connection, namedGraph);
				
		Util.addIRITriple(
				entity_iri, 
				ESADMURIs.provinciaProp.getURI(),
				NORABaseURIs.PROVINCE.getURI()+countyID, 
				namedGraph, 
				connection);
		if(xetrs89 != 0.0 && yetrs89 != 0.0) {
			Util.addLiteralTriple(entity_iri, GeoURIs.xETRS89.getURI(), xetrs89, namedGraph, connection);
			Util.addLiteralTriple(entity_iri, GeoURIs.yETRS89.getURI(), yetrs89, namedGraph, connection);
		}
		
	}
}
