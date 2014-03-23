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
 * Service Ajax concernant la table Centralien
 * 
 * @author Anthony
 * 
 */
public class ServiceCentralien extends Controller {

	private static final String PARAM_ANNEEPROMOTION = "anneePromotion_ID";
	private static final String PARAM_ECOLE = "ecole_ID";
	private static final String PARAM_ENTREPRISE = "entreprise_ID";
	private static final String PARAM_SECTEUR = "secteur_ID";
	private static final String PARAM_PAYS = "pays_ID";
	private static final String PARAM_VILLE = "ville_ID";

	private static String conditionsSelonFiltres(boolean historique,
	        String anneePromotion_ID, String ecole_ID, String entreprise_ID,
	        String secteur_ID, String ville_ID, Boolean[] parametresPresents) {
		String sql = "";

		boolean entrepriseActif = ecole_ID
		        .equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF);

		// On ajoute la contrainte d'historique
		if (!historique) {
			sql += IConstantesBDD.SQL_AND;
			if (entrepriseActif) {
				sql += "entrepriseVilleSecteurCentralien_centralien_ID IN (";
				sql += "SELECT posteActuel_entrepriseVilleSecteurCentralien_ID FROM PosteActuel";
				sql += ")";
			} else {
				sql += "ecoleSecteurCentralien_ID IN (";
				sql += "SELECT posteActuel_ecoleSecteurCentralien_ID FROM PosteActuel";
				sql += ")";
			}
		}

		// Si le filtre anneePromotion est actif
		if (parametresPresents[0]) {
			sql += IConstantesBDD.SQL_AND;
			sql += IConstantesBDD.CENTRALIEN_ANNEEPROMOTION_ID
			        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
			        + PARAM_ANNEEPROMOTION;
		}

		// Si le filtre ecole est actif
		if (parametresPresents[1]) {
			sql += IConstantesBDD.SQL_AND;
			sql += IConstantesBDD.CENTRALIEN_ID + IConstantesBDD.SQL_IN
			        + IConstantesBDD.SQL_BRACKET_OPEN;
			sql += IConstantesBDD.SQL_SELECT
			        + IConstantesBDD.ECOLESECTEURCENTRALIEN_CENTRALIEN_ID
			        + IConstantesBDD.SQL_FROM
			        + IConstantesBDD.ECOLESECTEURCENTRALIEN
			        + IConstantesBDD.SQL_COMMA + IConstantesBDD.ECOLESECTEUR
			        + IConstantesBDD.SQL_WHERE
			        + IConstantesBDD.ECOLESECTEUR_ECOLE_ID
			        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
			        + PARAM_ECOLE;
			sql += IConstantesBDD.SQL_AND;
			sql += IConstantesBDD.ECOLESECTEURCENTRALIEN_ECOLESECTEUR_ID
			        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.ECOLESECTEUR_ID;
			sql += IConstantesBDD.SQL_BRACKET_CLOSE;
		}

		// Si le filtre entreprise est actif
		if (parametresPresents[2]) {
			sql += IConstantesBDD.SQL_AND;

			sql += IConstantesBDD.CENTRALIEN_ID + IConstantesBDD.SQL_IN
			        + IConstantesBDD.SQL_BRACKET_OPEN;
			sql += IConstantesBDD.SQL_SELECT
			        + IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_CENTRALIEN_ID
			        + IConstantesBDD.SQL_FROM
			        + IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN
			        + IConstantesBDD.SQL_COMMA
			        + IConstantesBDD.ENTREPRISEVILLESECTEUR
			        + IConstantesBDD.SQL_WHERE
			        + IConstantesBDD.ENTREPRISEVILLESECTEUR_ENTREPRISE_ID
			        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
			        + PARAM_ENTREPRISE;
			sql += IConstantesBDD.SQL_AND;
			sql += IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_ENTREPRISEVILLESECTEUR_ID
			        + IConstantesBDD.SQL_EQUAL
			        + IConstantesBDD.ENTREPRISEVILLESECTEUR_ID;
			sql += IConstantesBDD.SQL_BRACKET_CLOSE;
		}

