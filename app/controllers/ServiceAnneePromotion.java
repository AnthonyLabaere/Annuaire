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

	public static List<AnneePromotion> listeDesAnneesPromotion() {
		return AnneePromotionDao.find.orderBy("libelle asc").findList();
	}

	public static Result AJAX_listeDesAnneesPromotion() {
		String sql = "SELECT anneePromotion_libelle FROM AnneePromotion ORDER BY anneePromotion_libelle DESC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesAnneesPromotion = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow) {
			listeDesAnneesPromotion.add(sqlRow.get("anneePromotion_libelle")
			        .toString());
		}

		return ok(Json.toJson(listeDesAnneesPromotion));
	}

	public static Result AJAX_listeDesAnneesPromotionSelonCriteres(
	        String entreprise_nom, String secteur_nom, String pays_nom,
	        String ville_nom) {
		Boolean[] parametresPresents = new Boolean[] {
		        entreprise_nom != null && !entreprise_nom.isEmpty(),
		        secteur_nom != null && !secteur_nom.isEmpty(),
		        pays_nom != null && !pays_nom.isEmpty(),
		        ville_nom != null && !ville_nom.isEmpty() };

		Boolean wherePlace = false;

		String sql = "SELECT anneePromotion_libelle FROM AnneePromotion";

		if (parametresPresents[0]) {
			wherePlace = true;
			sql += " WHERE ";
			// TODO : ajouter l'ecole !
			sql += "anneePromotion_ID IN (";
			sql += "SELECT personne_anneePromotion_ID FROM Personne WHERE personne_ID IN (";
			sql += "SELECT entreprisePersonne_ID FROM EntreprisePersonne WHERE entreprisePersonne_entreprise_ID = (";
			sql += "SELECT entreprise_ID FROM Entreprise WHERE entreprise_nom = :entreprise_nom";
			sql += ")))";
		}

		if (parametresPresents[1]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "anneePromotion_ID IN (";
			sql += "SELECT personne_anneePromotion_ID FROM Personne WHERE personne_ID IN (";
			sql += "SELECT entreprisePersonne_ID FROM EntreprisePersonne WHERE entreprisePersonne_entreprise_ID IN (";
			sql += "SELECT entrepriseSecteur_entreprise_ID FROM EntrepriseSecteur WHERE entrepriseSecteur_secteur_ID = (";
			sql += "SELECT secteur_ID FROM Secteur WHERE secteur_nom = :secteur_nom";
			sql += "))))";
		}

		if (parametresPresents[2] && !parametresPresents[3]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "anneePromotion_ID IN (";
			sql += "SELECT personne_anneePromotion_ID FROM Personne WHERE personne_ID IN (";
			sql += "SELECT entreprisePersonne_ID FROM EntreprisePersonne, Ville WHERE entreprisePersonne_ville_ID = (";
			sql += "SELECT pays_ID FROM Pays WHERE pays_nom = :pays_nom";
			sql += ")";
			sql += " AND ";
			sql += "ville_ID = entreprisePersonne_ville_ID";
			sql += "))";
		}

		if (parametresPresents[3]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "anneePromotion_ID IN (";
			sql += "SELECT personne_anneePromotion_ID FROM Personne WHERE personne_ID IN (";
			sql += "SELECT entreprisePersonne_ID FROM EntreprisePersonne WHERE entreprisePersonne_ville_ID IN (";
			sql += "SELECT ville_ID FROM Ville WHERE ville_nom = :ville_nom";
			sql += ")))";
		}

		sql += " ORDER BY anneePromotion_libelle ASC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		if (parametresPresents[0]) {
			sqlQuery.setParameter("entreprise_nom", entreprise_nom);
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
		List<String> listeDesAnneesPromotionParCriteres = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow) {
			listeDesAnneesPromotionParCriteres.add(sqlRow.get(
			        "anneePromotion_libelle").toString());
		}

		return ok(Json.toJson(listeDesAnneesPromotionParCriteres));
	}

}
