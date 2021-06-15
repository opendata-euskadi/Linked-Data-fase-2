package eus.ehu.nora.entity;

import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import eus.ehu.nora.graphdb.AddRDFtoGraphDB;
import eus.ehu.nora.graphdb.Util;
import eus.ehu.nora.uris.DublinCoreURIs;
import eus.ehu.nora.uris.ESADMURIs;
import eus.ehu.nora.uris.EuskadiURIs;
import eus.ehu.nora.uris.GeoNamesURIs;
import eus.ehu.nora.uris.NORABaseURIs;
import eus.ehu.nora.uris.SchemaURIs;

public class Country implements AddRDFtoGraphDB{
	
	private String rdftype;
	private String country;
	private String country_id;
	private String officialName;
	private String esDescripcion;
	private String euDescripcion;

	public Country (String rdftype, String country_iri, String country_id, String officialName, String esDEscripcion, String euDescripcion) {
		this.rdftype = rdftype;
		this.country = country_iri;
		this.country_id = country_id;
		this.officialName = officialName;
		this.esDescripcion = esDEscripcion;
		this.euDescripcion = euDescripcion;
	}

	@Override
	public void add(RepositoryConnection connection, String namedGraph) {
		Util.addIRITriple(
				country, 
				RDF.TYPE.stringValue(),
				rdftype, 
				namedGraph, 
				connection);
		
		Util.addIRITriple(
				country,
				GeoNamesURIs.featureCode.getURI(),
				GeoNamesURIs.admd.getURI(),
				namedGraph,
				connection);
		
		Util.addIRITriple(
				country,
				GeoNamesURIs.featureClass.getURI(),
				GeoNamesURIs.a.getURI(),
				namedGraph,
				connection);
		
		Util.addLiteralTriple(
				country,
				SchemaURIs.identifier.getURI(),
				country_id,
				namedGraph,
				connection);
		
		Util.addLiteralTriple(
				country,
				DublinCoreURIs.identifier.getURI(),
				country_id,
				namedGraph,
				connection);

		Util.addLiteralTriple(
				country,
				EuskadiURIs.identifier.getURI(),
				country_id,
				namedGraph,
				connection);
		
		Util.addLiteralTriple(
				country,
				GeoNamesURIs.officialname.getURI(),
				officialName,
				namedGraph,
				connection);
		
		Util.addLiteralTripleLang(
				country,
				RDFS.LABEL.stringValue(),
				esDescripcion,
				"es",
				namedGraph,
				connection);
		
		Util.addLiteralTripleLang(
				country,
				RDFS.LABEL.stringValue(),
				euDescripcion,
				"eu",
				namedGraph,
				connection);
	}
}
