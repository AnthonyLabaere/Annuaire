package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Pays;
import models.Ville;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import dao.VilleDao;

public class ServiceVille extends Controller {

	public static List<Ville> listeDesVilles() {
		return VilleDao.find.orderBy("nom desc").findList();
	}

	public static List<Ville> listeDesVillesDuPays(Pays pays) {
		return VilleDao.find.where(Expr.eq("ville", pays)).findList();
	}

	public static Result demandeAJAX_listeDesVillesDuPays(String pays_nom) {
		return envoiAJAX_listeDesVillesDuPays(pays_nom);
	}

	public static Result envoiAJAX_listeDesVillesDuPays(String pays_nom) {
		String sqlPays = "SELECT pays_ID FROM Pays WHERE pays_nom = :pays_nom";

		SqlQuery sqlQueryPays = Ebean.createSqlQuery(sqlPays);
		sqlQueryPays.setParameter("pays_nom", pays_nom);
		SqlRow sqlRowPays = sqlQueryPays.findUnique();
		Integer pays_ID = Integer
		        .parseInt(sqlRowPays.get("pays_ID").toString());

		String sqlVille = "SELECT ville_nom FROM Ville WHERE ville_pays_ID = :pays_ID ORDER BY ville_nom ASC";

		SqlQuery sqlQueryVille = Ebean.createSqlQuery(sqlVille);
		sqlQueryVille.setParameter("pays_ID", pays_ID);

		List<SqlRow> listSqlRowVille = sqlQueryVille.findList();
		List<String> listeDesVilles = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRowVille) {
			listeDesVilles.add(sqlRow.get("ville_nom").toString());
		}

		return ok(Json.toJson(listeDesVilles));
	}
}
