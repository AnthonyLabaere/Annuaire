package dao;

import models.Entreprise;
import play.db.ebean.Model.Finder;


/**
 * 
 * @author alabaere
 * 
 * @param <T>
 */
public class EntrepriseDao{
	
	public static Finder<Integer, Entreprise> find = new Finder<Integer, Entreprise>(
			Integer.class, Entreprise.class);
	

}
