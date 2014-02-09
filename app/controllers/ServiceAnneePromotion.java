package controllers;

import java.util.ArrayList;
import java.util.List;

import models.AnneePromotion;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import dao.AnneePromotionDao;

public class ServiceAnneePromotion extends Controller {

	public static List<AnneePromotion> listeDesAnneesdePromotion() {
		return AnneePromotionDao.find.orderBy("libelle asc").findList();
	}

	public static Result demandeAJAX_listeDesAnneesdePromotion() {
		return envoiAJAX_listeDesAnneesdePromotion();
	}

	public static Result envoiAJAX_listeDesAnneesdePromotion() {
		String sql = "SELECT anneePromotion_libelle FROM AnneePromotion ORDER BY anneePromotion_libelle DESC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesAnneesdePromotion = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow) {
			listeDesAnneesdePromotion.add(sqlRow.get("anneePromotion_libelle")
			        .toString());
		}

		// List<AnneePromotion> listeDesAnneesdePromotion =
		// AnneePromotionDao.find
		// .select("libelle")/*.orderBy("libelle asc")*/.findList();
		return ok(Json.toJson(listeDesAnneesdePromotion));
	}

}
