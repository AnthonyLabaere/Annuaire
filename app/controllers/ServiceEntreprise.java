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
	
	public static Result AJAX_listeDesEntreprises() {
		String sql = "SELECT entreprise_nom FROM Entreprise ORDER BY entreprise_nom ASC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesEntreprises = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow) {
			listeDesEntreprises.add(sqlRow.get("entreprise_nom").toString());
		}

		return ok(Json.toJson(listeDesEntreprises));
	}
	
	// TODO : essayer d'arreter le doublon de requetes AJAX
	// Les filtres sont activés par l'utilisateur. Sinon un NULL est mis dans le
	// parametre et donc ce n'est pas pris en compte dans cette fonction. Et si
	// on le prend en compte on rajoute un Where dans la requete SQL
	// Les memes fonctions sont a implementer pour les autres filtres !
	// Ajouter un bouton "reinitialise les filtres"
	// Petite légende pour préciser que les filtres sont dynamiques
	public static Result AJAX_listeDesEntreprisesSelonCriteres(
			String anneePromotion_libelle, String secteur_nom, String pays_nom,
			String ville_nom) {
		return null;
	}
}
