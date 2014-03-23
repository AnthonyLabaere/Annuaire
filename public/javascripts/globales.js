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

/**
 * -----------------------------------------------------------------------------
 * Ce fichier contient TOUTES les constantes et variables globales de la partie client
 * -----------------------------------------------------------------------------
 */

/**
 * -----------------------------------------------------------------------------
 * Celles portant sur le fonctionnement des filtres
 * -----------------------------------------------------------------------------
 */

/**
 * Ces tableaux de constantes permettent de retenir des informations sur chacun
 * des filtres
 */
var ARRAY_FILTRE_CENTRALIEN = new Array();
var ARRAY_FILTRE_ANNEEPROMOTION = new Array();
var ARRAY_FILTRE_ECOLE = new Array();
var ARRAY_FILTRE_ENTREPRISE = new Array();
var ARRAY_FILTRE_SECTEUR = new Array();
var ARRAY_FILTRE_PAYS = new Array();
var ARRAY_FILTRE_VILLE = new Array();

/** Ces constantes sont les ID des differents filtres */
ARRAY_FILTRE_CENTRALIEN.push('filtre_centralien');
ARRAY_FILTRE_ANNEEPROMOTION.push('filtre_anneePromotion');
ARRAY_FILTRE_ECOLE.push('filtre_ecole');
ARRAY_FILTRE_ENTREPRISE.push('filtre_entreprise');
ARRAY_FILTRE_SECTEUR.push('filtre_secteur');
ARRAY_FILTRE_PAYS.push('filtre_pays');
ARRAY_FILTRE_VILLE.push('filtre_ville');

/** Ces constantes sont les valeurs par defauts des differents filtres */
ARRAY_FILTRE_CENTRALIEN
		.push('S&eacute;lectionnez le Centralien recherch&eacute;');
ARRAY_FILTRE_ANNEEPROMOTION
		.push('S&eacute;lectionnez l\'Ann&eacute;e de promotion recherch&eacute;e');
ARRAY_FILTRE_ECOLE
		.push('S&eacute;lectionnez l\'&Eacute;cole recherch&eacute;e');
ARRAY_FILTRE_ENTREPRISE
		.push('S&eacute;lectionnez l\'Entreprise recherch&eacute;e');
ARRAY_FILTRE_SECTEUR.push('S&eacute;lectionnez le Secteur recherch&eacute;');
ARRAY_FILTRE_PAYS.push('S&eacute;lectionnez le Pays recherch&eacute;');
ARRAY_FILTRE_VILLE.push('S&eacute;lectionnez la Ville recherch&eacute;e');

/**
 * Ces constantes sont utilisees pour connaitre l'ordre dans lequel les filtres
 * ont ete actives et donc lesquels il faut recalculer lorsque l'un d'entre eux
 * est reinitialise
 */
var ORDRE_ACTIVATION_DERNIERE_VALEUR = 1;
var ORDRE_ACTIVATION_PAR_DEFAUT = -1;
ARRAY_FILTRE_CENTRALIEN.push(ORDRE_ACTIVATION_PAR_DEFAUT);
ARRAY_FILTRE_ANNEEPROMOTION.push(ORDRE_ACTIVATION_PAR_DEFAUT);
ARRAY_FILTRE_ECOLE.push(ORDRE_ACTIVATION_PAR_DEFAUT);
ARRAY_FILTRE_ENTREPRISE.push(ORDRE_ACTIVATION_PAR_DEFAUT);
ARRAY_FILTRE_SECTEUR.push(ORDRE_ACTIVATION_PAR_DEFAUT);
ARRAY_FILTRE_PAYS.push(ORDRE_ACTIVATION_PAR_DEFAUT);
ARRAY_FILTRE_VILLE.push(ORDRE_ACTIVATION_PAR_DEFAUT);

/**
 * Ces constantes indiquent simplement l'indice auquel chercher dans le tableau
 * pour trouver telle information
 */
var ARRAY_FILTRE_ID = 0;
var ARRAY_FILTRE_OPTION_PAR_DEFAUT = 1;
var ARRAY_FILTRE_ORDRE_ACTIVATION = 2;

/**
 * Tableau contenant les tableaux (utiles pour certains cas de faire une boucle
 * for dessus)
 */
var ARRAY_FILTRES = new Array();
ARRAY_FILTRES.push(ARRAY_FILTRE_CENTRALIEN);
ARRAY_FILTRES.push(ARRAY_FILTRE_ANNEEPROMOTION);
ARRAY_FILTRES.push(ARRAY_FILTRE_ECOLE);
ARRAY_FILTRES.push(ARRAY_FILTRE_ENTREPRISE);
ARRAY_FILTRES.push(ARRAY_FILTRE_SECTEUR);
ARRAY_FILTRES.push(ARRAY_FILTRE_PAYS);
ARRAY_FILTRES.push(ARRAY_FILTRE_VILLE);

/**
 * Et les constantes associees pour trouver le tableau d'un filtre dans le
 * tableau de filtres
 */
var ARRAY_FILTRES_CENTRALIEN = 0;
var ARRAY_FILTRES_ANNEEPROMOTION = 1;
var ARRAY_FILTRES_ECOLE = 2;
var ARRAY_FILTRES_ENTREPRISE = 3;
var ARRAY_FILTRES_SECTEUR = 4;
var ARRAY_FILTRES_PAYS = 5;
var ARRAY_FILTRES_VILLE = 6;

/**
 * Cette constante (presente aussi dans la partie serveur) permet de connaitre
 * quelle filtre est inactif d'Ecole ou d'Entreprise
 */
var ECOLE_OU_ENTREPRISE_INACTIF = "ECOLE_OU_ENTREPRISE_INACTIF";

/** ID de la checkbox historique */
var CHECKBOX_HISTORIQUE_ID = "historique";

/**
 * -----------------------------------------------------------------------------
 * Celles portant sur le fonctionnement de la carte
 * -----------------------------------------------------------------------------
 */

/**
 * Variable globale contenant la carte, la "map" d'openlayers
 */
var CARTE;

/** Id de la balise div qui contient la carte openlayers */
var DIV_CARTE_ID = "carte";

/** Position initiale centree sur Nantes */
var POSITION_INITIALE = new OpenLayers.LonLat(-1.583, 47.233);

/** Zoom initial */
var ZOOM_INITIAL = 6;

/**
 * -----------------------------------------------------------------------------
 * Celles portant sur le fonctionnement des filtres
 * -----------------------------------------------------------------------------
 */

/** Nombre de lignes par défaut */
var NOMBRE_LIGNES = 2;

/**
 * -----------------------------------------------------------------------------
 * Celles dites de "service"
 * -----------------------------------------------------------------------------
 */

/** Constante chaine de caractere vide */
var VIDE = "";
