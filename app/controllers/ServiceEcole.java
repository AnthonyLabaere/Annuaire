package controllers;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import models.Ecole;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import dao.EcoleDao;

public class ServiceEcole extends Controller {

	public static List<Ecole> listeDesEcoles() {
		return EcoleDao.find.orderBy("nom asc").findList();
	}

	public static Result demandeAJAX_listeDesEcoles() {
		return envoiAJAX_listeDesEcoles();
	}

	public static Result envoiAJAX_listeDesEcoles() {
//		List<Ecole> listeDesEcoles = EcoleDao.find.select("nom").orderBy("nom asc")
//		        .findList();
		String sql = "SELECT ecole_nom FROM Ecole";
		 
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);		 
		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesEcoles = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow){
			listeDesEcoles.add(sqlRow.get("ecole_nom").toString());
		}
		
		return ok(Json.toJson(listeDesEcoles));
	}

}
