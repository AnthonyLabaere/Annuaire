package controllers;

import geography.Coordonnees;

import java.util.ArrayList;
import java.util.List;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;

import constantes.IConstantes;
import constantes.IConstantesBDD;

/**
 * Service Ajax concernant la table Ville
 * 
 * @author Anthony
 * 
 */
public class ServiceVille extends Controller {

	public static Result AJAX_listeDesVillesDuPays(String pays_ID) {
		String param_paysID = "pays_ID";
		String param_ville_ID = "ville_ID";
		String param_ville_nom = "ville_nom";
		String param_ville_latitude = "ville_latitude";
		String param_ville_longitude = "ville_longitude";
		
		String sql = IConstantesBDD.SQL_SELECT 
				+ IConstantesBDD.VILLE_ID
				+ IConstantesBDD.SQL_COMMA
				+ IConstantesBDD.VILLE_NOM
				+ IConstantesBDD.SQL_COMMA
				+ IConstantesBDD.VILLE_LATITUDE
				+ IConstantesBDD.SQL_COMMA
				+ IConstantesBDD.VILLE_LONGITUDE
				+ IConstantesBDD.SQL_FROM
				+ IConstantesBDD.VILLE
				+ IConstantesBDD.SQL_WHERE 
				+ IConstantesBDD.VILLE_PAYS_ID
				+ IConstantesBDD.SQL_EQUAL
				+ IConstantesBDD.SQL_COLON
				+ IConstantesBDD.PAYS_ID
				+ IConstantesBDD.SQL_ORDER_BY
				+ IConstantesBDD.VILLE_NOM;

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		sqlQuery.setParameter(param_paysID, pays_ID);

		List<SqlRow> listSqlRow = sqlQuery.findList();
		// Liste de 4 String : ID, nom, latitude et longitude
		List<String[]> listeDesVilles = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get(param_ville_ID).toString();
			String nom = sqlRow.get(param_ville_nom).toString();
			String latitude = sqlRow.get(param_ville_latitude).toString();
			String longitude = sqlRow.get(param_ville_longitude).toString();
			listeDesVilles.add(new String[] { identifiant, nom, latitude,
			        longitude });
		}

		return ok(Json.toJson(listeDesVilles));
	}

	public static Result AJAX_listeDesVillesSelonCriteres(String centralien_ID,
	        String anneePromotion_ID, String ecole_ID, String entreprise_ID,
	        String secteur_ID, String pays_ID) {

		Boolean[] parametresPresents = new Boolean[] {
		        centralien_ID != null && !centralien_ID.isEmpty(),
		        anneePromotion_ID != null && !anneePromotion_ID.isEmpty(),
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

		String sql = "SELECT ville_ID, ville_nom, ville_latitude, ville_longitude FROM Ville";

		// Si le filtre centralien est actif
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

		// Si le filtre anneePromotion est actif
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

		// Si le filtre ecole est actif
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

		// Si le filtre entreprise est actif
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

		// Si le filtre secteur est actif
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
			sqlQuery.setParameter("centralien_ID",
			        Integer.parseInt(centralien_ID));
		}
		if (parametresPresents[1]) {
			sqlQuery.setParameter("anneePromotion_ID",
			        Integer.parseInt(anneePromotion_ID));
		}
		if (parametresPresents[2]) {
			sqlQuery.setParameter("ecole_ID", Integer.parseInt(ecole_ID));
		}
		if (parametresPresents[3]) {
			sqlQuery.setParameter("entreprise_ID",
			        Integer.parseInt(entreprise_ID));
		}
		if (parametresPresents[4]) {
			sqlQuery.setParameter("secteur_ID", Integer.parseInt(secteur_ID));
		}
		sqlQuery.setParameter("pays_ID", Integer.parseInt(pays_ID));

		List<SqlRow> listSqlRow = sqlQuery.findList();
		// Liste de 4 String : ID, nom, latitude et longitude
		List<String[]> listeDesVillesParCriteres = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get("ville_ID").toString();
			String nom = sqlRow.get("ville_nom").toString();
			String latitude = sqlRow.get("ville_latitude").toString();
			String longitude = sqlRow.get("ville_longitude").toString();
			listeDesVillesParCriteres.add(new String[] { identifiant, nom,
			        latitude, longitude });
		}

		return ok(Json.toJson(listeDesVillesParCriteres));
	}

	/** Cette fonction retourne les villes sans coordonnees */
	public static List<String[]> villesSansCoordonnees() {
		String sql = "SELECT ville_ID, ville_nom FROM Ville WHERE ville_latitude IS NULL OR ville_longitude IS NULL";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();

		List<String[]> listeDesVilles = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get("ville_ID").toString();
			String nom = sqlRow.get("ville_nom").toString();
			listeDesVilles.add(new String[] { identifiant, nom });
		}

		return listeDesVilles;

	}

	/** Cette fonction met a jour les coordonnees d'une ville */
	public static void updateCoordonneesVille(String ville,
	        Coordonnees coordonnees) {
		String sql = "UPDATE Ville SET ville_latitude = :latitude, ville_longitude = :longitude WHERE ville_ID = :identifiant";
		
		SqlUpdate sqlUpdate = Ebean.createSqlUpdate(sql);
		sqlUpdate.setParameter("latitude", coordonnees.getLatitude());
		sqlUpdate.setParameter("longitude", coordonnees.getLongitude());
		sqlUpdate.setParameter("identifiant", Integer.parseInt(ville));
		sqlUpdate.execute();
	}
}
