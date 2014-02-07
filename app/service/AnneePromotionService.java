package service;

import java.util.List;

import models.AnneePromotion;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import dao.AnneePromotionDao;

public class AnneePromotionService extends Controller {

	public static Result demandeAJAX_listeDesAnneesdePromotion(String nom) {
		return envoiAJAX_listeDesAnneesdePromotion();
	}

	public static Result envoiAJAX_listeDesAnneesdePromotion() {
		List<AnneePromotion> listeDesAnneesdePromotion = AnneePromotionDao.find
		        .orderBy("libelle asc").findList();
		return ok(Json.toJson(listeDesAnneesdePromotion));
	}

}
