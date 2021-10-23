package eus.ehu.nora.entity;

import org.eclipse.rdf4j.repository.RepositoryConnection;

import eus.ehu.nora.graphdb.AddRDFtoGraphDB;
import eus.ehu.nora.graphdb.Util;
import eus.ehu.nora.uris.EuskadiURIs;
import eus.ehu.nora.uris.GeoURIs;
import eus.ehu.nora.uris.NORABaseURIs;

public class Street extends GeoNamesRSTEntity implements AddRDFtoGraphDB {

	private String localityID;
	private double xetrs89;
	private double yetrs89;

	public Street(String entity_rdftype, String entity_iri, String entity__id, String officialName,
			String esDescripcion, String euDescripcion, String localityID, double xetrs89, double yetrs89) {
		super(entity_rdftype, entity_iri, entity__id, officialName,
				esDescripcion, euDescripcion);
		this.localityID = localityID;
		this.xetrs89 = xetrs89;
		this.yetrs89 = yetrs89;
	}
	
	@Override
	public void add(RepositoryConnection connection, String namedGraph) {
		super.add(connection, namedGraph);
				
		Util.addIRITriple(
				entity_iri, 
				EuskadiURIs.localidad.getURI(),
				NORABaseURIs.LOCALITY.getURI() + localityID, 
				namedGraph, 
				connection);
		
		Util.addLiteralTriple(entity_iri, GeoURIs.xETRS89.getURI(), xetrs89, namedGraph, connection);
		Util.addLiteralTriple(entity_iri, GeoURIs.yETRS89.getURI(), yetrs89, namedGraph, connection);
		
	}
}
