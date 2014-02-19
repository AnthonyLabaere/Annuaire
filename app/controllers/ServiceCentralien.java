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

public class ServiceCentralien extends Controller {

	public static Result AJAX_listeDesCentraliens() {
		String sql = "SELECT centralien_nom FROM Centralien ORDER BY centralien_nom DESC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesCentraliens = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow) {
			listeDesCentraliens.add(sqlRow.get("centralien_nom").toString());
		}

		return ok(Json.toJson(listeDesCentraliens));
	}

	public static Result AJAX_listeDesCentraliensSelonCriteres(
	        String anneePromotion_libelle, String ecole_nom,
	        String entreprise_nom, String secteur_nom, String pays_nom,
	        String ville_nom) {
		Boolean[] parametresPresents = new Boolean[] {
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
		        secteur_nom != null && !secteur_nom.isEmpty(),
		        pays_nom != null && !pays_nom.isEmpty(),
		        ville_nom != null && !ville_nom.isEmpty() };

		Boolean wherePlace = false;

		String sql = "SELECT centralien_nom FROM Centralien";

		if (parametresPresents[0]) {
			wherePlace = true;
			sql += " WHERE ";
			sql += "centralien_anneePromotion_ID = (";
			sql += "SELECT anneePromotion_ID FROM anneePromotion WHERE anneePromotion_libelle = :anneePromotion_libelle";
			sql += ")";
		}

		if (parametresPresents[1]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "centralien_ID IN (";
			sql += "SELECT ecoleSecteurCentralien_Centralien_ID FROM EcoleSecteurCentralien, EcoleSecteur WHERE ecoleSecteur_ecole_ID = (";
			sql += "SELECT ecole_ID FROM Ecole WHERE ecole_nom = :ecole_nom";
			sql += ")";
			sql += " AND ";
			sql += "ecoleSecteurCentralien_ecoleSecteur_ID = ecoleSecteur_ID ";
			sql += ")";
		}

		if (parametresPresents[2]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "centralien_ID IN (";
			sql += "SELECT entrepriseVilleSecteurCentralien_Centralien_ID FROM EntrepriseVilleSecteurCentralien, EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_entreprise_ID = (";
			sql += "SELECT entreprise_ID FROM Entreprise WHERE entreprise_nom = :entreprise_nom";
			sql += ")";
			sql += " AND ";
			sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID ";
			sql += ")";
		}

		if (parametresPresents[3]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			if (ecole_nom.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)){
				sql += "centralien_ID IN (";
				sql += "SELECT entrepriseVilleSecteurCentralien_Centralien_ID FROM EntrepriseVilleSecteurCentralien, EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_secteur_ID = (";
				sql += "SELECT secteur_ID FROM Secteur WHERE secteur_nom = :secteur_nom";
				sql += ")";
				sql += " AND ";
				sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID ";
				sql += ")";				
			} else {
				sql += "centralien_ID IN (";
				sql += "SELECT ecoleSecteurCentralien_Centralien_ID FROM EcoleSecteurCentralien, EcoleSecteur WHERE ecoleSecteur_secteur_ID = (";
				sql += "SELECT secteur_ID FROM Secteur WHERE secteur_nom = :secteur_nom";
				sql += ")";
				sql += " AND ";
				sql += "ecoleSecteurCentralien_ecoleSecteur_ID = ecoleSecteur_ID ";
				sql += ")";			
			}
		}

		if (parametresPresents[4] && !parametresPresents[5]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			if (ecole_nom.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)){
				sql += "centralien_ID IN (";
				sql += "SELECT entrepriseVilleSecteurCentralien_Centralien_ID FROM EntrepriseVilleSecteurCentralien, EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_ville_ID IN (";
				sql += "SELECT ville_ID FROM Ville, Pays WHERE ville_pays_ID = pays_ID AND pays_nom = :pays_nom";
				sql += ")";
				sql += " AND ";
				sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID ";
				sql += ")";		
			} else {
				sql += "centralien_ID IN (";
				sql += "SELECT ecoleSecteurCentralien_Centralien_ID FROM EcoleSecteur, EcoleSecteurCentralien, Ecole WHERE ecole_ville_ID IN (";
				sql += "SELECT ville_ID FROM Ville, Pays WHERE ville_pays_ID = pays_ID AND pays_nom = :pays_nom";
				sql += ")";
				sql += " AND ";
				sql += "ecoleSecteurCentralien_ecoleSecteur_ID = ecoleSecteur_ID ";
				sql += " AND ";
				sql += "ecoleSecteur_ecole_ID = ecole_ID ";				
				sql += ")";		
			}
		}

		if (parametresPresents[5]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			if (ecole_nom.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)){
				sql += "centralien_ID IN (";
				sql += "SELECT entrepriseVilleSecteurCentralien_Centralien_ID FROM EntrepriseVilleSecteurCentralien, EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_ville_ID = (";
				sql += "SELECT ville_ID FROM Ville WHERE ville_nom = :ville_nom";
				sql += ")";
				sql += " AND ";
				sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID ";
				sql += ")";				
			} else {
				sql += "centralien_ID IN (";
				sql += "SELECT ecoleSecteurCentralien_Centralien_ID FROM EcoleSecteurCentralien, Ecole, EcoleSecteur WHERE ecole_ville_ID = (";
				sql += "SELECT ville_ID FROM Ville WHERE ville_nom = :ville_nom";
				sql += ")";
				sql += " AND ";
				sql += "ecoleSecteurCentralien_ecoleSecteur_ID = ecoleSecteur_ID ";
				sql += " AND ";
				sql += "ecoleSecteur_ecole_ID = ecole_ID ";		
				sql += ")";						
			}
		}

		sql += " ORDER BY centralien_nom ASC";
		
		System.out.println(sql);

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		if (parametresPresents[0]) {
			sqlQuery.setParameter("anneePromotion_libelle",
			        Integer.parseInt(anneePromotion_libelle));
		}
		if (parametresPresents[1]) {
			sqlQuery.setParameter("ecole_nom", ecole_nom);
		}
		if (parametresPresents[2]) {
			sqlQuery.setParameter("entreprise_nom", entreprise_nom);
		}
		if (parametresPresents[3]) {
			sqlQuery.setParameter("secteur_nom", secteur_nom);
		}
		if (parametresPresents[4] && !parametresPresents[5]) {
			sqlQuery.setParameter("pays_nom", pays_nom);
		}
		if (parametresPresents[5]) {
			sqlQuery.setParameter("ville_nom", ville_nom);
		}

		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesCentraliensParCriteres = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow) {
			listeDesCentraliensParCriteres.add(sqlRow.get("centralien_nom")
			        .toString());
		}

		return ok(Json.toJson(listeDesCentraliensParCriteres));
	}

}
