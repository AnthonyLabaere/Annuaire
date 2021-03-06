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


/**
 * Service Ajax concernant la table Pays
 * 
 * @author Anthony
 * 
 */
public class ServicePays extends Controller {

	public static Result AJAX_listeDesPays() {
		String sql = "SELECT pays_ID, pays_nom, pays_latitude, pays_longitude, pays_zoom FROM Pays ORDER BY pays_nom ASC";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();
		// Liste de 4 String : ID, nom, latitude et longitude
		List<String[]> listeDesPays = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get("pays_ID").toString();
			String nom = sqlRow.get("pays_nom").toString();
			String latitude = sqlRow.get("pays_latitude").toString();
			String longitude = sqlRow.get("pays_longitude").toString();
			String zoom = sqlRow.get("pays_zoom").toString();
			listeDesPays.add(new String[] { identifiant, nom, latitude,
			        longitude, zoom });
		}

		return ok(Json.toJson(listeDesPays));
	}

	public static Result AJAX_listeDesPaysSelonCriteres(String centralien_ID,
	        String anneePromotion_ID, String ecole_ID, String entreprise_ID,
	        String secteur_ID) {
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

		String sql = "SELECT DISTINCT pays_ID, pays_nom, pays_latitude, pays_longitude, pays_zoom FROM Pays";

		// Si le filtre centralien est actif
		if (parametresPresents[0]) {
			if (wherePlace) {
				sql += " AND ";
			} else {
				sql += " WHERE ";
				wherePlace = true;
			}
			sql += " WHERE ";
			if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
				sql += "pays_ID IN (";
				sql += "SELECT ville_pays_ID FROM Ville WHERE ville_ID = (";
				sql += "SELECT entrepriseVilleSecteur_ville_ID FROM EntrepriseVilleSecteur, EntrepriseVilleSecteurCentralien, Centralien WHERE centralien_ID = :centralien_ID";
				sql += " AND ";
				sql += "centralien_ID = entrepriseVilleSecteurCentralien_centralien_ID";
				sql += " AND ";
				sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID";
				sql += "))";
			} else {
				sql += "pays_ID IN (";
				sql += "SELECT ville_pays_ID FROM Ville WHERE ville_ID IN (";
				sql += "SELECT ecole_ville_ID FROM Centralien, Ecole, EcoleSecteur, EcoleSecteurCentralien WHERE centralien_ID = :centralien_ID";
				sql += " AND ";
				sql += "centralien_ID = ecoleSecteurCentralien_centralien_ID";
				sql += " AND ";
				sql += "ecoleSecteurCentralien_ecoleSecteur_ID = ecoleSecteur_ID";
				sql += " AND ";
				sql += "ecoleSecteur_ecole_ID = ecole_ID";
				sql += "))";
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
				sql += "pays_ID IN (";
				sql += "SELECT ville_pays_ID FROM Ville, EntrepriseVilleSecteur, EntrepriseVilleSecteurCentralien, Centralien WHERE centralien_anneePromotion_ID = :anneePromotion_ID";
				sql += " AND ";
				sql += "centralien_ID = entrepriseVilleSecteurCentralien_centralien_ID";
				sql += " AND ";
				sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID";
				sql += " AND ";
				sql += "entrepriseVilleSecteur_ville_ID = ville_ID";
				sql += ")";
			} else {
				sql += "pays_ID IN (";
				sql += "SELECT ville_pays_ID FROM Ville WHERE ville_ID IN (";
				sql += "SELECT ecole_ville_ID FROM Centralien, Ecole, EcoleSecteur, EcoleSecteurCentralien WHERE centralien_anneePromotion_ID = :anneePromotion_ID";
				sql += " AND ";
				sql += "centralien_ID = ecoleSecteurCentralien_centralien_ID";
				sql += " AND ";
				sql += "ecoleSecteurCentralien_ecoleSecteur_ID = ecoleSecteur_ID";
				sql += " AND ";
				sql += "ecoleSecteur_ecole_ID = ecole_ID";
				sql += "))";
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
			sql += "pays_ID IN (";
			sql += "SELECT ville_pays_ID FROM Ville, Ecole WHERE ecole_ID = :ecole_ID";
			sql += " AND ";
			sql += "ville_ID = ecole_ville_ID";
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
			sql += "pays_ID IN (";
			sql += "SELECT ville_pays_ID FROM Ville, EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_entreprise_ID = :entreprise_ID";
			sql += " AND ";
			sql += "ville_ID = entrepriseVilleSecteur_ville_ID";
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
				sql += "pays_ID IN (";
				sql += "SELECT ville_pays_ID FROM Ville, EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_secteur_ID = :secteur_ID";
				sql += " AND ";
				sql += "ville_ID = entrepriseVilleSecteur_ville_ID";
				sql += ")";
			} else {
				sql += "pays_ID IN (";
				sql += "SELECT ville_pays_ID FROM Ville WHERE ville_ID IN (";
				sql += "SELECT ecole_ville_ID FROM Ecole, EcoleSecteur WHERE ecoleSecteur_secteur_ID = :secteur_ID";
				sql += " AND ";
				sql += "ecoleSecteur_ecole_ID = ecole_ID";
				sql += "))";
			}
		}

		sql += " ORDER BY pays_nom ASC";

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

		List<SqlRow> listSqlRow = sqlQuery.findList();
		// Liste de 4 String : ID, nom, latitude et longitude
		List<String[]> listeDesPaysParCriteres = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get("pays_ID").toString();
			String nom = sqlRow.get("pays_nom").toString();
			String latitude = sqlRow.get("pays_latitude").toString();
			String longitude = sqlRow.get("pays_longitude").toString();
			String zoom = sqlRow.get("pays_zoom").toString();
			listeDesPaysParCriteres.add(new String[] { identifiant, nom,
			        latitude, longitude, zoom });
		}

		return ok(Json.toJson(listeDesPaysParCriteres));
	}

	/** Cette fonction retourne les pays sans coordonnees */
	public static List<String[]> paysSansCoordonnees() {
		String sql = "SELECT pays_ID, pays_nomMinuscule FROM Pays";
		sql += " WHERE ";
		sql += "(pays_latitude IS NULL OR pays_latitude = 0)";
		sql += " OR ";
		sql += "pays_longitude IS NULL OR pays_longitude = 0";

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();

		List<String[]> listeDesPays = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get("pays_ID").toString();
			String nom = sqlRow.get("pays_nomMinuscule").toString();
			listeDesPays.add(new String[] { identifiant, nom });
		}
		return listeDesPays;
	}

	/** Cette fonction met a jour les coordonnees d'un pays */
	public static void updateCoordonneesPays(String pays,
	        Coordonnees coordonnees) {
		String sql = "UPDATE Pays SET pays_latitude = :latitude, pays_longitude = :longitude WHERE pays_ID = :identifiant";
		SqlUpdate sqlUpdate = Ebean.createSqlUpdate(sql);
		sqlUpdate.setParameter("latitude", coordonnees.getLatitude());
		sqlUpdate.setParameter("longitude", coordonnees.getLongitude());
		sqlUpdate.setParameter("identifiant", Integer.parseInt(pays));
		sqlUpdate.execute();
	}
}
