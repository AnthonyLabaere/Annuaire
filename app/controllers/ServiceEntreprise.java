package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Entreprise;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import dao.EntrepriseDao;

public class ServiceEntreprise extends Controller {

	public static List<Entreprise> listeDesEntreprises() {
		return EntrepriseDao.find.orderBy("nom asc").findList();
	}

	public static Result demandeAJAX_listeDesEntreprises() {
		return envoiAJAX_listeDesEntreprises();
	}

	public static Result envoiAJAX_listeDesEntreprises() {
		String sql = "SELECT entreprise_nom FROM Entreprise ORDER BY entreprise_nom ASC";
		 
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);		 
		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesEntreprises = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow){
			listeDesEntreprises.add(sqlRow.get("entreprise_nom").toString());
		}
		
		return ok(Json.toJson(listeDesEntreprises));
	}
}
