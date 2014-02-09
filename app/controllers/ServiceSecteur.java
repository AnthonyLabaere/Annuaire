package controllers;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import models.Secteur;
import dao.SecteurDao;

public class ServiceSecteur extends Controller {

	public static List<Secteur> listeDesSecteurs() {
		return SecteurDao.find.orderBy("nom asc").findList();
	}
	
	public static Result demandeAJAX_listeDesSecteurs() {
		return envoiAJAX_listeDesSecteurs();
	}

	public static Result envoiAJAX_listeDesSecteurs() {
		String sql = "SELECT secteur_nom FROM Secteur ORDER BY secteur_nom ASC";
		 
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);		 
		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesSecteurs = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow){
			listeDesSecteurs.add(sqlRow.get("secteur_nom").toString());
		}
		
		return ok(Json.toJson(listeDesSecteurs));
	}

}
