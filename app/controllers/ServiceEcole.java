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
import constantes.IConstantesBDD;

/**
 * Service Ajax concernant la table Ecole
 * 
 * @author Anthony
 * 
 */
public class ServiceEcole extends Controller {

	public static Result AJAX_listeDesEcoles() {
		String sql = "SELECT ecole_ID, ecole_nom FROM Ecole ORDER BY ecole_nom ASC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();		
		// Liste de double String : le premier est l'ID et le deuxième est le nom
		List<String[]> listeDesEcoles = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get("ecole_ID").toString();
			String nom = sqlRow.get("ecole_nom").toString();
			listeDesEcoles.add(new String[] { identifiant, nom });
		}

		return ok(Json.toJson(listeDesEcoles));
	}

	public static Result AJAX_listeDesEcolesSelonCriteres(
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
		
		String sql = "SELECT DISTINCT ecole_ID, ecole_nom FROM Ecole";
		
		// On ajoute la contrainte d'historique
		if (!historique) {
			wherePlace = true;
			
			sql += ", EcoleSecteurCentralien, EcoleSecteur, Centralien";
			sql += " WHERE ";
			sql += "ecole_ID = ecoleSecteur_ecole_ID AND ecoleSecteur_ID = ecoleSecteurCentralien_ecolesecteur_ID AND ecoleSecteurCentralien_centralien_ID = centralien_ID";		
			sql += " AND ";			
			sql += "ecoleSecteurCentralien_ID IN (";
			sql += "SELECT posteActuel_ecoleSecteurCentralien_ID FROM PosteActuel";
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
			sql += "ecole_ID IN (";
			sql += "SELECT ecoleSecteur_ecole_ID FROM EcoleSecteur, EcoleSecteurCentralien, Centralien WHERE centralien_ID = :centralien_ID";
			sql += " AND ";
			sql += "centralien_ID = ecoleSecteurCentralien_centralien_ID";
			sql += " AND ";
			sql += "ecoleSecteurCentralien_ecoleSecteur_ID = ecoleSecteur_ID";
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
			sql += "ecole_ID IN (";
			sql += "SELECT ecoleSecteur_ecole_ID FROM EcoleSecteur, EcoleSecteurCentralien, Centralien WHERE centralien_anneePromotion_ID = :anneePromotion_ID";
			sql += " AND ";
			sql += "centralien_ID = ecoleSecteurCentralien_centralien_ID";
			sql += " AND ";
			sql += "ecoleSecteurCentralien_ecoleSecteur_ID = ecoleSecteur_ID";
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
			sql += "ecole_ID IN (";
			sql += "SELECT ecoleSecteur_ecole_ID FROM EcoleSecteur, Secteur WHERE secteur_ID = :secteur_ID";
			sql += " AND ";
			sql += "secteur_ID = ecoleSecteur_secteur_ID";
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
			sql += "ecole_ville_ID IN (";
			sql += "SELECT ville_ID FROM Ville WHERE ville_pays_ID = :pays_ID";
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
			sql += "ecole_ville_ID = :ville_ID";
		}

		sql += " ORDER BY ecole_nom ASC";

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
		List<String[]> listeDesEcolesParCriteres = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get("ecole_ID").toString();
			String nom = sqlRow.get("ecole_nom").toString();
			listeDesEcolesParCriteres.add(new String[] { identifiant, nom });
		}


		return ok(Json.toJson(listeDesEcolesParCriteres));
	}

}
