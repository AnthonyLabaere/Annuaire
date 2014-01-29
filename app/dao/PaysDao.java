package dao;

import models.Pays;
import play.db.ebean.Model.Finder;


/**
 * 
 * @author alabaere
 * 
 * @param <T>
 */
public class PaysDao{
	
	public static Finder<Integer, Pays> find = new Finder<Integer, Pays>(
			Integer.class, Pays.class);
	

}
