package dao;

import models.Ville;
import play.db.ebean.Model.Finder;


/**
 * 
 * @author alabaere
 * 
 * @param <T>
 */
public class VilleDao{
	
	public static Finder<Integer, Ville> find = new Finder<Integer, Ville>(
			Integer.class, Ville.class);
	

}
