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
