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

package constantes;

/**
 * Recense les termes SQL utilisees dans les requetes et les noms des tables et colonnes de la base de donnees de
 * l'application
 * 
 * @author Anthony
 * 
 */
public interface IConstantesBDD {
	
	/** Principaux termes sql */
	public static final String SQL_SELECT = "SELECT ";
	public static final String SQL_UPDATE = "UPDATE "; 
	public static final String SQL_SET = "SET ";
	public static final String SQL_WHERE = " WHERE ";	
	public static final String SQL_FROM = " FROM ";	
	public static final String SQL_AND = " AND ";	
	public static final String SQL_OR = " OR ";	
	public static final String SQL_ORDER_BY = " ORDER BY ";	
	public static final String SQL_ASC = " ASC";	
	public static final String SQL_DESC = " DESC";	
	public static final String SQL_AS = " AS ";	
	public static final String SQL_EQUAL = " = ";	
	public static final String SQL_IN = " IN ";	
	public static final String SQL_BRACKET_OPEN = "(";	
	public static final String SQL_BRACKET_CLOSE = ")";	
	public static final String SQL_COMMA = ", ";	
	public static final String SQL_COLON = ":";	
	public static final String SQL_IS = " IS ";	
	public static final String SQL_NULL = " NULL ";
	public static final String SQL_DISTINCT = "DISTINCT ";
	
	/** Principales fonctions sql */
	public static final String SQL_CONCAT = "CONCAT";
	
	/** Table AnneePromotion */
	public static final String ANNEEPROMOTION = "AnneePromotion";	
	public static final String ANNEEPROMOTION_ID = "anneePromotion_ID";
	public static final String ANNEEPROMOTION_LIBELLE = "anneePromotion_libelle";
	
	/** Table Centralien */
	public static final String CENTRALIEN = "Centralien";	
	public static final String CENTRALIEN_ID = "centralien_ID";	
	public static final String CENTRALIEN_NOM = "centralien_nom";	
	public static final String CENTRALIEN_PRENOM = "centralien_prenom";	
	public static final String CENTRALIEN_TELEPHONE = "centralien_telephone";	
	public static final String CENTRALIEN_MAIL = "centralien_mail";	
	public static final String CENTRALIEN_ANNEEPROMOTION_ID = "centralien_anneePromotion_ID";	

	/** Table Pays */
	public static final String PAYS = "Pays";	
	public static final String PAYS_ID = "pays_ID";	
	public static final String PAYS_NOM = "pays_nom";	
	public static final String PAYS_NOMMAJUSCULE = "pays_nomMajuscule";	
	public static final String PAYS_NOMMINUSCULE = "pays_nomMinuscule";		
	public static final String PAYS_CODE = "pays_code";	
	public static final String PAYS_LATITUDE = "pays_latitude";	
	public static final String PAYS_LONGITUDE = "pays_longitude";	
	public static final String PAYS_ZOOM = "pays_zoom";

	/** Table Ville */
	public static final String VILLE = "Ville";	
	public static final String VILLE_ID = "ville_ID";	
	public static final String VILLE_NOM = "ville_nom";	
	public static final String VILLE_PAYS_ID = "ville_pays_ID";	
	public static final String VILLE_LATITUDE = "ville_latitude";	
	public static final String VILLE_LONGITUDE = "ville_longitude";	

	/** Table Entreprise */
	public static final String ENTREPRISE = "Entreprise";	
	public static final String ENTREPRISE_ID = "entreprise_ID";	
	public static final String ENTREPRISE_NOM = "entreprise_nom";	

	/** Table Secteur */
	public static final String SECTEUR = "Secteur";	
	public static final String SECTEUR_ID = "secteur_ID";	
	public static final String SECTEUR_NOM = "secteur_nom";	

	/** Table EntrepriseVilleSecteur */
	public static final String ENTREPRISEVILLESECTEUR = "EntrepriseVilleSecteur";	
	public static final String ENTREPRISEVILLESECTEUR_ID = "entrepriseVilleSecteur_ID";	
	public static final String ENTREPRISEVILLESECTEUR_ENTREPRISE_ID = "entrepriseVilleSecteur_entreprise_ID";	
	public static final String ENTREPRISEVILLESECTEUR_VILLE_ID = "entrepriseVilleSecteur_ville_ID";	
	public static final String ENTREPRISEVILLESECTEUR_SECTEUR_ID = "entrepriseVilleSecteur_secteur_ID";	

	/** Table EntrepriseVilleSecteurCentralien */
	public static final String ENTREPRISEVILLESECTEURCENTRALIEN = "EntrepriseVilleSecteurCentralien";	
	public static final String ENTREPRISEVILLESECTEURCENTRALIEN_ID = "entrepriseVilleSecteurCentralien_ID";	
	public static final String ENTREPRISEVILLESECTEURCENTRALIEN_CENTRALIEN_ID = "entrepriseVilleSecteurCentralien_centralien_ID";	
	public static final String ENTREPRISEVILLESECTEURCENTRALIEN_ENTREPRISEVILLESECTEUR_ID = "entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID";

	/** Table Ecole */
	public static final String ECOLE = "Ecole";	
	public static final String ECOLE_ID = "ecole_ID";	
	public static final String ECOLE_NOM = "ecole_nom";	
	public static final String ECOLE_VILLE_ID = "ecole_ville_ID";	

	/** Table EcoleSecteur */
	public static final String ECOLESECTEUR = "EcoleSecteur";	
	public static final String ECOLESECTEUR_ID = "ecoleSecteur_ID";	
	public static final String ECOLESECTEUR_ECOLE_ID = "ecoleSecteur_ecole_ID";	
	public static final String ECOLESECTEUR_SECTEUR_ID = "ecoleSecteur_secteur_ID";	

	/** Table EcoleSecteurCentralien */
	public static final String ECOLESECTEURCENTRALIEN = "EcoleSecteurCentralien";	
	public static final String ECOLESECTEURCENTRALIEN_ID = "ecoleSecteurCentralien_ID";	
	public static final String ECOLESECTEURCENTRALIEN_ECOLESECTEUR_ID = "ecoleSecteurCentralien_ecoleSecteur_ID";	
	public static final String ECOLESECTEURCENTRALIEN_CENTRALIEN_ID = "ecoleSecteurCentralien_centralien_ID";
	
	/** Tri des colonnes */
	public static final String TRI_DEFAUT = "defaut";
	public static final String TRI_PRENOM = "prenom";
	public static final String TRI_NOM = "nom";
	public static final String TRI_TELEPHONE = "telephone";
	public static final String TRI_MAIL = "mail";
	public static final String TRI_ANNEEPROMOTION = "anneePromotion";
	public static final String TRI_POSTE = "poste";
	public static final String TRI_ECOLE = "ecole";
	public static final String TRI_ENTREPRISE = "entreprise";
	public static final String TRI_SECTEUR = "secteur";
}
