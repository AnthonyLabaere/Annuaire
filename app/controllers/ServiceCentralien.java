package controllers;

import java.util.ArrayList;
import java.util.List;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

public class ServiceCentralien extends Controller {

	public static Result AJAX_listeDesCentraliens() {
		String sql = "SELECT centralien_nom FROM Centralien ORDER BY centralien_nom DESC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesCentraliens = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow) {
			listeDesCentraliens.add(sqlRow.get("centralien_nom").toString());
		}

		return ok(Json.toJson(listeDesCentraliens));
	}

}
