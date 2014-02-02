package dao;

import models.AnneePromotion;
import models.Pays;
import play.db.ebean.Model.Finder;


/**
 * 
 * @author alabaere
 * 
 * @param <T>
 */
public class AnneePromotionDao{
	
	public static Finder<Integer, AnneePromotion> find = new Finder<Integer, AnneePromotion>(
			Integer.class, AnneePromotion.class);
	

}
