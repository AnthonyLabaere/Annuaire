package dao;

import models.Personne;
import play.db.ebean.Model.Finder;


/**
 * 
 * @author alabaere
 * 
 * @param <T>
 */
public class PersonneDao{
	
	public static Finder<Integer, Personne> find = new Finder<Integer, Personne>(
			Integer.class, Personne.class);
	

}
