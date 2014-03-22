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
	        String anneePromotion_ID, String ecole_ID, String entreprise_ID,
	        String secteur_ID, String pays_ID, String ville_ID) {
		String identifiant = "identifiant";
		String nomPrenom = "nomPrenom";
		String param_anneePromotion = "anneePromotion_ID";
		String param_ecole = "ecole_ID";
		String param_entreprise = "entreprise_ID";
		String param_secteur = "secteur_ID";
		String param_pays = "pays_ID";
		String param_ville = "ville_ID";

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

		Boolean wherePlace = false;

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

		// Si le filtre anneePromotion est actif
		if (parametresPresents[0]) {
			wherePlace = true;
			sql += IConstantesBDD.SQL_WHERE
			        + IConstantesBDD.CENTRALIEN_ANNEEPROMOTION_ID
			        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.SQL_COLON
			        + param_anneePromotion;
		}

		// Si le filtre ecole est actif
		if (parametresPresents[1]) {
			if (wherePlace) {
				sql += IConstantesBDD.SQL_AND;
			} else {
				sql += IConstantesBDD.SQL_WHERE;
				wherePlace = true;
			}
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
			        + param_ecole;
			sql += IConstantesBDD.SQL_AND;
			sql += IConstantesBDD.ECOLESECTEURCENTRALIEN_ECOLESECTEUR_ID
			        + IConstantesBDD.SQL_EQUAL + IConstantesBDD.ECOLESECTEUR_ID;
			sql += IConstantesBDD.SQL_BRACKET_CLOSE;
		}

		// Si le filtre entreprise est actif
		if (parametresPresents[2]) {
			if (wherePlace) {
				sql += IConstantesBDD.SQL_AND;
			} else {
				sql += IConstantesBDD.SQL_WHERE;
				wherePlace = true;
			}

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
			        + param_entreprise;
			sql += IConstantesBDD.SQL_AND;
			sql += IConstantesBDD.ENTREPRISEVILLESECTEURCENTRALIEN_ENTREPRISEVILLESECTEUR_ID
			        + IConstantesBDD.SQL_EQUAL
			        + IConstantesBDD.ENTREPRISEVILLESECTEUR_ID;
			sql += IConstantesBDD.SQL_BRACKET_CLOSE;
		}

		// Si le filtre secteur est actif
		if (parametresPresents[3]) {
			if (wherePlace) {
				sql += IConstantesBDD.SQL_AND;
			} else {
				sql += IConstantesBDD.SQL_WHERE;
				wherePlace = true;
			}
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
				        + param_secteur;
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
				        + param_secteur;
				sql += IConstantesBDD.SQL_AND;
				sql += IConstantesBDD.ECOLESECTEURCENTRALIEN_ECOLESECTEUR_ID
				        + IConstantesBDD.SQL_EQUAL
				        + IConstantesBDD.ECOLESECTEUR_ID;
				sql += IConstantesBDD.SQL_BRACKET_CLOSE;
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
				        + param_pays;
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
				        + param_pays;
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

		// Si le filtre ville est actif
		if (parametresPresents[5]) {
			if (wherePlace) {
				sql += IConstantesBDD.SQL_AND;
			} else {
				sql += IConstantesBDD.SQL_WHERE;
				wherePlace = true;
			}
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
				        + param_ville;
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
				        + param_ville;
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
			sqlQuery.setParameter(param_anneePromotion,
			        Integer.parseInt(anneePromotion_ID));
		}
		if (parametresPresents[1]) {
			sqlQuery.setParameter(param_ecole, Integer.parseInt(ecole_ID));
		}
		if (parametresPresents[2]) {
			sqlQuery.setParameter(param_entreprise,
			        Integer.parseInt(entreprise_ID));
		}
		if (parametresPresents[3]) {
			sqlQuery.setParameter(param_secteur, Integer.parseInt(secteur_ID));
		}
		if (parametresPresents[4] && !parametresPresents[5]) {
			sqlQuery.setParameter(param_pays, Integer.parseInt(pays_ID));
		}
		if (parametresPresents[5]) {
			sqlQuery.setParameter(param_ville, Integer.parseInt(ville_ID));
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
	        String centralien_ID, String anneePromotion_ID, String ecole_ID,
	        String entreprise_ID, String secteur_ID, String ville_ID, String limite, String offset, String tri) {
		
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
		        secteur_ID != null && !secteur_ID.isEmpty(),
		        ville_ID != null && !ville_ID.isEmpty() };

		// Les informations renvoyees sont le prenom, le nom, l'annee de
		// Promotion, l'ecole ou l'entreprise, le secteur
		String sql = "SELECT centralien_prenom, centralien_nom, anneePromotion_libelle";
		if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
			sql += ", entreprise_nom";
		} else {
			sql += ", ecole_nom";
		}
		sql += ", secteur_nom";
		sql += " FROM Centralien, AnneePromotion";
		if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
			sql += ", Entreprise, EntrepriseVilleSecteur, EntrepriseVilleSecteurCentralien";
		} else {
			sql += ", Ecole, EcoleSecteur, EcoleSecteurCentralien";
		}
		sql += ", Secteur";
		sql += " WHERE ";
		sql += " anneePromotion_ID = centralien_anneePromotion_ID";
		sql += " AND ";
		if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
			sql += "entreprise_ID = entrepriseVilleSecteur_entreprise_ID AND entrepriseVilleSecteur_ID = entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID AND entrepriseVilleSecteurCentralien_centralien_ID = centralien_ID";
		} else {
			sql += "ecole_ID = ecoleSecteur_ecole_ID AND ecoleSecteur_ID = ecoleSecteurCentralien_ecolesecteur_ID AND ecoleSecteurCentralien_centralien_ID = centralien_ID";
		}
		sql += " AND ";
		if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
			sql += "entrepriseVilleSecteur_secteur_ID = secteur_ID";
		} else {
			sql += "ecoleSecteur_secteur_ID = secteur_ID";
		}

		if (parametresPresents[0]) {
			sql += " AND ";
			sql += "centralien_ID = :centralien_ID";
		}

		if (parametresPresents[1]) {
			sql += " AND ";
			sql += "centralien_anneePromotion_ID = :anneePromotion_ID";
		}

		if (parametresPresents[2]) {
			sql += " AND ";
			sql += "centralien_ID IN (";
			sql += "SELECT ecoleSecteurCentralien_Centralien_ID FROM EcoleSecteurCentralien, EcoleSecteur WHERE ecoleSecteur_ecole_ID = :ecole_ID";
			sql += " AND ";
			sql += "ecoleSecteurCentralien_ecoleSecteur_ID = ecoleSecteur_ID ";
			sql += ")";
		}

		if (parametresPresents[3]) {
			sql += " AND ";
			sql += "centralien_ID IN (";
			sql += "SELECT entrepriseVilleSecteurCentralien_Centralien_ID FROM EntrepriseVilleSecteurCentralien, EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_entreprise_ID = :entreprise_ID";
			sql += " AND ";
			sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID ";
			sql += ")";
		}

		if (parametresPresents[4]) {
			sql += " AND ";
			if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
				sql += "centralien_ID IN (";
				sql += "SELECT entrepriseVilleSecteurCentralien_Centralien_ID FROM EntrepriseVilleSecteurCentralien, EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_secteur_ID = :secteur_ID";
				sql += " AND ";
				sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID ";
				sql += ")";
			} else {
				sql += "centralien_ID IN (";
				sql += "SELECT ecoleSecteurCentralien_Centralien_ID FROM EcoleSecteurCentralien, EcoleSecteur WHERE ecoleSecteur_secteur_ID = :secteur_ID";
				sql += " AND ";
				sql += "ecoleSecteurCentralien_ecoleSecteur_ID = ecoleSecteur_ID ";
				sql += ")";
			}
		}

		// La ville est nécessairement indiquee
		sql += " AND ";
		if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
			sql += "centralien_ID IN (";
			sql += "SELECT entrepriseVilleSecteurCentralien_Centralien_ID FROM EntrepriseVilleSecteurCentralien, EntrepriseVilleSecteur WHERE entrepriseVilleSecteur_ville_ID = :ville_ID";
			sql += " AND ";
			sql += "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID = entrepriseVilleSecteur_ID ";
			sql += ")";
		} else {
			sql += "centralien_ID IN (";
			sql += "SELECT ecoleSecteurCentralien_Centralien_ID FROM EcoleSecteurCentralien, Ecole, EcoleSecteur WHERE ecole_ville_ID = :ville_ID";
			sql += " AND ";
			sql += "ecoleSecteurCentralien_ecoleSecteur_ID = ecoleSecteur_ID ";
			sql += " AND ";
			sql += "ecoleSecteur_ecole_ID = ecole_ID ";
			sql += ")";
		}

		if (tri != null && !tri.isEmpty()){
			sql += " ORDER BY ";
			
			if (tri.equals(IConstantesBDD.TRI_DEFAUT)){
				sql += "centralien_nom, centralien_prenom";
			} else if (tri.equals(IConstantesBDD.TRI_PRENOM)) {
				sql += "centralien_prenom";				
			} else if (tri.equals(IConstantesBDD.TRI_NOM)) {
				sql += "centralien_nom";				
			} else if (tri.equals(IConstantesBDD.TRI_ANNEEPROMOTION)) {
				sql += "anneePromotion_libelle";				
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
		
		// On met les parametres en integer pour des raisons de securite
		sql += " LIMIT " + Integer.parseInt(limite);
		sql += " OFFSET " + Integer.parseInt(offset);

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
		// La ville est nécessairement indiquee
		sqlQuery.setParameter("ville_ID", Integer.parseInt(ville_ID));

		List<SqlRow> listSqlRow = sqlQuery.findList();

		// Liste de double String : le premier est l'ID et le deuxième est le
		// prenomNom
		List<String[]> listeDesCoordonneesDesCentraliens = new ArrayList<String[]>();
		for (SqlRow sqlRow : listSqlRow) {
			String prenom = sqlRow.get("centralien_prenom").toString();
			String nom = sqlRow.get("centralien_nom").toString();
			String anneePromotion = sqlRow.get("anneePromotion_libelle")
			        .toString();
			String ecoleOuEntreprise;
			if (ecole_ID.equals(IConstantes.ECOLE_OU_ENTREPRISE_INACTIF)) {
				ecoleOuEntreprise = sqlRow.get("entreprise_nom").toString();
			} else {
				ecoleOuEntreprise = sqlRow.get("ecole_nom").toString();
			}
			String secteur = sqlRow.get("secteur_nom").toString();
			listeDesCoordonneesDesCentraliens.add(new String[] { prenom, nom,
			        anneePromotion, ecoleOuEntreprise, secteur });
		}

		return ok(Json.toJson(listeDesCoordonneesDesCentraliens));
	}

}
