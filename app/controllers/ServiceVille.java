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

public class ServiceVille extends Controller {

	public static Result AJAX_listeDesVillesDuPays(String pays_ID) {

		String sql = "SELECT ville_nom FROM Ville WHERE ville_pays_ID = :pays_ID";
		sql += " ORDER BY ville_nom ASC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		sqlQuery.setParameter("pays_ID", pays_ID);

		List<SqlRow> listSqlRowVille = sqlQuery.findList();
		List<String> listeDesVilles = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRowVille) {
			listeDesVilles.add(sqlRow.get("ville_nom").toString());
		}

		return ok(Json.toJson(listeDesVilles));
	}

	public static Result AJAX_listeDesVillesSelonCriteres(
	        String centralien_ID, String anneePromotion_ID,
	        String ecole_ID, String entreprise_ID, String secteur_ID,
	        String pays_ID) {

		Boolean[] parametresPresents = new Boolean[] {
				centralien_ID != null && !centralien_ID.isEmpty(),
						anneePromotion_ID != null
		                && !anneePromotion_ID.isEmpty(),
		        ecole_ID != null
		                && !ecole_ID.isEmpty()
		                && !ecole_ID
		                        .equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF),
		        entreprise_ID != null
		                && !entreprise_ID.isEmpty()
		                && !entreprise_ID
		                        .equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF),
		        secteur_ID != null && !secteur_ID.isEmpty() };

		Boolean wherePlace = false;

		String sql = "SELECT ville_nom FROM Ville";

		if (parametresPresents[0]) {
			wherePlace = true;
			sql += " WHERE ";
			if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
				sql += "ville_ID IN (";
				sql += "SELECT entrepriseVilleSecteur_ville_ID FROM EntrepriseVilleSecteur, EntrepriseVilleSecteurCentralien, Centralien WHERE centralien_ID = :centralien_ID";
				sql += " AND ";
				sql += "centralien_ID = entrepriseVilleSecteurCentralien_centralien_ID";
				sql += " AND ";
				sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID";
				sql += ")";
			} else {
				sql += "ville_ID IN (";
				sql += "SELECT ecole_ville_ID FROM Centralien, Ecole, EcoleSecteur, EcoleSecteurCentralien WHERE centralien_ID = :centralien_ID";
				sql += " AND ";
				sql += "centralien_ID = ecoleSecteurCentralien_centralien_ID";
				sql += " AND ";
				sql += "ecoleSecteurCentralien_ecoleSecteur_ID = ecoleSecteur_ID";
				sql += " AND ";
				sql += "ecoleSecteur_ecole_ID = ecole_ID";
				sql += ")";
			}
		}

		if (parametresPresents[1]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
				sql += "ville_ID IN (";
				sql += "SELECT entrepriseVilleSecteur_ville_ID FROM EntrepriseVilleSecteur, EntrepriseVilleSecteurCentralien WHERE entrepriseVilleSecteurCentralien_centralien_ID IN (";
				sql += "SELECT centralien_ID FROM Centralien WHERE centralien_anneePromotion_ID = :anneePromotion_ID";
				sql += " AND ";
				sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID";
				sql += "))";
			} else {
				sql += "ville_ID IN (";
				sql += "SELECT ecole_ville_ID FROM Centralien, Ecole, EcoleSecteur, EcoleSecteurCentralien WHERE centralien_anneePromotion_ID = :anneePromotion_ID";
				sql += " AND ";
				sql += "centralien_ID = ecoleSecteurCentralien_centralien_ID";
				sql += " AND ";
				sql += "ecoleSecteurCentralien_ecoleSecteur_ID = ecoleSecteur_ID";
				sql += " AND ";
				sql += "ecoleSecteur_ecole_ID = ecole_ID";
				sql += ")";
			}
		}

		if (parametresPresents[2]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "ville_ID IN (";
			sql += "SELECT ecole_ville_ID FROM Ecole WHERE ecole_ID = :ecole_ID";
			sql += ")";

		}

		if (parametresPresents[3]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "ville_ID IN (";
			sql += "SELECT entrepriseVilleSecteur_ville_ID FROM EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_entreprise_ID = :entreprise_ID";
			sql += ")";
		}

		if (parametresPresents[4]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
				sql += "ville_ID IN (";
				sql += "SELECT entrepriseVilleSecteur_ville_ID FROM EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_secteur_ID = :secteur_ID";
				sql += ")";
			} else {
				sql += "ville_ID IN (";
				sql += "SELECT ecole_ville_ID FROM Ecole, EcoleSecteur WHERE ecoleSecteur_secteur_ID = :secteur_ID";
				sql += " AND ";
				sql += "ecoleSecteur_ecole_ID = ecole_ID";
				sql += ")";
			}
		}
		
		// pays_ID est forcement present
		if (wherePlace) {
			sql += " AND ";
		} else {
			sql += " WHERE ";
			wherePlace = true;
		}
		sql += "ville_pays_ID = :pays_ID";

		sql += " ORDER BY ville_nom ASC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		if (parametresPresents[0]) {
			sqlQuery.setParameter("centralien_ID", Integer.parseInt(centralien_ID));
		}
		if (parametresPresents[1]) {
			sqlQuery.setParameter("anneePromotion_ID",
			        Integer.parseInt(anneePromotion_ID));
		}
		if (parametresPresents[2]) {
			sqlQuery.setParameter("ecole_ID", Integer.parseInt(ecole_ID));
		}
		if (parametresPresents[3]) {
			sqlQuery.setParameter("entreprise_ID", Integer.parseInt(entreprise_ID));
		}
		if (parametresPresents[4]) {
			sqlQuery.setParameter("secteur_ID", Integer.parseInt(secteur_ID));
		}
		sqlQuery.setParameter("pays_ID", Integer.parseInt(pays_ID));

		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesVillesParCriteres = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow) {
			listeDesVillesParCriteres.add(sqlRow.get("ville_nom").toString());
		}

		return ok(Json.toJson(listeDesVillesParCriteres));
	}
}
