package eus.ehu.nora.entity;

import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import eus.ehu.nora.graphdb.AddRDFtoGraphDB;
import eus.ehu.nora.graphdb.Util;
import eus.ehu.nora.uris.DublinCoreURIs;
import eus.ehu.nora.uris.EuskadiURIs;
import eus.ehu.nora.uris.GeoNamesURIs;
import eus.ehu.nora.uris.SchemaURIs;

public class Basic implements AddRDFtoGraphDB{
	protected String entity_rdftype;
	protected String entity_iri;
	protected String entity__id;
	protected String officialName;
	protected String esDescripcion;
	protected String euDescripcion;

	public Basic(String entity_rdftype, String entity_iri, String entity__id, String officialName,
			String esDescripcion, String euDescripcion) {
		this.entity_rdftype = entity_rdftype;
		this.entity_iri = entity_iri;
		this.entity__id = entity__id;
		this.officialName = officialName;
		this.esDescripcion = esDescripcion;
		this.euDescripcion = euDescripcion;
	}

	@Override
	public void add(RepositoryConnection connection, String namedGraph) {
		Util.addIRITriple(
				entity_iri, 
				RDF.TYPE.stringValue(),
				entity_rdftype, 
				namedGraph, 
				connection);

		Util.addLiteralTriple(
				entity_iri,
				SchemaURIs.identifier.getURI(),
				entity__id,
				namedGraph,
				connection);
		
		Util.addLiteralTriple(
				entity_iri,
				DublinCoreURIs.identifier.getURI(),
				entity__id,
				namedGraph,
				connection);

		Util.addLiteralTriple(
				entity_iri,
				EuskadiURIs.identifier.getURI(),
				entity__id,
				namedGraph,
				connection);
		
		Util.addLiteralTriple(
				entity_iri,
				GeoNamesURIs.officialname.getURI(),
				officialName,
				namedGraph,
				connection);
		
		Util.addLiteralTripleLang(
				entity_iri,
				RDFS.LABEL.stringValue(),
				esDescripcion,
				"es",
				namedGraph,
				connection);
		
		Util.addLiteralTripleLang(
				entity_iri,
				RDFS.LABEL.stringValue(),
				euDescripcion,
				"eu",
				namedGraph,
				connection);
	}
}