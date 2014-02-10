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

	// TODO
	// Les filtres sont activés par l'utilisateur. Sinon un NULL est mis dans le
	// parametre et donc ce n'est pas pris en compte dans cette fonction. Et si
	// on le prend en compte on rajoute un Where dans la requete SQL
	// Les memes fonctions sont a implementer pour les autres filtres !
	// Ajouter un bouton "reinitialise les filtres"
	// Petite légende pour préciser que les filtres sont dynamiques (dans un
	// bouton aide "?")
	public static Result AJAX_listeDesEntreprisesSelonCriteres(
	        String anneePromotion_libelle, String secteur_nom, String pays_nom,
	        String ville_nom) {
		Boolean[] parametresPresents = new Boolean[] {
		        anneePromotion_libelle != null
		                && !anneePromotion_libelle.isEmpty(),
		        secteur_nom != null && !secteur_nom.isEmpty(),
		        pays_nom != null && !pays_nom.isEmpty(),
		        ville_nom != null && !ville_nom.isEmpty() };

		Boolean wherePlace = false;

		String sql = "SELECT entreprise_nom FROM Entreprise";

		if (parametresPresents[0]) {
			wherePlace = true;
			sql += " WHERE ";
			sql += "entreprise_ID IN (";
			sql += "SELECT entreprisePersonne_entreprise_ID FROM EntreprisePersonne, Personne WHERE personne_anneePromotion_ID IN (";
			sql += "SELECT anneePromotion_ID FROM anneePromotion WHERE anneePromotion_libelle = :anneePromotion_libelle";
			sql += ")";
			sql += " AND "; 
			sql += "personne_ID = entreprisePersonne_personne_ID";
			sql += ")";
		}

		if (parametresPresents[1]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "entreprise_ID IN (SELECT entrepriseSecteur_entreprise_ID FROM EntrepriseSecteur, Secteur WHERE secteur_nom = :secteur_nom AND secteur_ID = entrepriseSecteur_secteur_ID)";
		}

		if (parametresPresents[2] && !parametresPresents[3]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "entreprise_ID IN (SELECT entrepriseVille_entreprise_ID FROM EntrepriseVille, Ville WHERE ville_pays_ID = (SELECT pays_ID FROM Pays WHERE pays_nom = :pays_nom) AND ville_ID = entrepriseVille_ville_ID)";
		}

		if (parametresPresents[3]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "entreprise_ID IN (SELECT entrepriseVille_entreprise_ID FROM EntrepriseVille, Ville WHERE ville_nom = :ville_nom AND ville_ID = entrepriseVille_ville_ID)";
		}

		sql += " ORDER BY entreprise_nom ASC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		if (parametresPresents[0]) {
			sqlQuery.setParameter("anneePromotion_libelle",
			        Integer.parseInt(anneePromotion_libelle));
		}
		if (parametresPresents[1]) {
			sqlQuery.setParameter("secteur_nom", secteur_nom);
		}
		if (parametresPresents[2] && !parametresPresents[3]) {
			sqlQuery.setParameter("pays_nom", pays_nom);
		}
		if (parametresPresents[3]) {
			sqlQuery.setParameter("ville_nom", ville_nom);
		}

		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesEntreprisesParCriteres = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow) {
			listeDesEntreprisesParCriteres.add(sqlRow.get("entreprise_nom")
			        .toString());
		}

		return ok(Json.toJson(listeDesEntreprisesParCriteres));
	}
}
