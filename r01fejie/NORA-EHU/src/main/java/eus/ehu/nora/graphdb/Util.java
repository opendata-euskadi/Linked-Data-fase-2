package eus.ehu.nora.graphdb;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.repository.RepositoryConnection;

public abstract class Util {
	
	public static void clearGraph (String graphURI, RepositoryConnection connection) {
		IRI graph = Values.iri(graphURI);
		connection.clear(graph);
	}
		
	public static void addIRITriple (String s, String p, String o, String namedGraph, RepositoryConnection connection) {
		IRI sub= Values.iri(s);
		IRI pred = Values.iri(p);
		IRI obj = Values.iri(o);
		IRI namedgraphIRI = Values.iri(namedGraph);
		connection.add(sub, pred, obj, namedgraphIRI);
	}
	
	public static void addLiteralTriple (String s, String p, String o, String namedGraph, RepositoryConnection connection) {
		IRI sub= Values.iri(s);
		IRI pred = Values.iri(p);
		Literal obj = Values.literal(o);
		IRI namedgraphIRI = Values.iri(namedGraph);
		connection.add(sub, pred, obj, namedgraphIRI);
	}
	
	public static void addLiteralTriple (String s, String p, double o, String namedGraph, RepositoryConnection connection) {
		IRI sub= Values.iri(s);
		IRI pred = Values.iri(p);
		Literal obj = Values.literal(o);
		IRI namedgraphIRI = Values.iri(namedGraph);
		connection.add(sub, pred, obj, namedgraphIRI);
	}
	
	public static void addLiteralTripleLang (String s, String p, String o, String lang, String namedGraph, RepositoryConnection connection) {
		IRI sub= Values.iri(s);
		IRI pred = Values.iri(p);
		Literal obj = Values.literal(o,lang);
		IRI namedgraphIRI = Values.iri(namedGraph);
		connection.add(sub, pred, obj, namedgraphIRI);
	}
}
