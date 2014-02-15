package controllers;

import java.util.ArrayList;
import java.util.List;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

public class ServiceAnneePromotion extends Controller {

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
			String ecole_nom, String entreprise_nom, String secteur_nom, String pays_nom,
	        String ville_nom) {
		Boolean[] parametresPresents = new Boolean[] {
		        ecole_nom != null && !ecole_nom.isEmpty(),
		        entreprise_nom != null && !entreprise_nom.isEmpty(),
		        secteur_nom != null && !secteur_nom.isEmpty(),
		        pays_nom != null && !pays_nom.isEmpty(),
		        ville_nom != null && !ville_nom.isEmpty() };

		Boolean wherePlace = false;

		String sql = "SELECT anneePromotion_libelle FROM AnneePromotion";

		if (parametresPresents[0]) {
			wherePlace = true;
			sql += " WHERE ";
			sql += "anneePromotion_ID IN (";
			sql += "SELECT personne_anneePromotion_ID FROM Personne WHERE personne_ID IN (";
			sql += "SELECT ecoleSecteurPersonne_ID FROM EcoleSecteurPersonne, EcoleSecteur WHERE ecoleSecteur_ecole_ID = (";
			sql += "SELECT ecole_ID FROM Ecole WHERE ecole_nom = :ecole_nom";
			sql += ")";
			sql += " AND ";
			sql += "ecoleSecteurPersonne_ecoleSecteur_ID = ecoleSecteur_ID ";	
			sql += "))";
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
			sql += "SELECT entrepriseVilleSecteurPersonne_ID FROM EntrepriseVilleSecteurPersonne, EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_entreprise_ID = (";
			sql += "SELECT entreprise_ID FROM Entreprise WHERE entreprise_nom = :entreprise_nom";
			sql += ")";
			sql += " AND ";
			sql += "entrepriseVilleSecteurPersonne_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID ";	
			sql += "))";
		}

		if (parametresPresents[2]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "anneePromotion_ID IN (";
			sql += "SELECT personne_anneePromotion_ID FROM Personne WHERE personne_ID IN (";
			sql += "SELECT entrepriseVilleSecteurPersonne_ID FROM EntrepriseVilleSecteurPersonne, EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_secteur_ID = (";
			sql += "SELECT secteur_ID FROM Secteur WHERE secteur_nom = :secteur_nom";
			sql += ")";
			sql += " AND ";
			sql += "entrepriseVilleSecteurPersonne_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID ";	
			sql += "))";
		}

		if (parametresPresents[3] && !parametresPresents[4]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "anneePromotion_ID IN (";
			sql += "SELECT personne_anneePromotion_ID FROM Personne WHERE personne_ID IN (";
			sql += "SELECT entrepriseVilleSecteurPersonne_ID FROM EntrepriseVilleSecteurPersonne, EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_ville_ID IN (";
			sql += "SELECT ville_ID FROM Ville, Pays WHERE ville_pays_ID = pays_ID AND pays_nom = :pays_nom";
			sql += ")";
			sql += " AND ";
			sql += "entrepriseVilleSecteurPersonne_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID ";	
			sql += "))";
		}

		if (parametresPresents[4]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			
			sql += "anneePromotion_ID IN (";
			sql += "SELECT personne_anneePromotion_ID FROM Personne WHERE personne_ID IN (";
			sql += "SELECT entrepriseVilleSecteurPersonne_ID FROM EntrepriseVilleSecteurPersonne, EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_ville_ID = (";
			sql += "SELECT ville_ID FROM Ville WHERE ville_nom = :ville_nom";
			sql += ")";
			sql += " AND ";
			sql += "entrepriseVilleSecteurPersonne_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID ";	
			sql += "))";
		}

		sql += " ORDER BY anneePromotion_libelle ASC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		if (parametresPresents[0]) {
			sqlQuery.setParameter("ecole_nom", ecole_nom);
		}
		if (parametresPresents[1]) {
			sqlQuery.setParameter("entreprise_nom", entreprise_nom);
		}
		if (parametresPresents[2]) {
			sqlQuery.setParameter("secteur_nom", secteur_nom);
		}
		if (parametresPresents[3] && !parametresPresents[4]) {
			sqlQuery.setParameter("pays_nom", pays_nom);
		}
		if (parametresPresents[4]) {
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
