package controllers;

import java.util.ArrayList;
import java.util.List;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

public class ServicePays extends Controller {

	public static Result AJAX_listeDesPays() {
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
