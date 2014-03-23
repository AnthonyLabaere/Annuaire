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

		String sql = IConstantesBDD.SQL_SELECT + IConstantesBDD.VILLE_ID
		        + IConstantesBDD.SQL_COMMA + IConstantesBDD.VILLE_NOM
		        + IConstantesBDD.SQL_COMMA + IConstantesBDD.VILLE_LATITUDE
		        + IConstantesBDD.SQL_COMMA + IConstantesBDD.VILLE_LONGITUDE
		        + IConstantesBDD.SQL_FROM + IConstantesBDD.VILLE
		        + IConstantesBDD.SQL_WHERE + IConstantesBDD.VILLE_PAYS_ID
		        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
		        + IConstantesBDD.PAYS_ID + IConstantesBDD.SQL_ORDER_BY
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

		String param_centralien_ID = "centralien_ID";
		String param_anneePromotion_ID = "anneePromotion_ID";
		String param_ecole_ID = "ecole_ID";
		String param_entreprise_ID = "entreprise_ID";
		String param_secteur_ID = "secteur_ID";
		String param_pays_ID = "pays_ID";
		String param_ville_ID = "ville_ID";
		String param_ville_nom = "ville_nom";
		String param_ville_latitude = "ville_latitude";
		String param_ville_longitude = "ville_longitude";

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

		String sql = IConstantesBDD.SQL_SELECT + IConstantesBDD.VILLE_ID
		        + IConstantesBDD.SQL_COMMA + IConstantesBDD.VILLE_NOM
		        + IConstantesBDD.SQL_COMMA + IConstantesBDD.VILLE_LATITUDE
		        + IConstantesBDD.SQL_COMMA + IConstantesBDD.VILLE_LONGITUDE
		        + IConstantesBDD.SQL_FROM + IConstantesBDD.VILLE;

		// Si le filtre centralien est actif
		if (parametresPresents[0]) {
			wherePlace = true;
			sql += IConstantesBDD.SQL_WHERE;
			if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
				sql += IConstantesBDD.VILLE_ID
				        + IConstantesBDD.SQL_IN
				        + IConstantesBDD.SQL_BRACKET_OPEN
				        + IConstantesBDD.SQL_SELECT
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR_VILLE_ID
				        + IConstantesBDD.SQL_FROM
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR
				        + IConstantesBDD.SQL_COMMA
				        + IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN
				        + IConstantesBDD.SQL_COMMA
				        + IConstantesBDD.CENTRALIEN
				        + IConstantesBDD.SQL_WHERE
				        + IConstantesBDD.CENTRALIEN_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.SQL_COLON
				        + IConstantesBDD.CENTRALIEN_ID
				        + IConstantesBDD.SQL_AND
				        + IConstantesBDD.CENTRALIEN_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_CENTRALIEN_ID
				        + IConstantesBDD.SQL_AND
				        + IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_ENTREPRISEVILLESECTEUR_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR_ID
				        + IConstantesBDD.SQL_BRACKET_CLOSE;
			} else {
				sql += IConstantesBDD.VILLE_ID + IConstantesBDD.SQL_IN
				        + IConstantesBDD.SQL_BRACKET_OPEN
				        + IConstantesBDD.SQL_SELECT
				        + IConstantesBDD.ECOLE_VILLE_ID
				        + IConstantesBDD.SQL_FROM + IConstantesBDD.CENTRALIEN
				        + IConstantesBDD.SQL_COMMA + IConstantesBDD.ECOLE
				        + IConstantesBDD.SQL_COMMA
				        + IConstantesBDD.ECOLESECTEUR
				        + IConstantesBDD.SQL_COMMA
				        + IConstantesBDD.ECOLESECTEURCENTRALIEN
				        + IConstantesBDD.SQL_WHERE
				        + IConstantesBDD.CENTRALIEN_ID
				        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
				        + IConstantesBDD.CENTRALIEN_ID + IConstantesBDD.SQL_AND
				        + IConstantesBDD.CENTRALIEN_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.ECOLESECTEURCENTRALIEN_CENTRALIEN_ID
				        + IConstantesBDD.SQL_AND
				        + IConstantesBDD.ECOLESECTEURCENTRALIEN_ECOLESECTEUR_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.ECOLESECTEUR_ID
				        + IConstantesBDD.SQL_AND
				        + IConstantesBDD.ECOLESECTEUR_ECOLE_ID
				        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.ECOLE_ID
				        + IConstantesBDD.SQL_BRACKET_CLOSE;
			}
		}

		// Si le filtre anneePromotion est actif
		if (parametresPresents[1]) {
			if (wherePlace) {
				sql += IConstantesBDD.SQL_AND;
			} else {
				sql += IConstantesBDD.SQL_WHERE;
				wherePlace = true;
			}
			if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
				sql += IConstantesBDD.VILLE_ID
				        + IConstantesBDD.SQL_IN
				        + IConstantesBDD.SQL_BRACKET_OPEN
				        + IConstantesBDD.SQL_SELECT
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR_VILLE_ID
				        + IConstantesBDD.SQL_FROM
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR
				        + IConstantesBDD.SQL_COMMA
				        + IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN
				        + IConstantesBDD.SQL_WHERE
				        + IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_CENTRALIEN_ID
				        + IConstantesBDD.SQL_IN
				        + IConstantesBDD.SQL_BRACKET_OPEN
				        + IConstantesBDD.SQL_SELECT
				        + IConstantesBDD.CENTRALIEN_ID
				        + IConstantesBDD.SQL_FROM
				        + IConstantesBDD.CENTRALIEN
				        + IConstantesBDD.SQL_WHERE
				        + IConstantesBDD.CENTRALIEN_ANNEEPROMOTION_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.SQL_COLON
				        + IConstantesBDD.ANNEEPROMOTION_ID
				        + IConstantesBDD.SQL_AND
				        + IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_ENTREPRISEVILLESECTEUR_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR_ID
				        + IConstantesBDD.SQL_BRACKET_CLOSE
				        + IConstantesBDD.SQL_BRACKET_CLOSE;
			} else {
				sql += IConstantesBDD.VILLE_ID + IConstantesBDD.SQL_IN
				        + IConstantesBDD.SQL_BRACKET_OPEN
				        + IConstantesBDD.SQL_SELECT
				        + IConstantesBDD.ECOLE_VILLE_ID
				        + IConstantesBDD.SQL_FROM + IConstantesBDD.CENTRALIEN
				        + IConstantesBDD.SQL_COMMA + IConstantesBDD.ECOLE
				        + IConstantesBDD.SQL_COMMA
				        + IConstantesBDD.ECOLESECTEUR
				        + IConstantesBDD.SQL_COMMA
				        + IConstantesBDD.ECOLESECTEURCENTRALIEN
				        + IConstantesBDD.SQL_WHERE
				        + IConstantesBDD.CENTRALIEN_ANNEEPROMOTION_ID
				        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
				        + IConstantesBDD.ANNEEPROMOTION_ID
				        + IConstantesBDD.SQL_AND + IConstantesBDD.CENTRALIEN_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.ECOLESECTEURCENTRALIEN_CENTRALIEN_ID
				        + IConstantesBDD.SQL_AND
				        + IConstantesBDD.ECOLESECTEURCENTRALIEN_ECOLESECTEUR_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.ECOLESECTEUR_ID
				        + IConstantesBDD.SQL_AND
				        + IConstantesBDD.ECOLESECTEUR_ECOLE_ID
				        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.ECOLE_ID
				        + IConstantesBDD.SQL_BRACKET_CLOSE;
			}
		}

		// Si le filtre ecole est actif
		if (parametresPresents[2]) {
			if (wherePlace) {
				sql += IConstantesBDD.SQL_AND;
			} else {
				sql += IConstantesBDD.SQL_WHERE;
				wherePlace = true;
			}
			sql += IConstantesBDD.VILLE_ID + IConstantesBDD.SQL_IN
			        + IConstantesBDD.SQL_BRACKET_OPEN
			        + IConstantesBDD.SQL_SELECT + IConstantesBDD.ECOLE_VILLE_ID
			        + IConstantesBDD.SQL_FROM + IConstantesBDD.ECOLE
			        + IConstantesBDD.SQL_WHERE + IConstantesBDD.ECOLE_ID
			        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
			        + IConstantesBDD.ECOLE_ID
			        + IConstantesBDD.SQL_BRACKET_CLOSE;
		}

		// Si le filtre entreprise est actif
		if (parametresPresents[3]) {
			if (wherePlace) {
				sql += IConstantesBDD.SQL_AND;
			} else {
				sql += IConstantesBDD.SQL_WHERE;
				wherePlace = true;
			}
			sql += IConstantesBDD.VILLE_ID + IConstantesBDD.SQL_IN
			        + IConstantesBDD.SQL_BRACKET_OPEN
			        + IConstantesBDD.SQL_SELECT
			        + IConstantesBDD.ENTREPRISEVILLESECTEUR_VILLE_ID
			        + IConstantesBDD.SQL_FROM
			        + IConstantesBDD.ENTREPRISEVILLESECTEUR
			        + IConstantesBDD.SQL_WHERE
			        + IConstantesBDD.ENTREPRISEVILLESECTEUR_ENTREPRISE_ID
			        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
			        + IConstantesBDD.ENTREPRISE_ID
			        + IConstantesBDD.SQL_BRACKET_CLOSE;
		}

		// Si le filtre secteur est actif
		if (parametresPresents[4]) {
			if (wherePlace) {
				sql += IConstantesBDD.SQL_AND;
			} else {
				sql += IConstantesBDD.SQL_WHERE;
				wherePlace = true;
			}
			if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
				sql += IConstantesBDD.VILLE_ID + IConstantesBDD.SQL_IN
				        + IConstantesBDD.SQL_BRACKET_OPEN
				        + IConstantesBDD.SQL_SELECT
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR_VILLE_ID
				        + IConstantesBDD.SQL_FROM
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR
				        + IConstantesBDD.SQL_WHERE
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR_SECTEUR_ID
				        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
				        + IConstantesBDD.SECTEUR_ID
				        + IConstantesBDD.SQL_BRACKET_CLOSE;
			} else {
				sql += IConstantesBDD.VILLE_ID + IConstantesBDD.SQL_IN
				        + IConstantesBDD.SQL_BRACKET_OPEN
				        + IConstantesBDD.SQL_SELECT
				        + IConstantesBDD.ECOLE_VILLE_ID
				        + IConstantesBDD.SQL_FROM + IConstantesBDD.ECOLE
				        + IConstantesBDD.SQL_COMMA
				        + IConstantesBDD.ECOLESECTEUR
				        + IConstantesBDD.SQL_WHERE
				        + IConstantesBDD.ECOLESECTEUR_SECTEUR_ID
				        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
				        + IConstantesBDD.SECTEUR_ID + IConstantesBDD.SQL_AND
				        + IConstantesBDD.ECOLESECTEUR_ECOLE_ID
				        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.ECOLE_ID
				        + IConstantesBDD.SQL_BRACKET_CLOSE;
			}
		}

		// pays_ID est forcement present
		if (wherePlace) {
			sql += IConstantesBDD.SQL_AND;
		} else {
			sql += IConstantesBDD.SQL_WHERE;
			wherePlace = true;
		}
		sql += IConstantesBDD.VILLE_PAYS_ID + IConstantesBDD.SQL_EQUAL
		        + IConstantesBDD.SQL_COLON + IConstantesBDD.PAYS_ID
		        + IConstantesBDD.SQL_ORDER_BY + IConstantesBDD.VILLE_NOM;

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		if (parametresPresents[0]) {
			sqlQuery.setParameter(param_centralien_ID,
			        Integer.parseInt(centralien_ID));
		}
		if (parametresPresents[1]) {
			sqlQuery.setParameter(param_anneePromotion_ID,
			        Integer.parseInt(anneePromotion_ID));
		}
		if (parametresPresents[2]) {
			sqlQuery.setParameter(param_ecole_ID, Integer.parseInt(ecole_ID));
		}
		if (parametresPresents[3]) {
			sqlQuery.setParameter(param_entreprise_ID,
			        Integer.parseInt(entreprise_ID));
		}
		if (parametresPresents[4]) {
			sqlQuery.setParameter(param_secteur_ID,
			        Integer.parseInt(secteur_ID));
		}
		sqlQuery.setParameter(param_pays_ID, Integer.parseInt(pays_ID));

		List<SqlRow> listSqlRow = sqlQuery.findList();
		// Liste de 4 String : ID, nom, latitude et longitude
		List<String[]> listeDesVillesParCriteres = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get(param_ville_ID).toString();
			String nom = sqlRow.get(param_ville_nom).toString();
			String latitude = sqlRow.get(param_ville_latitude).toString();
			String longitude = sqlRow.get(param_ville_longitude).toString();
			listeDesVillesParCriteres.add(new String[] { identifiant, nom,
			        latitude, longitude });
		}

		return ok(Json.toJson(listeDesVillesParCriteres));
	}

	/** Cette fonction retourne les villes sans coordonnees */
	public static List<String[]> villesSansCoordonnees() {
		String param_ville_ID = "ville_ID";
		String param_ville_nom = "ville_nom";

		String sql = IConstantesBDD.SQL_SELECT + IConstantesBDD.VILLE_ID
		        + IConstantesBDD.SQL_COMMA + IConstantesBDD.VILLE_NOM
		        + IConstantesBDD.SQL_FROM + IConstantesBDD.VILLE
		        + IConstantesBDD.SQL_WHERE + IConstantesBDD.VILLE_LATITUDE
		        + IConstantesBDD.SQL_IS + IConstantesBDD.SQL_NULL
		        + IConstantesBDD.SQL_OR + IConstantesBDD.VILLE_LONGITUDE
		        + IConstantesBDD.SQL_IS + IConstantesBDD.SQL_NULL;

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();

		List<String[]> listeDesVilles = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get(param_ville_ID).toString();
			String nom = sqlRow.get(param_ville_nom).toString();
			listeDesVilles.add(new String[] { identifiant, nom });
		}

		return listeDesVilles;

	}

	/** Cette fonction met a jour les coordonnees d'une ville */
	public static void updateCoordonneesVille(String ville,
	        Coordonnees coordonnees) {
		String param_latitude = "latitude";
		String param_longitude = "longitude";
		String param_identifiant = "identifiant";

		String sql = IConstantesBDD.SQL_UPDATE + IConstantesBDD.VILLE
		        + IConstantesBDD.SQL_SET + IConstantesBDD.VILLE_LATITUDE
		        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
		        + param_latitude + IConstantesBDD.SQL_COMMA
		        + IConstantesBDD.VILLE_LONGITUDE + IConstantesBDD.SQL_EQUAL
		        + IConstantesBDD.SQL_COLON + param_longitude
		        + IConstantesBDD.SQL_WHERE + IConstantesBDD.VILLE_ID
		        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
		        + param_identifiant;

		SqlUpdate sqlUpdate = Ebean.createSqlUpdate(sql);
		sqlUpdate.setParameter(param_latitude, coordonnees.getLatitude());
		sqlUpdate.setParameter(param_longitude, coordonnees.getLongitude());
		sqlUpdate.setParameter(param_identifiant, Integer.parseInt(ville));
		sqlUpdate.execute();
	}
}
