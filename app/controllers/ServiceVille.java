package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Pays;
import models.Ville;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import dao.VilleDao;

public class ServiceVille extends Controller {

	public static List<Ville> listeDesVilles() {
		return VilleDao.find.orderBy("nom desc").findList();
	}

	public static List<Ville> listeDesVillesDuPays(Pays pays) {
		return VilleDao.find.where(Expr.eq("ville", pays)).findList();
	}

	public static Result AJAX_listeDesVillesDuPays(String pays_nom) {

		String sql = "SELECT ville_nom FROM Ville WHERE ville_pays_ID = (";
		sql += "SELECT pays_ID FROM Pays WHERE pays_nom = :pays_nom";
		sql += ") ";
		sql += "ORDER BY ville_nom ASC";
		
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		sqlQuery.setParameter("pays_nom", pays_nom);

		List<SqlRow> listSqlRowVille = sqlQuery.findList();
		List<String> listeDesVilles = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRowVille) {
			listeDesVilles.add(sqlRow.get("ville_nom").toString());
		}

		return ok(Json.toJson(listeDesVilles));
	}

	// pays_nom est forcement present
	public static Result AJAX_listeDesVillesSelonCriteres(
	        String anneePromotion_libelle, String entreprise_nom,
	        String secteur_nom, String pays_nom) {
		
		Boolean[] parametresPresents = new Boolean[] {
		        anneePromotion_libelle != null
		                && !anneePromotion_libelle.isEmpty(),
		        entreprise_nom != null && !entreprise_nom.isEmpty(),
		        secteur_nom != null && !secteur_nom.isEmpty() };

		Boolean wherePlace = false;

		String sql = "SELECT ville_nom FROM Ville";

		if (parametresPresents[0]) {
			wherePlace = true;
			sql += " WHERE ";
			// TODO : ajouter l'ecole !
			sql += "ville_ID IN (";
			sql += "SELECT entrepriseVilleSecteur_ville_ID FROM EntrepriseVilleSecteur, EntrepriseVilleSecteurPersonne WHERE entrepriseVilleSecteurPersonne_personne_ID IN (";
			sql += "SELECT personne_ID FROM Personne WHERE personne_anneePromotion_ID IN (";
			sql += "SELECT anneePromotion_ID FROM AnneePromotion WHERE anneePromotion_libelle = :anneePromotion_libelle";
			sql += ")";
			sql += " AND "; 
			sql += "entrepriseVilleSecteurPersonne_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID";
			sql += "))";
		}

		if (parametresPresents[1]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "ville_ID IN (";
			sql += "SELECT entrepriseVilleSecteur_ville_ID FROM EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_entreprise_ID = (";
			sql += "SELECT entreprise_ID FROM Entreprise WHERE entreprise_nom = :entreprise_nom";
			sql += "))";
		}

		if (parametresPresents[2]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += "ville_ID IN (";
			sql += "SELECT entrepriseVilleSecteur_ville_ID FROM EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_secteur_ID = (";
			sql += "SELECT secteur_ID FROM Secteur WHERE secteur_nom = :secteur_nom";
			sql += "))";
		}

		if (wherePlace) {
			sql += " AND ";
		} else {
			sql += " WHERE ";
			wherePlace = true;
		}
		sql += "ville_pays_ID = (";
		sql += "SELECT pays_ID FROM Pays WHERE pays_nom = :pays_nom";
		sql += ") ";

		sql += "ORDER BY ville_nom ASC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		if (parametresPresents[0]) {
			sqlQuery.setParameter("anneePromotion_libelle",
			        Integer.parseInt(anneePromotion_libelle));
		}
		if (parametresPresents[1]) {
			sqlQuery.setParameter("entreprise_nom", secteur_nom);
		}
		if (parametresPresents[2]) {
			sqlQuery.setParameter("secteur_nom", secteur_nom);
		}
		sqlQuery.setParameter("pays_nom", pays_nom);
		
		List<SqlRow> listSqlRow = sqlQuery.findList();
		List<String> listeDesEntreprisesParCriteres = new ArrayList<String>();
		for (SqlRow sqlRow : listSqlRow) {
			listeDesEntreprisesParCriteres.add(sqlRow.get("ville_nom")
			        .toString());
		}

		return ok(Json.toJson(listeDesEntreprisesParCriteres));
	}
}
