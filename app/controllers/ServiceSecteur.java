package controllers;

import java.util.ArrayList;
import java.util.List;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import constantes.IConstantes;

public class ServiceSecteur extends Controller {

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
	        String centralien_nom, String anneePromotion_libelle,
	        String ecole_nom, String entreprise_nom, String pays_nom,
	        String ville_nom) {
		Boolean[] parametresPresents = new Boolean[] {
		        centralien_nom != null && !centralien_nom.isEmpty(),
		        anneePromotion_libelle != null
		                && !anneePromotion_libelle.isEmpty(),
		        ecole_nom != null
		                && !ecole_nom.isEmpty()
		                && !ecole_nom
		                        .equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF),
		        entreprise_nom != null
		                && !entreprise_nom.isEmpty()
		                && !entreprise_nom
		                        .equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF),
		        pays_nom != null && !pays_nom.isEmpty(),
		        ville_nom != null && !ville_nom.isEmpty() };

		Boolean wherePlace = false;

		String sql = "SELECT secteur_nom FROM Secteur";

		// TODO : Ou Ecole !!!
		if (parametresPresents[0]) {
			wherePlace = true;
			sql += " WHERE ";
			sql += "secteur_ID = (";
			sql += "SELECT entrepriseVilleSecteur_secteur_ID FROM EntrepriseVilleSecteur, EntrepriseVilleSecteurCentralien, Centralien WHERE centralien_nom = :centralien_nom";
			sql += " AND ";
			sql += "centralien_ID = entrepriseVilleSecteurCentralien_centralien_ID";
			sql += " AND ";
			sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID";
			sql += ")";
		}

		// TODO : Ou Ecole !!!
		if (parametresPresents[1]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "secteur_ID IN (";
			sql += "SELECT entrepriseVilleSecteur_secteur_ID FROM EntrepriseVilleSecteur, EntrepriseVilleSecteurCentralien, Centralien WHERE centralien_anneePromotion_ID IN (";
			sql += "SELECT anneePromotion_ID FROM anneePromotion WHERE anneePromotion_libelle = :anneePromotion_libelle";
			sql += ")";
			sql += " AND ";
			sql += "centralien_ID = entrepriseVilleSecteurCentralien_centralien_ID";
			sql += " AND ";
			sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID";
			sql += ")";
		}

		if (parametresPresents[2]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "secteur_ID IN (";
			sql += "SELECT ecoleSecteur_secteur_ID FROM EcoleSecteur, Ecole WHERE ecoleSecteur_ecole_ID = ecole_ID";
			sql += " AND ";
			sql += "ecole_nom = :ecole_nom";
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
			sql += "SELECT entrepriseVilleSecteur_secteur_ID FROM EntrepriseVilleSecteur, Entreprise WHERE entrepriseVilleSecteur_entreprise_ID = entreprise_ID";
			sql += " AND ";
			sql += "entreprise_nom = :entreprise_nom";
			sql += ")";
		}

		// TODO : Ou Ecole !!!
		if (parametresPresents[4] && !parametresPresents[5]) {
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

		// TODO : Ou Ecole !!!
		if (parametresPresents[5]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "secteur_ID IN (";
			sql += "SELECT entrepriseVilleSecteur_secteur_ID FROM EntrepriseVilleSecteur, Ville WHERE ville_nom = :ville_nom";
			sql += " AND ";
			sql += "ville_ID = entrepriseVilleSecteur_ville_ID";
			sql += ")";
		}

		sql += " ORDER BY secteur_nom ASC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		if (parametresPresents[0]) {
			sqlQuery.setParameter("centralien_nom", centralien_nom);
		}
		if (parametresPresents[1]) {
			sqlQuery.setParameter("anneePromotion_libelle",
			        Integer.parseInt(anneePromotion_libelle));
		}
		if (parametresPresents[2]) {
			sqlQuery.setParameter("ecole_nom", ecole_nom);
		}
		if (parametresPresents[3]) {
			sqlQuery.setParameter("entreprise_nom", entreprise_nom);
		}
		if (parametresPresents[4] && !parametresPresents[5]) {
			sqlQuery.setParameter("pays_nom", pays_nom);
		}
		if (parametresPresents[5]) {
			sqlQuery.setParameter("ville_nom", ville_nom);
		}

		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesSecteursParCriteres = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow) {
			listeDesSecteursParCriteres.add(sqlRow.get("secteur_nom")
			        .toString());
		}

		return ok(Json.toJson(listeDesSecteursParCriteres));
	}

}
