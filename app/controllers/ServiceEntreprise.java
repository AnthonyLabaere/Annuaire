package controllers;

import java.util.ArrayList;
import java.util.List;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

/**
 * Service Ajax concernant la table Entreprise
 * 
 * @author Anthony
 * 
 */
public class ServiceEntreprise extends Controller {

	public static Result AJAX_listeDesEntreprises() {
		String sql = "SELECT entreprise_ID, entreprise_nom FROM Entreprise ORDER BY entreprise_nom ASC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();
		// Liste de double String : le premier est l'ID et le deuxième est le nom
		List<String[]> listeDesEntreprises = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get("entreprise_ID").toString();
			String nom = sqlRow.get("entreprise_nom").toString();
			listeDesEntreprises.add(new String[] { identifiant, nom });
		}

		return ok(Json.toJson(listeDesEntreprises));
	}

	public static Result AJAX_listeDesEntreprisesSelonCriteres(
	        boolean historique, String centralien_ID, String anneePromotion_ID,
	        String secteur_ID, String pays_ID, String ville_ID) {
		Boolean[] parametresPresents = new Boolean[] {
				centralien_ID != null && !centralien_ID.isEmpty(),
						anneePromotion_ID != null
		                && !anneePromotion_ID.isEmpty(),
		        secteur_ID != null && !secteur_ID.isEmpty(),
		        pays_ID != null && !pays_ID.isEmpty(),
		        ville_ID != null && !ville_ID.isEmpty() };

		Boolean wherePlace = false;

		String sql = "SELECT DISTINCT entreprise_ID, entreprise_nom FROM Entreprise";
		
		// On ajoute la contrainte d'historique
		if (!historique) {
			wherePlace = true;
			
			sql += ", EntrepriseVilleSecteur, EntrepriseVilleSecteurCentralien, Centralien";
			sql += " WHERE ";
				sql += "entrepriseVilleSecteurCentralien_centralien_ID IN (";
				sql += "SELECT posteActuel_entrepriseVilleSecteurCentralien_ID FROM PosteActuel";
				sql += ")";
		}

		// Si le filtre centralien est actif
		if (parametresPresents[0]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "entreprise_ID IN (";
			sql += "SELECT entrepriseVilleSecteur_entreprise_ID FROM EntrepriseVilleSecteur, EntrepriseVilleSecteurCentralien, Centralien WHERE centralien_ID = :centralien_ID";
			sql += " AND ";
			sql += "centralien_ID = entrepriseVilleSecteurCentralien_centralien_ID";
			sql += " AND ";
			sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID";
			sql += ")";
		}

		// Si le filtre anneePromotion est actif
		if (parametresPresents[1]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "entreprise_ID IN (";
			sql += "SELECT entrepriseVilleSecteur_entreprise_ID FROM EntrepriseVilleSecteur, EntrepriseVilleSecteurCentralien, Centralien WHERE centralien_anneePromotion_ID = :anneePromotion_ID";
			sql += " AND ";
			sql += "centralien_ID = entrepriseVilleSecteurCentralien_centralien_ID";
			sql += " AND ";
			sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID";
			sql += ")";
		}
		
		// Si le filtre secteur est actif
		if (parametresPresents[2]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "entreprise_ID IN (";
			sql += "SELECT entrepriseVilleSecteur_entreprise_ID FROM EntrepriseVilleSecteur, Secteur WHERE secteur_ID = :secteur_ID";
			sql += " AND ";
			sql += "secteur_ID = entrepriseVilleSecteur_secteur_ID";
			sql += ")";
		}
		
		// Si le filtre pays est actif mais pas le filtre ville
		if (parametresPresents[3] && !parametresPresents[4]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "entreprise_ID IN (";
			sql += "SELECT entrepriseVilleSecteur_entreprise_ID FROM EntrepriseVilleSecteur, Ville WHERE ville_pays_ID = :pays_ID";
			sql += " AND ";
			sql += "ville_ID = entrepriseVilleSecteur_ville_ID";
			sql += ")";
		}
		
		// Si le filtre ville est actif
		if (parametresPresents[4]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "entreprise_ID IN (";
			sql += "SELECT entrepriseVilleSecteur_entreprise_ID FROM EntrepriseVilleSecteur, Ville WHERE ville_ID = :ville_ID";
			sql += " AND ";
			sql += "ville_ID = entrepriseVilleSecteur_ville_ID";
			sql += ")";
		}

		sql += " ORDER BY entreprise_nom ASC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		if (parametresPresents[0]) {
			sqlQuery.setParameter("centralien_ID", Integer.parseInt(centralien_ID));
		}
		if (parametresPresents[1]) {
			sqlQuery.setParameter("anneePromotion_ID",
			        Integer.parseInt(anneePromotion_ID));
		}
		if (parametresPresents[2]) {
			sqlQuery.setParameter("secteur_ID", Integer.parseInt(secteur_ID));
		}
		if (parametresPresents[3] && !parametresPresents[4]) {
			sqlQuery.setParameter("pays_ID", Integer.parseInt(pays_ID));
		}
		if (parametresPresents[4]) {
			sqlQuery.setParameter("ville_ID", Integer.parseInt(ville_ID));
		}

		List<SqlRow> listSqlRow = sqlQuery.findList();		
		// Liste de double String : le premier est l'ID et le deuxième est le nom
		List<String[]> listeDesEntreprisesParCriteres = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get("entreprise_ID").toString();
			String nom = sqlRow.get("entreprise_nom").toString();
			listeDesEntreprisesParCriteres.add(new String[] { identifiant, nom });
		}

		return ok(Json.toJson(listeDesEntreprisesParCriteres));
	}
}
