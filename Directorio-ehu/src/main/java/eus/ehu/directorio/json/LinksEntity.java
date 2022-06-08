package eus.ehu.directorio.json;

import java.util.List;

public class LinksEntity {
	public String mainEntityOfPage;
	public List <Link> legalFramework;
	public List <Link> entitesAbove; // Fallo tipografico en los JSON: https://labur.eus/hjWZP
	public List <Link> entitiesBelow;
    public List <Link> people;
}
