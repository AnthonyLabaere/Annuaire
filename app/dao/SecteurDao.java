package dao;

import models.Secteur;
import play.db.ebean.Model.Finder;


/**
 * 
 * @author alabaere
 * 
 * @param <T>
 */
public class SecteurDao{
	
	public static Finder<Integer, Secteur> find = new Finder<Integer, Secteur>(
			Integer.class, Secteur.class);
	

}