		// Si le filtre secteur est actif
		if (parametresPresents[3]) {
			sql += IConstantesBDD.SQL_AND;
			if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
				sql += IConstantesBDD.CENTRALIEN_ID + IConstantesBDD.SQL_IN
				        + IConstantesBDD.SQL_BRACKET_OPEN;
				sql += IConstantesBDD.SQL_SELECT
				        + IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_CENTRALIEN_ID
				        + IConstantesBDD.SQL_FROM
				        + IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN
				        + IConstantesBDD.SQL_COMMA
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR
				        + IConstantesBDD.SQL_WHERE
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR_SECTEUR_ID
				        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
				        + PARAM_SECTEUR;
				sql += IConstantesBDD.SQL_AND;
				sql += IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_ENTREPRISEVILLESECTEUR_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR_ID;
				sql += IConstantesBDD.SQL_BRACKET_CLOSE;
			} else {
				sql += IConstantesBDD.CENTRALIEN_ID + IConstantesBDD.SQL_IN
				        + IConstantesBDD.SQL_BRACKET_OPEN;
				sql += IConstantesBDD.SQL_SELECT
				        + IConstantesBDD.ECOLESECTEURCENTRALIEN_CENTRALIEN_ID
				        + IConstantesBDD.SQL_FROM
				        + IConstantesBDD.ECOLESECTEURCENTRALIEN
				        + IConstantesBDD.SQL_COMMA
				        + IConstantesBDD.ECOLESECTEUR
				        + IConstantesBDD.SQL_WHERE
				        + IConstantesBDD.ECOLESECTEUR_SECTEUR_ID
				        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
				        + PARAM_SECTEUR;
				sql += IConstantesBDD.SQL_AND;
				sql += IConstantesBDD.ECOLESECTEURCENTRALIEN_ECOLESECTEUR_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.ECOLESECTEUR_ID;
				sql += IConstantesBDD.SQL_BRACKET_CLOSE;
			}
		}

		// Si le filtre ville est actif
		if (parametresPresents[5]) {
			sql += IConstantesBDD.SQL_AND;
			if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
				sql += IConstantesBDD.CENTRALIEN_ID + IConstantesBDD.SQL_IN
				        + IConstantesBDD.SQL_BRACKET_OPEN;
				sql += IConstantesBDD.SQL_SELECT
				        + IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_CENTRALIEN_ID
				        + IConstantesBDD.SQL_FROM
				        + IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN
				        + IConstantesBDD.SQL_COMMA
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR
				        + IConstantesBDD.SQL_WHERE
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR_VILLE_ID
				        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
				        + PARAM_VILLE;
				sql += IConstantesBDD.SQL_AND;
				sql += IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_ENTREPRISEVILLESECTEUR_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR_ID;
				sql += IConstantesBDD.SQL_BRACKET_CLOSE;
			} else {
				sql += IConstantesBDD.CENTRALIEN_ID + IConstantesBDD.SQL_IN
				        + IConstantesBDD.SQL_BRACKET_OPEN;
				sql += IConstantesBDD.SQL_SELECT
				        + IConstantesBDD.ECOLESECTEURCENTRALIEN_CENTRALIEN_ID
				        + IConstantesBDD.SQL_FROM
				        + IConstantesBDD.ECOLESECTEURCENTRALIEN
				        + IConstantesBDD.SQL_COMMA + IConstantesBDD.ECOLE
				        + IConstantesBDD.SQL_COMMA
				        + IConstantesBDD.ECOLESECTEUR
				        + IConstantesBDD.SQL_WHERE
				        + IConstantesBDD.ECOLE_VILLE_ID
				        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
				        + PARAM_VILLE;
				sql += IConstantesBDD.SQL_AND;
				sql += IConstantesBDD.ECOLESECTEURCENTRALIEN_ECOLESECTEUR_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.ECOLESECTEUR_ID;
				sql += IConstantesBDD.SQL_AND;
				sql += IConstantesBDD.ECOLESECTEUR_ECOLE_ID
				        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.ECOLE_ID;
				sql += IConstantesBDD.SQL_BRACKET_CLOSE;
			}
		}

		return sql;
	}

	public static Result AJAX_listeDesCentraliens() {
		String identifiant = "identifiant";
		String nomPrenom = "nomPrenom";

		String sql = IConstantesBDD.SQL_SELECT + IConstantesBDD.CENTRALIEN_ID
		        + IConstantesBDD.SQL_AS + identifiant
		        + IConstantesBDD.SQL_COMMA + IConstantesBDD.SQL_CONCAT
		        + IConstantesBDD.SQL_BRACKET_OPEN
		        + IConstantesBDD.CENTRALIEN_NOM + IConstantesBDD.SQL_COMMA
		        + "' '" + IConstantesBDD.SQL_COMMA
		        + IConstantesBDD.CENTRALIEN_PRENOM
		        + IConstantesBDD.SQL_BRACKET_CLOSE + IConstantesBDD.SQL_AS
		        + nomPrenom + IConstantesBDD.SQL_FROM
		        + IConstantesBDD.CENTRALIEN;

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		List<SqlRow> listSqlRow = sqlQuery.findList();
		// Liste de double String : le premier est l'ID et le deuxième est le
		// nomPrenom
		List<String[]> listeDesCentraliens = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String resultat_identifiant = sqlRow.get(identifiant).toString();
			String resultat_nomPrenom = sqlRow.get(nomPrenom).toString();
			listeDesCentraliens.add(new String[] { resultat_identifiant,
			        resultat_nomPrenom });
		}

		return ok(Json.toJson(listeDesCentraliens));
	}

	public static Result AJAX_listeDesCentraliensSelonCriteres(
	        boolean historique, String anneePromotion_ID, String ecole_ID,
	        String entreprise_ID, String secteur_ID, String pays_ID,
	        String ville_ID) {
		String identifiant = "identifiant";
		String nomPrenom = "nomPrenom";

		boolean entrepriseActif = ecole_ID
		        .equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF);

		Boolean[] parametresPresents = new Boolean[] {
		        anneePromotion_ID != null && !anneePromotion_ID.isEmpty(),
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

		String sql = IConstantesBDD.SQL_SELECT + IConstantesBDD.SQL_DISTINCT
		        + IConstantesBDD.CENTRALIEN_ID + IConstantesBDD.SQL_AS
		        + identifiant + IConstantesBDD.SQL_COMMA
		        + IConstantesBDD.SQL_CONCAT + IConstantesBDD.SQL_BRACKET_OPEN
		        + IConstantesBDD.CENTRALIEN_NOM + IConstantesBDD.SQL_COMMA
		        + "' '" + IConstantesBDD.SQL_COMMA
		        + IConstantesBDD.CENTRALIEN_PRENOM
		        + IConstantesBDD.SQL_BRACKET_CLOSE + IConstantesBDD.SQL_AS
		        + nomPrenom + IConstantesBDD.SQL_FROM
		        + IConstantesBDD.CENTRALIEN;

		sql += ", AnneePromotion";
		if (entrepriseActif) {
			sql += ", Entreprise, EntrepriseVilleSecteur, EntrepriseVilleSecteurCentralien";
		} else {
			sql += ", Ecole, EcoleSecteur, EcoleSecteurCentralien";
		}
		sql += ", Secteur";
		sql += " WHERE ";
		sql += " anneePromotion_ID = centralien_anneePromotion_ID";
		sql += " AND ";
		if (entrepriseActif) {
			sql += "entreprise_ID = entrepriseVilleSecteur_entreprise_ID AND entrepriseVilleSecteur_ID = entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID AND entrepriseVilleSecteurCentralien_centralien_ID = centralien_ID";
		} else {
			sql += "ecole_ID = ecoleSecteur_ecole_ID AND ecoleSecteur_ID = ecoleSecteurCentralien_ecolesecteur_ID AND ecoleSecteurCentralien_centralien_ID = centralien_ID";
		}
		sql += " AND ";
		if (entrepriseActif) {
			sql += "entrepriseVilleSecteur_secteur_ID = secteur_ID";
		} else {
			sql += "ecoleSecteur_secteur_ID = secteur_ID";
		}

		// On ajoute les filtres
		String conditionsSql = conditionsSelonFiltres(historique,
		        anneePromotion_ID, ecole_ID, entreprise_ID, secteur_ID,
		        ville_ID, parametresPresents);
		if (!conditionsSql.isEmpty()) {
			sql += conditionsSql;
		}

		// Si le filtre pays est actif mais pas le filtre ville
		if (parametresPresents[4] && !parametresPresents[5]) {
			sql += IConstantesBDD.SQL_AND;
			if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
				sql += IConstantesBDD.CENTRALIEN_ID + IConstantesBDD.SQL_IN
				        + IConstantesBDD.SQL_BRACKET_OPEN;
				sql += IConstantesBDD.SQL_SELECT
				        + IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_CENTRALIEN_ID
				        + IConstantesBDD.SQL_FROM
				        + IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN
				        + IConstantesBDD.SQL_COMMA
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR
				        + IConstantesBDD.SQL_WHERE
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR_VILLE_ID
				        + IConstantesBDD.SQL_IN
				        + IConstantesBDD.SQL_BRACKET_OPEN;
				sql += IConstantesBDD.SQL_SELECT + IConstantesBDD.VILLE_ID
				        + IConstantesBDD.SQL_FROM + IConstantesBDD.VILLE
				        + IConstantesBDD.SQL_WHERE
				        + IConstantesBDD.VILLE_PAYS_ID
				        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
				        + PARAM_PAYS;
				sql += IConstantesBDD.SQL_BRACKET_CLOSE;
				sql += IConstantesBDD.SQL_AND;
				sql += IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_ENTREPRISEVILLESECTEUR_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.ENTREPRISEVILLESECTEUR_ID;
				sql += IConstantesBDD.SQL_BRACKET_CLOSE;
			} else {
				sql += IConstantesBDD.CENTRALIEN_ID + IConstantesBDD.SQL_IN
				        + IConstantesBDD.SQL_BRACKET_OPEN;
				sql += IConstantesBDD.SQL_SELECT
				        + IConstantesBDD.ECOLESECTEURCENTRALIEN_CENTRALIEN_ID
				        + IConstantesBDD.SQL_FROM + IConstantesBDD.ECOLESECTEUR
				        + IConstantesBDD.SQL_COMMA
				        + IConstantesBDD.ECOLESECTEURCENTRALIEN
				        + IConstantesBDD.SQL_COMMA + IConstantesBDD.ECOLE
				        + IConstantesBDD.SQL_WHERE
				        + IConstantesBDD.ECOLE_VILLE_ID + IConstantesBDD.SQL_IN
				        + IConstantesBDD.SQL_BRACKET_OPEN;
				sql += IConstantesBDD.SQL_SELECT + IConstantesBDD.VILLE_ID
				        + IConstantesBDD.SQL_FROM + IConstantesBDD.VILLE
				        + IConstantesBDD.SQL_WHERE
				        + IConstantesBDD.VILLE_PAYS_ID
				        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
				        + PARAM_PAYS;
				sql += IConstantesBDD.SQL_BRACKET_CLOSE;
				sql += IConstantesBDD.SQL_AND;
				sql += IConstantesBDD.ECOLESECTEURCENTRALIEN_ECOLESECTEUR_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.ECOLESECTEUR_ID;
				sql += IConstantesBDD.SQL_AND;
				sql += IConstantesBDD.ECOLESECTEUR_ECOLE_ID
				        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.ECOLE_ID;
				sql += IConstantesBDD.SQL_BRACKET_CLOSE;
			}
		}

		sql += IConstantesBDD.SQL_ORDER_BY + nomPrenom + IConstantesBDD.SQL_ASC;

		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		if (parametresPresents[0]) {
			sqlQuery.setParameter(PARAM_ANNEEPROMOTION,
			        Integer.parseInt(anneePromotion_ID));
		}
		if (parametresPresents[1]) {
			sqlQuery.setParameter(PARAM_ECOLE, Integer.parseInt(ecole_ID));
		}
		if (parametresPresents[2]) {
			sqlQuery.setParameter(PARAM_ENTREPRISE,
			        Integer.parseInt(entreprise_ID));
		}
		if (parametresPresents[3]) {
			sqlQuery.setParameter(PARAM_SECTEUR, Integer.parseInt(secteur_ID));
		}
		if (parametresPresents[4] && !parametresPresents[5]) {
			sqlQuery.setParameter(PARAM_PAYS, Integer.parseInt(pays_ID));
		}
		if (parametresPresents[5]) {
			sqlQuery.setParameter(PARAM_VILLE, Integer.parseInt(ville_ID));
		}

		List<SqlRow> listSqlRow = sqlQuery.findList();

		// Liste de double String : le premier est l'ID et le deuxième est le
		// prenomNom
		List<String[]> listeDesCentraliensParCriteres = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String resultat_identifiant = sqlRow.get(identifiant).toString();
			String resultat_nomPrenom = sqlRow.get(nomPrenom).toString();
			listeDesCentraliensParCriteres.add(new String[] {
			        resultat_identifiant, resultat_nomPrenom });
		}

		return ok(Json.toJson(listeDesCentraliensParCriteres));
	}

	/**
	 * Cette methode renvoie au client la liste des coordonnees des centraliens
	 * ayant etudie ou travaille dans une ville donnee avec differentes
	 * informations les concernant
	 */
	public static Result AJAX_listeDesCoordonneesDesCentraliens(
	        boolean historique, String centralien_ID, String anneePromotion_ID,
	        String ecole_ID, String entreprise_ID, String secteur_ID,
	        String ville_ID, Integer limite, Integer offset,
	        boolean nombreLignes, String tri) {

		Boolean[] parametresPresents = new Boolean[] {
		        anneePromotion_ID != null && !anneePromotion_ID.isEmpty(),
		        ecole_ID != null
		                && !ecole_ID.isEmpty()
		                && !ecole_ID
		                        .equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF),
		        entreprise_ID != null
		                && !entreprise_ID.isEmpty()
		                && !entreprise_ID
		                        .equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF),
		        secteur_ID != null && !secteur_ID.isEmpty(),
		        centralien_ID != null && !centralien_ID.isEmpty(),
		        ville_ID != null && !ville_ID.isEmpty() };

		boolean entrepriseActif = ecole_ID
		        .equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF);

		// Les informations renvoyees sont le prenom, le nom, l'annee de
		// Promotion, l'ecole ou l'entreprise, le secteur
		String sql = "SELECT ";

		if (!nombreLignes) {
			sql += IConstantesBDD.SQL_DISTINCT;
			sql += "centralien_prenom, centralien_nom, centralien_telephone, centralien_mail, anneePromotion_libelle, poste_nom";
			if (entrepriseActif) {
				sql += ", entreprise_nom";
			} else {
				sql += ", ecole_nom";
			}
			sql += ", secteur_nom";
		} else {
			// Si le client veut uniquement le nombre total de lignes
			sql += "COUNT(DISTINCT CONCAT(centralien_prenom, centralien_nom, centralien_telephone, centralien_mail, anneePromotion_libelle, poste_nom";
			if (entrepriseActif) {
				sql += ", entreprise_nom";
			} else {
				sql += ", ecole_nom";
			}
			sql += ", secteur_nom)) AS nombreLignes";
		}

		sql += " FROM Centralien, AnneePromotion, Poste";
		if (entrepriseActif) {
			sql += ", Entreprise, EntrepriseVilleSecteur, EntrepriseVilleSecteurCentralien";
		} else {
			sql += ", Ecole, EcoleSecteur, EcoleSecteurCentralien";
		}
		sql += ", Secteur";
		sql += " WHERE ";
		sql += " anneePromotion_ID = centralien_anneePromotion_ID";
		sql += " AND ";
		if (entrepriseActif) {
			sql += "entreprise_ID = entrepriseVilleSecteur_entreprise_ID AND entrepriseVilleSecteur_ID = entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID AND entrepriseVilleSecteurCentralien_centralien_ID = centralien_ID";
		} else {
			sql += "ecole_ID = ecoleSecteur_ecole_ID AND ecoleSecteur_ID = ecoleSecteurCentralien_ecolesecteur_ID AND ecoleSecteurCentralien_centralien_ID = centralien_ID";
		}
		sql += " AND ";
		if (entrepriseActif) {
			sql += "entrepriseVilleSecteur_secteur_ID = secteur_ID";
		} else {
			sql += "ecoleSecteur_secteur_ID = secteur_ID";
		}
		
		System.out.println(sql);

		// On ajoute les filtres
		String conditionsSql = conditionsSelonFiltres(historique,
		        anneePromotion_ID, ecole_ID, entreprise_ID, secteur_ID,
		        ville_ID, parametresPresents);
		if (!conditionsSql.isEmpty()) {
			sql += conditionsSql;
		}

		// Si le filtre centralien est actif
		if (parametresPresents[4]) {
			sql += " AND ";
			sql += "centralien_ID = :centralien_ID";
		}

		// Si le client veut uniquement le nombre total de lignes, il ne faut
		// pas limiter ce nombre ou trier les resultat
		if (!nombreLignes) {

			if (tri != null && !tri.isEmpty()) {
				sql += " ORDER BY ";

				if (tri.equals(IConstantesBDD.TRI_DEFAUT)) {
					sql += "centralien_nom, centralien_prenom";
				} else if (tri.equals(IConstantesBDD.TRI_PRENOM)) {
					sql += "centralien_prenom";
				} else if (tri.equals(IConstantesBDD.TRI_NOM)) {
					sql += "centralien_nom";
				} else if (tri.equals(IConstantesBDD.TRI_TELEPHONE)) {
					sql += "centralien_telephone";
				} else if (tri.equals(IConstantesBDD.TRI_MAIL)) {
					sql += "centralien_mail";
				} else if (tri.equals(IConstantesBDD.TRI_ANNEEPROMOTION)) {
					sql += "anneePromotion_libelle";
				} else if (tri.equals(IConstantesBDD.TRI_POSTE)) {
					sql += "poste_nom";
				} else if (tri.equals(IConstantesBDD.TRI_ECOLE)) {
					sql += "ecole_nom";
				} else if (tri.equals(IConstantesBDD.TRI_ENTREPRISE)) {
					sql += "entreprise_nom";
				} else if (tri.equals(IConstantesBDD.TRI_SECTEUR)) {
					sql += "secteur_nom";
				}
			} else {
				sql += " ORDER BY centralien_nom, centralien_prenom";
			}

			sql += " LIMIT " + limite;
			sql += " OFFSET " + offset;
		}

		System.out.println(sql);
		
		SqlQuery sqlQuery = Ebean.createSqlQuery(sql);
		if (parametresPresents[0]) {
			sqlQuery.setParameter("anneePromotion_ID",
			        Integer.parseInt(anneePromotion_ID));
		}
		if (parametresPresents[1]) {
			sqlQuery.setParameter("ecole_ID", Integer.parseInt(ecole_ID));
		}
		if (parametresPresents[2]) {
			sqlQuery.setParameter("entreprise_ID",
			        Integer.parseInt(entreprise_ID));
		}
		if (parametresPresents[3]) {
			sqlQuery.setParameter("secteur_ID", Integer.parseInt(secteur_ID));
		}
		if (parametresPresents[4]) {
			sqlQuery.setParameter("centralien_ID",
			        Integer.parseInt(centralien_ID));
		}
		// La ville est nécessairement indiquee
		sqlQuery.setParameter("ville_ID", Integer.parseInt(ville_ID));

		if (!nombreLignes) {
			List<SqlRow> listSqlRow = sqlQuery.findList();

			// Liste de double String : le premier est l'ID et le deuxième est
			// le prenomNom
			List<String[]> listeDesCoordonneesDesCentraliens = new ArrayList<String[]>();
			for (SqlRow sqlRow : listSqlRow) {
				String prenom = sqlRow.get("centralien_prenom").toString();
				String nom = sqlRow.get("centralien_nom").toString();
				String telephone;
				if (sqlRow.get("centralien_telephone") != null) {
					telephone = sqlRow.get("centralien_telephone").toString();
				} else {
					telephone = IConstantes.CHAMP_NON_RENSEIGNE;
				}
				String mail;
				if (sqlRow.get("centralien_mail") != null) {
					mail = sqlRow.get("centralien_mail").toString();
				} else {
					mail = IConstantes.CHAMP_NON_RENSEIGNE;
				}
				String anneePromotion = sqlRow.get("anneePromotion_libelle")
				        .toString();
				String poste = sqlRow.get("poste_nom").toString();
				String ecoleOuEntreprise;
				if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
					ecoleOuEntreprise = sqlRow.get("entreprise_nom").toString();
				} else {
					ecoleOuEntreprise = sqlRow.get("ecole_nom").toString();
				}
				String secteur = sqlRow.get("secteur_nom").toString();
				listeDesCoordonneesDesCentraliens.add(new String[] { prenom,
				        nom, telephone, mail, anneePromotion, poste,
				        ecoleOuEntreprise, secteur });
			}

			return ok(Json.toJson(listeDesCoordonneesDesCentraliens));
		} else {
			SqlRow sqlRow = sqlQuery.findUnique();
			return ok(Json.toJson(sqlRow.get("nombreLignes").toString()));
		}

	}

}
