package controllers;

import java.util.ArrayList;
import java.util.List;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import models.Pays;
import dao.PaysDao;
import dao.VilleDao;

public class ServicePays extends Controller {
	
	public static List<Pays> listeDesPays(){
		return PaysDao.find.orderBy("nom asc").findList();
	}
	
	public static Pays PaysDeNom(String nom){
		return PaysDao.find.where(Expr.eq("nom", nom)).findUnique();
	}
	
	public static Result demandeAJAX_listeDesPays() {
		return envoiAJAX_listeDesPays();
	}

	public static Result envoiAJAX_listeDesPays() {
		String sql = "SELECT pays_nom FROM Pays ORDER BY pays_nom ASC";
		 
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);		 
		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesPays = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow){
			listeDesPays.add(sqlRow.get("pays_nom").toString());
		}
		
		return ok(Json.toJson(listeDesPays));
	}

}
