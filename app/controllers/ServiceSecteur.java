package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Secteur;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import dao.SecteurDao;

public class ServiceSecteur extends Controller {

	public static List<Secteur> listeDesSecteurs() {
		return SecteurDao.find.orderBy("nom asc").findList();
	}

	public static Result AJAX_listeDesSecteurs() {
		String sql = "SELECT secteur_nom FROM Secteur ORDER BY secteur_nom ASC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesSecteurs = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow) {
			listeDesSecteurs.add(sqlRow.get("secteur_nom").toString());
		}

		return ok(Json.toJson(listeDesSecteurs));
	}

	public static Result AJAX_listeDesSecteursSelonCriteres(
	        String anneePromotion_libelle, String entreprise_nom,
	        String pays_nom, String ville_nom) {
		Boolean[] parametresPresents = new Boolean[] {
		        anneePromotion_libelle != null
		                && !anneePromotion_libelle.isEmpty(),
		        entreprise_nom != null && !entreprise_nom.isEmpty(),
		        pays_nom != null && !pays_nom.isEmpty(),
		        ville_nom != null && !ville_nom.isEmpty() };

		Boolean wherePlace = false;

		String sql = "SELECT secteur_nom FROM Secteur";

		if (parametresPresents[0]) {
			wherePlace = true;
			sql += " WHERE ";
			sql += "secteur_ID IN (";
			sql += "SELECT entrepriseVilleSecteur_secteur_ID FROM EntrepriseVilleSecteur, EntrepriseVilleSecteurPersonne, Personne WHERE personne_anneePromotion_ID IN (";
			sql += "SELECT anneePromotion_ID FROM anneePromotion WHERE anneePromotion_libelle = :anneePromotion_libelle";
			sql += ")";
			sql += " AND "; 
			sql += "personne_ID = entrepriseVilleSecteurPersonne_personne_ID";
			sql += " AND "; 
			sql += "entrepriseVilleSecteurPersonne_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID";
			sql += ")";
		}

		if (parametresPresents[1]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "secteur_ID IN (";
			sql += "SELECT entrepriseVilleSecteur_secteur_ID FROM EntrepriseVilleSecteur, Entreprise WHERE entrepriseVilleSecteur_entreprise_ID = entreprise_ID";
			sql += " AND ";
			sql += "entreprise_nom = :entreprise_nom";
			sql += ")";
		}

		if (parametresPresents[2] && !parametresPresents[3]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "secteur_ID IN (";
			sql += "SELECT entrepriseVilleSecteur_secteur_ID FROM EntrepriseVilleSecteur, Ville WHERE ville_pays_ID = (";
			sql += "SELECT pays_ID FROM Pays WHERE pays_nom = :pays_nom";
			sql += ")";
			sql += " AND ";
			sql += "ville_ID = entrepriseVilleSecteur_ville_ID";
			sql += ")";
		}

		if (parametresPresents[3]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "secteur_ID IN (";
			sql += "SELECT entrepriseVilleSecteur_secteur FROM EntrepriseVilleSecteur, Ville WHERE ville_nom = :ville_nom";
			sql += " AND ";
			sql += "ville_ID = entrepriseVilleSecteur_ville_ID";
			sql += ")";
		}

		sql += " ORDER BY secteur_nom ASC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		if (parametresPresents[0]) {
			sqlQuery.setParameter("anneePromotion_libelle",
			        Integer.parseInt(anneePromotion_libelle));
		}
		if (parametresPresents[1]) {
			sqlQuery.setParameter("entreprise_nom", entreprise_nom);
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
			listeDesEntreprisesParCriteres.add(sqlRow.get("secteur_nom")
			        .toString());
		}

		return ok(Json.toJson(listeDesEntreprisesParCriteres));
	}

}