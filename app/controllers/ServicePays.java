package controllers;

import java.util.ArrayList;
import java.util.List;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

public class ServicePays extends Controller {

	public static Result AJAX_listeDesPays() {
		String sql = "SELECT pays_nom FROM Pays ORDER BY pays_nom ASC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesPays = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow) {
			listeDesPays.add(sqlRow.get("pays_nom").toString());
		}

		return ok(Json.toJson(listeDesPays));
	}

	public static Result AJAX_listeDesPaysSelonCriteres(
	        String anneePromotion_libelle, String ecole_nom,
	        String entreprise_nom, String secteur_nom) {
		Boolean[] parametresPresents = new Boolean[] {
		        anneePromotion_libelle != null
		                && !anneePromotion_libelle.isEmpty(),
		        ecole_nom != null && !ecole_nom.isEmpty(),
		        entreprise_nom != null && !entreprise_nom.isEmpty(),
		        secteur_nom != null && !secteur_nom.isEmpty() };

		Boolean wherePlace = false;

		String sql = "SELECT pays_nom FROM Pays";

		if (parametresPresents[0]) {
			wherePlace = true;
			sql += " WHERE ";
			sql += "pays_ID IN (";
			sql += "SELECT ville_pays_ID FROM Ville, EntrepriseVilleSecteur, EntrepriseVilleSecteurCentralien, Centralien WHERE centralien_anneePromotion_ID IN (";
			sql += "SELECT anneePromotion_ID FROM AnneePromotion WHERE anneePromotion_libelle = :anneePromotion_libelle";
			sql += ")";
			sql += " AND ";
			sql += "centralien_ID = entrepriseVilleSecteurCentralien_centralien_ID";
			sql += " AND ";
			sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID";
			sql += ")";
		}

		if (parametresPresents[1]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "pays_ID IN (";
			sql += "SELECT ville_pays_ID FROM Ville, Ecole WHERE ecole_nom = :ecole_nom";
			sql += " AND ";
			sql += "ville_ID = ecole_ville_ID";
			sql += ")";
		}
		
		if (parametresPresents[2]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "pays_ID IN (";
			sql += "SELECT ville_pays_ID FROM Ville, EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_entreprise_ID = (";
			sql += "SELECT entreprise_ID FROM Entreprise WHERE entreprise_nom = :entreprise_nom";
			sql += ")";
			sql += " AND ";
			sql += "ville_ID = entrepriseVilleSecteur_ville_ID";
			sql += ")";
		}

		// TODO : Ou Ecole !!!
		if (parametresPresents[3]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "pays_ID IN (";
			sql += "SELECT ville_pays_ID FROM Ville, EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_secteur_ID = (";
			sql += "SELECT secteur_ID FROM Secteur WHERE secteur_nom = :secteur_nom";
			sql += ")";
			sql += " AND ";
			sql += "ville_ID = entrepriseVilleSecteur_ville_ID";
			sql += ")";
		}

		sql += " ORDER BY pays_nom ASC";

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

		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesPaysParCriteres = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow) {
			listeDesPaysParCriteres.add(sqlRow.get("pays_nom").toString());
		}

		return ok(Json.toJson(listeDesPaysParCriteres));
	}

}
