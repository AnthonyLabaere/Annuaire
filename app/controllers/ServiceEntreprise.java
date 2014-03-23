/****************************************************************************

Copyright 2014 Anthony Labaere

Contributeurs : 
François Neber francois.neber@centraliens-nantes.net
Malik Olivier Boussejra malik.boussejra@centraliens-nantes.net
Anthony Labaere anthony.labaere@centraliens-nantes.net

Ce logiciel est un programme informatique ayant pour but de faciliter 
les contacts entre étudiants et diplômés de l'École Centrale Nantes 
à l'étranger comme en France.

Ce logiciel est régi par la licence CeCILL soumise au droit français et
respectant les principes de diffusion des logiciels libres. Vous pouvez
utiliser, modifier et/ou redistribuer ce programme sous les conditions
de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA 
sur le site "http://www.cecill.info".

En contrepartie de l'accessibilité au code source et des droits de copie,
de modification et de redistribution accordés par cette licence, il n'est
offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
seule une responsabilité restreinte pèse sur l'auteur du programme,  le
titulaire des droits patrimoniaux et les concédants successifs.

A cet égard  l'attention de l'utilisateur est attirée sur les risques
associés au chargement,  à l'utilisation,  à la modification et/ou au
développement et à la reproduction du logiciel par l'utilisateur étant 
donné sa spécificité de logiciel libre, qui peut le rendre complexe à 
manipuler et qui le réserve donc à des développeurs et des professionnels
avertis possédant  des  connaissances  informatiques approfondies.  Les
utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
logiciel à leurs besoins dans des conditions permettant d'assurer la
sécurité de leurs systèmes et ou de leurs données et, plus généralement, 
à l'utiliser et l'exploiter dans les mêmes conditions de sécurité. 

Le fait que vous puissiez accéder à cet en-tête signifie que vous avez 
pris connaissance de la licence CeCILL et que vous en avez accepté les
termes.

******************************************************************************/

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
