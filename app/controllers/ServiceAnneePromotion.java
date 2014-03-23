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

import constantes.IConstantes;
import constantes.IConstantesBDD;


/**
 * Service Ajax concernant la table AnneePromotion
 * 
 * @author Anthony
 * 
 */
public class ServiceAnneePromotion extends Controller {

	public static Result AJAX_listeDesAnneesPromotion() {
		String param_anneePromotion = "anneePromotion_ID";
		String param_anneePromotion_libelle = "anneePromotion_libelle";
		
		String sql = IConstantesBDD.SQL_SELECT 
				+ IConstantesBDD.ANNEEPROMOTION_ID 
				+ IConstantesBDD.SQL_COMMA
				+ IConstantesBDD.ANNEEPROMOTION_LIBELLE
				+ IConstantesBDD.SQL_FROM 
				+ IConstantesBDD.ANNEEPROMOTION 
				+ IConstantesBDD.SQL_ORDER_BY 
				+ IConstantesBDD.ANNEEPROMOTION_LIBELLE;
		
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();
		// Liste de double String : le premier est l'ID et le deuxième est le libelle
		List<String[]> listeDesAnneesPromotion = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get(param_anneePromotion).toString();
			String libelle = sqlRow.get(param_anneePromotion_libelle).toString();
			listeDesAnneesPromotion.add(new String[] { identifiant, libelle });
		}

