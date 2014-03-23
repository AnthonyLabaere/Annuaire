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
 * Ce fichier contient les fonctions portant sur les marqueurs de la carte
 * -----------------------------------------------------------------------------
 */

// TODO : faire en sorte de savoir s'il y a eu des modifications dans les
// villes... bref ne pas tout recharger a chaque fois
/** Le layer des marqueurs */
var MARKER_LAYER;

/** Le controlleur sur le select d'un marqueur */
var SELECT_CONTROL;

/** Types de marqueurs : pays ou ville */
var TYPES_MARQUEUR = new Array();
TYPES_MARQUEUR.push("pays");
TYPES_MARQUEUR.push("ville");

TYPE_MARQUEUR_PAYS_ID = 0;
TYPE_MARQUEUR_VILLE_ID = 1;

/** Initialisation de la partie des marqueurs : layer et controlleur */
function init_marqueur() {
	MARKER_LAYER = new OpenLayers.Layer.Vector("Overlay");

	// On ajoute le layer des marqueurs sur la carte
	CARTE.addLayer(MARKER_LAYER);

	SELECT_CONTROL = new OpenLayers.Control.SelectFeature(MARKER_LAYER, {
		onSelect : clicSurMarqueur
	});

	CARTE.addControl(SELECT_CONTROL);

	SELECT_CONTROL.activate();

	// On cree les marqueurs (un par pays)
	miseAjourDesMarqueurs();
}

/** Cette fonction definit l'action a effectuer lors d'un clic sur un marqueur */
function clicSurMarqueur(feature) {
	var type = feature.attributes.type;
	var nom = feature.attributes.nom;
	var id = feature.attributes.id;
	if (type == TYPES_MARQUEUR[TYPE_MARQUEUR_PAYS_ID]) {
		// Si le marqueur est de type pays alors on remplace le filtre pays par
		// le pays du marqueur et on met a jour les filtres (et donc les
		// marqueurs)
		HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]).value = id;
		action_modificationFiltre(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]);
	} else {
		// Si le marqueur est de type ville alors on affiche une modale avec les
		// informations sur les centraliens correspondants aux filtres
		// selectionnes
		alimenterModale(id, NOMBRE_LIGNES, 1);
		afficherModale();
		SELECT_CONTROL.unselect(feature);
	}
}

/**
 * Fonction d'ajout d'un marqueur qui prend en parametre le type du marqueur
 * (pays ou ville) et l'option HTML correspondante au marqueur a inserer
 */
function ajout_marqueur(type, option) {
	var coordonnees = new OpenLayers.LonLat(option.getAttribute("longitude"),
			option.getAttribute("latitude"));

	var position = getPositionCarte(coordonnees);
	var point = new OpenLayers.Geometry.Point(position.lon, position.lat);

	// TODO Mettre les drapeaux du monde au lieu de balises classiques
	var feature = new OpenLayers.Feature.Vector(point, {
		type : type,
		nom : option.text,
		id : option.value
	}, {
		externalGraphic : '/assets/images/map/marqueur.png',
		graphicHeight : 21,
		graphicWidth : 16
	});

	MARKER_LAYER.addFeatures(feature);
}

/**
 * Cette fonction met a jour les marqueurs en tenant compte des differents
 * filtres
 */
function miseAjourDesMarqueurs() {
	// Les marqueurs precedemment places sont supprimes
	if (MARKER_LAYER) {
		MARKER_LAYER.destroyFeatures();
	}

	var filtre_pays = HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]);
	var filtre_ville = HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]);

	if (filtre_pays.selectedIndex == 0) {
		// Si le filtre pays n'est pas renseigne alors l'application affiche un
		// marqueur par Pays concerne par la recherche actuelle
		var nombre_pays = filtre_pays.options.length;
		for ( var i = 1; i < nombre_pays; i++) {
			ajout_marqueur(TYPES_MARQUEUR[TYPE_MARQUEUR_PAYS_ID],
					filtre_pays.options[i]);
		}

	} else {
		if (filtre_ville.selectedIndex == 0) {
			// Si le filtre pays est renseigne mais pas le filtre ville
			// l'application affiche les villes du pays apres avoir effectue un
			// zoom sur le pays
			zoom(filtre_pays.options[filtre_pays.selectedIndex]);

			var nombre_ville = filtre_ville.options.length;
			for ( var i = 1; i < nombre_ville; i++) {
				ajout_marqueur(TYPES_MARQUEUR[TYPE_MARQUEUR_VILLE_ID],
						filtre_ville.options[i]);
			}

		} else {
			// Si le filtre ville est renseigne l'application affiche un
			// marqueur sur la ville apres un zoom sur cette derniere
			var filtreville_ID = filtre_ville.selectedIndex;
			ajout_marqueur(TYPES_MARQUEUR[TYPE_MARQUEUR_VILLE_ID],
					filtre_ville.options[filtreville_ID]);

		}
	}
}
