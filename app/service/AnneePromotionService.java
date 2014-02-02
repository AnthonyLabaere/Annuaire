package service;

import java.util.List;

import models.AnneePromotion;
import dao.AnneePromotionDao;

public class AnneePromotionService {
	
	public static List<AnneePromotion> listeDesAnneesdePromotion(){
		return AnneePromotionDao.find.orderBy("libelle asc").findList();
	}
	
}
