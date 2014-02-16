package controllers;

import java.util.ArrayList;
import java.util.List;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

public class ServiceEcole extends Controller {

	public static Result AJAX_listeDesEcoles() {
		String sql = "SELECT ecole_nom FROM Ecole ORDER BY ecole_nom ASC";
		 
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);		 
		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesEcoles = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow){
			listeDesEcoles.add(sqlRow.get("ecole_nom").toString());
		}
		
		return ok(Json.toJson(listeDesEcoles));
	}
	
	public static Result AJAX_listeDesEcolesSelonCriteres(
	        String anneePromotion_libelle, String secteur_nom, String pays_nom,
	        String ville_nom) {
		Boolean[] parametresPresents = new Boolean[] {
		        anneePromotion_libelle != null
		                && !anneePromotion_libelle.isEmpty(),
		        secteur_nom != null && !secteur_nom.isEmpty(),
		        pays_nom != null && !pays_nom.isEmpty(),
		        ville_nom != null && !ville_nom.isEmpty() };

		Boolean wherePlace = false;

		String sql = "SELECT ecole_nom FROM Ecole";

		if (parametresPresents[0]) {
			wherePlace = true;
			sql += " WHERE ";
			sql += "ecole_ID IN (";
			sql += "SELECT ecoleSecteur_ecole_ID FROM EcoleSecteur, EcoleSecteurCentralien, Centralien WHERE centralien_anneePromotion_ID IN (";
			sql += "SELECT anneePromotion_ID FROM anneePromotion WHERE anneePromotion_libelle = :anneePromotion_libelle";
			sql += ")";
			sql += " AND "; 
			sql += "centralien_ID = ecoleSecteurCentralien_centralien_ID";
			sql += " AND "; 
			sql += "ecoleSecteurCentralien_ecoleSecteur_ID = ecoleSecteur_ID";
			sql += ")";
		}

		if (parametresPresents[1]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "ecole_ID IN (";
			sql += "SELECT ecoleSecteur_ecole_ID FROM EcoleSecteur, Secteur WHERE secteur_nom = :secteur_nom";
			sql += " AND ";
			sql += "secteur_ID = ecoleSecteur_secteur_ID";
			sql += ")";
		}

		if (parametresPresents[2] && !parametresPresents[3]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "ecole_ville_ID IN (";
			sql += "SELECT ville_ID FROM Ville WHERE ville_pays_ID = (";
			sql += "SELECT pays_ID FROM Pays WHERE pays_nom = :pays_nom";
			sql += "))";
		}

		if (parametresPresents[3]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "ecole_ville_ID = (";
			sql += "SELECT ville_ID FROM Ville WHERE ville_nom = :ville_nom";
			sql += ")";
		}

		sql += " ORDER BY ecole_nom ASC";

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
		List<String> listeDesEcolesParCriteres = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow) {
			listeDesEcolesParCriteres.add(sqlRow.get("ecole_nom")
			        .toString());
		}

		return ok(Json.toJson(listeDesEcolesParCriteres));
	}

}
