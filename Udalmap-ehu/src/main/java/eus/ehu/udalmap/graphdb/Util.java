package eus.ehu.udalmap.graphdb;

import java.io.FileOutputStream;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

public class Util {
	private Model model;
	
	public Util () {
		model = new TreeModel();
	}
	
	public void flushModel (FileOutputStream fileoutputstream) {
		Rio.write(model, fileoutputstream, RDFFormat.TURTLE);
	}
	
	public void clearGraph (String graphURI, RepositoryConnection connection) {
		IRI graph = Values.iri(graphURI);
		connection.clear(graph);
	}
		
	public void addIRITriple (String s, String p, String o, String namedGraph, RepositoryConnection connection) {
		IRI sub= Values.iri(s);
		IRI pred = Values.iri(p);
		IRI obj = Values.iri(o);
		IRI namedgraphIRI = Values.iri(namedGraph);
		connection.add(sub, pred, obj, namedgraphIRI);
		model.add(sub,pred,obj);
	}
	
	public void addLiteralTriple (String s, String p, String o, String namedGraph, RepositoryConnection connection) {
		IRI sub= Values.iri(s);
		IRI pred = Values.iri(p);
		if (o != null && !o.isEmpty()) {
			Literal obj = Values.literal(o);
			IRI namedgraphIRI = Values.iri(namedGraph);
			connection.add(sub, pred, obj, namedgraphIRI);
			model.add(sub,pred,obj);
		}
	}
	
	public void addLiteralTriple (String s, String p, double o, String namedGraph, RepositoryConnection connection) {
		IRI sub= Values.iri(s);
		IRI pred = Values.iri(p);
		if (o != 0.0) {
			Literal obj = Values.literal(o);
			IRI namedgraphIRI = Values.iri(namedGraph);
			connection.add(sub, pred, obj, namedgraphIRI);
			model.add(sub,pred,obj);
		}
	}
	
	public void addLiteralTripleLang (String s, String p, String o, String lang, String namedGraph, RepositoryConnection connection) {
		IRI sub= Values.iri(s);
		IRI pred = Values.iri(p);
		if (o != null && !o.isEmpty()) {
			Literal obj = Values.literal(o,lang);
			IRI namedgraphIRI = Values.iri(namedGraph);
			connection.add(sub, pred, obj, namedgraphIRI);
			model.add(sub,pred,obj);
		}
	}
}
