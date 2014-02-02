package dao;

import models.Ecole;
import play.db.ebean.Model.Finder;


/**
 * 
 * @author alabaere
 * 
 * @param <T>
 */
public class EcoleDao{
	
	public static Finder<Integer, Ecole> find = new Finder<Integer, Ecole>(
			Integer.class, Ecole.class);
	

}