		return ok(Json.toJson(listeDesAnneesPromotion));
	}

	public static Result AJAX_listeDesAnneesPromotionSelonCriteres(	
			String centralien_ID, String ecole_ID, String entreprise_ID,
	        String secteur_ID, String pays_ID, String ville_ID) {
		
		String param_centralien_ID = "centralien_ID";
		String param_ecole_ID = "ecole_ID";
		String param_entreprise_ID = "entreprise_ID";
		String param_secteur_ID = "secteur_ID";
		String param_pays_ID = "pays_ID";
		String param_ville_ID = "ville_ID";	
		String param_anneePromotion = "anneePromotion_ID";
		String param_anneePromotion_libelle = "anneePromotion_libelle";
		
		Boolean[] parametresPresents = new Boolean[] {
				centralien_ID != null && !centralien_ID.isEmpty(),
		        ecole_ID != null
		                && !ecole_ID.isEmpty()
		                && !ecole_ID
		                        .equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF),
		        entreprise_ID != null
		                && !entreprise_ID.isEmpty()
		                && !entreprise_ID
		                        .equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF),
		        secteur_ID != null && !secteur_ID.isEmpty(),
		        pays_ID != null && !pays_ID.isEmpty(),
		        ville_ID != null && !ville_ID.isEmpty() };

		Boolean wherePlace = false;

		String sql = IConstantesBDD.SQL_SELECT 
				+ IConstantesBDD.ANNEEPROMOTION_ID
				+ IConstantesBDD.SQL_COMMA
				+ IConstantesBDD.ANNEEPROMOTION_LIBELLE
				+ IConstantesBDD.SQL_FROM 
				+ IConstantesBDD.ANNEEPROMOTION;
		
		// Si le filtre centralien est actif
		if (parametresPresents[0]) {
			wherePlace = true;
			sql += IConstantesBDD.SQL_WHERE 
					+ IConstantesBDD.ANNEEPROMOTION_ID 
					+ IConstantesBDD.SQL_EQUAL 
					+ IConstantesBDD.SQL_BRACKET_OPEN 
					+ IConstantesBDD.SQL_SELECT 
					+ IConstantesBDD.CENTRALIEN_ANNEEPROMOTION_ID
					+ IConstantesBDD.SQL_FROM
					+ IConstantesBDD.CENTRALIEN
					+ IConstantesBDD.SQL_WHERE 
					+ IConstantesBDD.CENTRALIEN_ID
					+ IConstantesBDD.SQL_EQUAL 
					+ IConstantesBDD.SQL_COLON
					+ IConstantesBDD.CENTRALIEN_ID
					+ IConstantesBDD.SQL_BRACKET_CLOSE;
		}

		// Si le filtre ecole est actif
		if (parametresPresents[1]) {
			if (wherePlace) {
				sql += IConstantesBDD.SQL_AND;
			} else {
				sql += IConstantesBDD.SQL_WHERE;
				wherePlace = true;
			}
			sql += IConstantesBDD.ANNEEPROMOTION_ID 
					+ IConstantesBDD.SQL_IN
					+ IConstantesBDD.SQL_BRACKET_OPEN
					+ IConstantesBDD.SQL_SELECT
					+ IConstantesBDD.CENTRALIEN_ANNEEPROMOTION_ID
					+ IConstantesBDD.SQL_FROM
					+ IConstantesBDD.CENTRALIEN
					+ IConstantesBDD.SQL_WHERE
					+ IConstantesBDD.CENTRALIEN_ID
					+ IConstantesBDD.SQL_IN
					+ IConstantesBDD.SQL_BRACKET_OPEN
					+ IConstantesBDD.SQL_SELECT 
					+ IConstantesBDD.ECOLESECTEURCENTRALIEN_ID
					+ IConstantesBDD.SQL_FROM
					+ IConstantesBDD.ECOLESECTEURCENTRALIEN
					+ IConstantesBDD.SQL_COMMA
					+ IConstantesBDD.ECOLESECTEUR
					+ IConstantesBDD.SQL_WHERE
					+ IConstantesBDD.ECOLESECTEUR_ECOLE_ID
					+ IConstantesBDD.SQL_EQUAL
					+ IConstantesBDD.SQL_COLON
					+ IConstantesBDD.ECOLE_ID
					+ IConstantesBDD.SQL_AND
					+ IConstantesBDD.ECOLESECTEURCENTRALIEN_ECOLESECTEUR_ID
					+ IConstantesBDD.SQL_EQUAL
					+ IConstantesBDD.ECOLESECTEUR_ID
					+ IConstantesBDD.SQL_BRACKET_CLOSE
					+ IConstantesBDD.SQL_BRACKET_CLOSE;
		}

		// Si le filtre entreprise est actif
		if (parametresPresents[2]) {
			if (wherePlace) {
				sql += IConstantesBDD.SQL_AND;
			} else {
				sql += IConstantesBDD.SQL_WHERE;
				wherePlace = true;
			}
			sql += IConstantesBDD.ANNEEPROMOTION_ID
					+ IConstantesBDD.SQL_IN
					+ IConstantesBDD.SQL_BRACKET_OPEN
					+ IConstantesBDD.SQL_SELECT
					+ IConstantesBDD.CENTRALIEN_ANNEEPROMOTION_ID
					+ IConstantesBDD.SQL_FROM
					+ IConstantesBDD.CENTRALIEN
					+ IConstantesBDD.SQL_WHERE
					+ IConstantesBDD.CENTRALIEN_ID 
					+ IConstantesBDD.SQL_IN
					+ IConstantesBDD.SQL_BRACKET_OPEN
					+ IConstantesBDD.SQL_SELECT
					+ IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_ID
					+ IConstantesBDD.SQL_FROM
					+ IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN
					+ IConstantesBDD.SQL_COMMA
					+ IConstantesBDD.ENTREPRISEVILLESECTEUR
					+ IConstantesBDD.SQL_WHERE
					+ IConstantesBDD.ENTREPRISEVILLESECTEUR_ENTREPRISE_ID
					+ IConstantesBDD.SQL_EQUAL
					+ IConstantesBDD.SQL_COLON
					+ IConstantesBDD.ENTREPRISE_ID
					+ IConstantesBDD.SQL_AND
					+ IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_ENTREPRISEVILLESECTEUR_ID
					+ IConstantesBDD.SQL_EQUAL
					+ IConstantesBDD.ENTREPRISEVILLESECTEUR_ID
					+ IConstantesBDD.SQL_BRACKET_CLOSE
					+ IConstantesBDD.SQL_BRACKET_CLOSE;
		}
		
		// Si le filtre secteur est actif
		if (parametresPresents[3]) {
			if (wherePlace) {
				sql += IConstantesBDD.SQL_AND;
			} else {
				sql += IConstantesBDD.SQL_WHERE;
				wherePlace = true;
			}
			
			if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)){
				sql += IConstantesBDD.ANNEEPROMOTION_ID
						+ IConstantesBDD.SQL_IN
						+ IConstantesBDD.SQL_BRACKET_OPEN
						+ IConstantesBDD.SQL_SELECT
						+ IConstantesBDD.CENTRALIEN_ANNEEPROMOTION_ID
						+ IConstantesBDD.SQL_FROM
						+ IConstantesBDD.CENTRALIEN
						+ IConstantesBDD.SQL_WHERE
						+ IConstantesBDD.CENTRALIEN_ID
						+ IConstantesBDD.SQL_IN
						+ IConstantesBDD.SQL_BRACKET_OPEN
						+ IConstantesBDD.SQL_SELECT
						+ IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_ID
						+ IConstantesBDD.SQL_FROM
						+ IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN
						+ IConstantesBDD.SQL_COMMA
						+ IConstantesBDD.ENTREPRISEVILLESECTEUR
						+ IConstantesBDD.SQL_WHERE
						+ IConstantesBDD.ENTREPRISEVILLESECTEUR_SECTEUR_ID
						+ IConstantesBDD.SQL_EQUAL
						+ IConstantesBDD.SQL_COLON
						+ IConstantesBDD.SECTEUR_ID
						+ IConstantesBDD.SQL_AND
						+ IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_ENTREPRISEVILLESECTEUR_ID
						+ IConstantesBDD.SQL_EQUAL
						+ IConstantesBDD.ENTREPRISEVILLESECTEUR_ID
						+ IConstantesBDD.SQL_BRACKET_CLOSE
						+ IConstantesBDD.SQL_BRACKET_CLOSE;
			} else {
				sql += IConstantesBDD.ANNEEPROMOTION_ID
						+ IConstantesBDD.SQL_IN
						+ IConstantesBDD.SQL_BRACKET_OPEN
						+ IConstantesBDD.CENTRALIEN_ANNEEPROMOTION_ID
						+ IConstantesBDD.SQL_FROM
						+ IConstantesBDD.CENTRALIEN 
						+ IConstantesBDD.SQL_WHERE 
						+ IConstantesBDD.CENTRALIEN_ID 
						+ IConstantesBDD.SQL_IN
						+ IConstantesBDD.SQL_BRACKET_OPEN
						+ IConstantesBDD.SQL_SELECT
						+ IConstantesBDD.ECOLESECTEURCENTRALIEN_CENTRALIEN_ID 
						+ IConstantesBDD.SQL_FROM 
						+ IConstantesBDD.ECOLESECTEURCENTRALIEN
						+ IConstantesBDD.SQL_COMMA
						+ IConstantesBDD.ECOLESECTEUR 
						+ IConstantesBDD.SQL_WHERE 
						+ IConstantesBDD.ECOLESECTEUR_SECTEUR_ID 
						+ IConstantesBDD.SQL_EQUAL
						+ IConstantesBDD.SQL_COLON
						+ IConstantesBDD.SECTEUR_ID
						+ IConstantesBDD.SQL_AND
						+ IConstantesBDD.ECOLESECTEURCENTRALIEN_ECOLESECTEUR_ID 
						+ IConstantesBDD.SQL_EQUAL
						+ IConstantesBDD.ECOLESECTEUR_ID
						+ IConstantesBDD.SQL_BRACKET_CLOSE
						+ IConstantesBDD.SQL_BRACKET_CLOSE;
			}
		}
		
		// Si le filtre pays est actif mais pas le filtre ville
		if (parametresPresents[4] && !parametresPresents[5]) {
			if (wherePlace) {
				sql += IConstantesBDD.SQL_AND;
			} else {
				sql += IConstantesBDD.SQL_WHERE;
				wherePlace = true;
			}
			if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)){
				sql += IConstantesBDD.ANNEEPROMOTION_ID 
						+ IConstantesBDD.SQL_IN
						+ IConstantesBDD.SQL_BRACKET_OPEN
						+ IConstantesBDD.SQL_SELECT
						+ IConstantesBDD.CENTRALIEN_ANNEEPROMOTION_ID
						+ IConstantesBDD.SQL_FROM
						+ IConstantesBDD.CENTRALIEN 
						+ IConstantesBDD.SQL_WHERE
						+ IConstantesBDD.CENTRALIEN_ID 
						+ IConstantesBDD.SQL_IN
						+ IConstantesBDD.SQL_BRACKET_OPEN		
						+ IConstantesBDD.SQL_SELECT
						+ IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_ID 
						+ IConstantesBDD.SQL_FROM 
						+ IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN
						+ IConstantesBDD.SQL_COMMA 
						+ IConstantesBDD.ENTREPRISEVILLESECTEUR 
						+ IConstantesBDD.SQL_WHERE
						+ IConstantesBDD.ENTREPRISEVILLESECTEUR_VILLE_ID
						+ IConstantesBDD.SQL_IN
						+ IConstantesBDD.SQL_BRACKET_OPEN
						+ IConstantesBDD.SQL_SELECT
						+ IConstantesBDD.VILLE_ID 
						+ IConstantesBDD.SQL_FROM
						+ IConstantesBDD.VILLE
						+ IConstantesBDD.SQL_WHERE
						+ IConstantesBDD.VILLE_PAYS_ID 
						+ IConstantesBDD.SQL_EQUAL
						+ IConstantesBDD.SQL_COLON
						+ IConstantesBDD.PAYS_ID
						+ IConstantesBDD.SQL_BRACKET_CLOSE
						+ IConstantesBDD.SQL_AND
						+ IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_ENTREPRISEVILLESECTEUR_ID 
						+ IConstantesBDD.SQL_EQUAL 
						+ IConstantesBDD.ENTREPRISEVILLESECTEUR_ID
						+ IConstantesBDD.SQL_BRACKET_CLOSE
						+ IConstantesBDD.SQL_BRACKET_CLOSE;
			} else {
				sql += IConstantesBDD.ANNEEPROMOTION_ID 
						+ IConstantesBDD.SQL_IN
						+ IConstantesBDD.SQL_BRACKET_OPEN
						+ IConstantesBDD.SQL_SELECT
						+ IConstantesBDD.CENTRALIEN_ANNEEPROMOTION_ID 
						+ IConstantesBDD.SQL_FROM 
						+ IConstantesBDD.CENTRALIEN 
						+ IConstantesBDD.SQL_WHERE 
						+ IConstantesBDD.CENTRALIEN_ID
						+ IConstantesBDD.SQL_IN
						+ IConstantesBDD.SQL_BRACKET_OPEN
						+ IConstantesBDD.SQL_SELECT
						+ IConstantesBDD.ECOLESECTEURCENTRALIEN_CENTRALIEN_ID
						+ IConstantesBDD.SQL_FROM
						+ IConstantesBDD.ECOLESECTEUR
						+ IConstantesBDD.SQL_COMMA
						+ IConstantesBDD.ECOLESECTEURCENTRALIEN
						+ IConstantesBDD.SQL_COMMA
						+ IConstantesBDD.ECOLE
						+ IConstantesBDD.SQL_WHERE
						+ IConstantesBDD.ECOLE_VILLE_ID
						+ IConstantesBDD.SQL_IN
						+ IConstantesBDD.SQL_BRACKET_OPEN
						+ IConstantesBDD.SQL_SELECT
						+ IConstantesBDD.VILLE_ID
						+ IConstantesBDD.SQL_FROM
						+ IConstantesBDD.VILLE
						+ IConstantesBDD.SQL_WHERE
						+ IConstantesBDD.VILLE_PAYS_ID
						+ IConstantesBDD.SQL_EQUAL
						+ IConstantesBDD.SQL_COLON
						+ IConstantesBDD.PAYS_ID
						+ IConstantesBDD.SQL_BRACKET_CLOSE
						+ IConstantesBDD.SQL_AND
						+ IConstantesBDD.ECOLESECTEURCENTRALIEN_ECOLESECTEUR_ID
						+ IConstantesBDD.SQL_EQUAL
						+ IConstantesBDD.ECOLESECTEUR_ID
						+ IConstantesBDD.SQL_AND
						+ IConstantesBDD.ECOLESECTEUR_ECOLE_ID
						+ IConstantesBDD.SQL_EQUAL
						+ IConstantesBDD.ECOLE_ID
						+ IConstantesBDD.SQL_BRACKET_CLOSE
						+ IConstantesBDD.SQL_BRACKET_CLOSE;
			}
		}
		
		// Si le filtre ville est actif
		if (parametresPresents[5]) {
			if (wherePlace) {
				sql += IConstantesBDD.SQL_AND;
			} else {
				sql += IConstantesBDD.SQL_WHERE;
				wherePlace = true;
			}
			if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)){
				sql += IConstantesBDD.ANNEEPROMOTION_ID
						+ IConstantesBDD.SQL_IN
						+ IConstantesBDD.SQL_BRACKET_OPEN
						+ IConstantesBDD.SQL_SELECT
						+ IConstantesBDD.CENTRALIEN_ANNEEPROMOTION_ID
						+ IConstantesBDD.SQL_FROM
						+ IConstantesBDD.CENTRALIEN 
						+ IConstantesBDD.SQL_WHERE
						+ IConstantesBDD.CENTRALIEN_ID
						+ IConstantesBDD.SQL_IN
						+ IConstantesBDD.SQL_BRACKET_OPEN
						+ IConstantesBDD.SQL_SELECT
						+ IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_ID 
						+ IConstantesBDD.SQL_FROM 
						+ IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN
						+ IConstantesBDD.SQL_COMMA
						+ IConstantesBDD.ENTREPRISEVILLESECTEUR
						+ IConstantesBDD.SQL_WHERE
						+ IConstantesBDD.ENTREPRISEVILLESECTEUR_VILLE_ID
						+ IConstantesBDD.SQL_EQUAL
						+ IConstantesBDD.SQL_COLON
						+ IConstantesBDD.VILLE_ID
						+ IConstantesBDD.SQL_AND
						+ IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_ENTREPRISEVILLESECTEUR_ID
						+ IConstantesBDD.SQL_EQUAL
						+ IConstantesBDD.ENTREPRISEVILLESECTEUR_ID
						+ IConstantesBDD.SQL_BRACKET_CLOSE
						+ IConstantesBDD.SQL_BRACKET_CLOSE;
			} else {
				sql += IConstantesBDD.ANNEEPROMOTION_ID 
						+ IConstantesBDD.SQL_IN
						+ IConstantesBDD.SQL_BRACKET_OPEN
						+ IConstantesBDD.SQL_SELECT
						+ IConstantesBDD.CENTRALIEN_ANNEEPROMOTION_ID
						+ IConstantesBDD.SQL_FROM 
						+ IConstantesBDD.CENTRALIEN
						+ IConstantesBDD.SQL_WHERE 
						+ IConstantesBDD.CENTRALIEN_ID
						+ IConstantesBDD.SQL_IN
						+ IConstantesBDD.SQL_BRACKET_OPEN
						+ IConstantesBDD.SQL_SELECT
						+ IConstantesBDD.ECOLESECTEURCENTRALIEN_CENTRALIEN_ID
						+ IConstantesBDD.SQL_FROM
						+ IConstantesBDD.ECOLESECTEURCENTRALIEN
						+ IConstantesBDD.SQL_COMMA
						+ IConstantesBDD.ECOLE
						+ IConstantesBDD.SQL_COMMA
						+ IConstantesBDD.ECOLESECTEUR
						+ IConstantesBDD.SQL_WHERE
						+ IConstantesBDD.ECOLE_VILLE_ID
						+ IConstantesBDD.SQL_EQUAL
						+ IConstantesBDD.SQL_COLON
						+ IConstantesBDD.VILLE_ID
						+ IConstantesBDD.SQL_AND
						+ IConstantesBDD.ECOLESECTEURCENTRALIEN_ECOLESECTEUR_ID 
						+ IConstantesBDD.SQL_EQUAL
						+ IConstantesBDD.ECOLESECTEUR_ID
						+ IConstantesBDD.SQL_AND
						+ IConstantesBDD.ECOLESECTEUR_ECOLE_ID
						+ IConstantesBDD.SQL_EQUAL
						+ IConstantesBDD.ECOLE_ID
						+ IConstantesBDD.SQL_BRACKET_CLOSE
						+ IConstantesBDD.SQL_BRACKET_CLOSE;
			}
		}

		sql += IConstantesBDD.SQL_ORDER_BY 
				+ IConstantesBDD.ANNEEPROMOTION_LIBELLE;

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		if (parametresPresents[0]) {
			sqlQuery.setParameter(param_centralien_ID, Integer.parseInt(centralien_ID));
		}
		if (parametresPresents[1]) {
			sqlQuery.setParameter(param_ecole_ID, Integer.parseInt(ecole_ID));
		}
		if (parametresPresents[2]) {
			sqlQuery.setParameter(param_entreprise_ID, Integer.parseInt(entreprise_ID));
		}
		if (parametresPresents[3]) {
			sqlQuery.setParameter(param_secteur_ID, Integer.parseInt(secteur_ID));
		}
		if (parametresPresents[4] && !parametresPresents[5]) {
			sqlQuery.setParameter(param_pays_ID, Integer.parseInt(pays_ID));
		}
		if (parametresPresents[5]) {
			sqlQuery.setParameter(param_ville_ID, Integer.parseInt(ville_ID));
		}

		List<SqlRow> listSqlRow = sqlQuery.findList();
		// Liste de double String : le premier est l'ID et le deuxième est le libelle
		List<String[]> listeDesAnneesPromotionParCriteres = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String identifiant = sqlRow.get(param_anneePromotion).toString();
			String libelle = sqlRow.get(param_anneePromotion_libelle).toString();
			listeDesAnneesPromotionParCriteres.add(new String[] { identifiant, libelle });
		}

		return ok(Json.toJson(listeDesAnneesPromotionParCriteres));
	}

}
