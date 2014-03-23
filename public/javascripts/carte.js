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
 * Ce fichier contient les fonctions portant sur la carte
 * -----------------------------------------------------------------------------
 */

/**
 * Initialisation de la carte OpenLayers
 * 
 * @param div_nom
 *            ID de la balise div ou est affiche la carte
 */
function map_init(div_nom) {
	OpenLayers.ImgPath = "/assets/javascripts/OpenLayers/theme/default/img/";

	/*---------------- Initialisation de la carte -------------------*/

	// Create a map Object
	var carte_tmp = new OpenLayers.Map(div_nom);
	CARTE = carte_tmp;
	window.map = carte_tmp;

	window.map.addLayer(new OpenLayers.Layer.OSM());
	window.map.setCenter(getPositionCarte(POSITION_INITIALE), ZOOM_INITIAL);
	// window.map.zoomToMaxExtent();
}

/**
 * Cette fonction retournes la position dans un format utilisable par OpenLayers
 * 
 * @param coordonnees
 *            coordonnees dans le système longitude latitude
 * @returns Une position au format OpenLayers
 */
function getPositionCarte(coordonnees) {
	var format_depart = new OpenLayers.Projection("EPSG:4326");
	var format_arrive = new OpenLayers.Projection("EPSG:900913");
	var position = coordonnees.transform(format_depart, format_arrive);

	return position;
}

/**
 * Fonction qui fait glisser le panneau (le montre ou le cache)
 */
function glisserPanneau() {
	var boutonPanneau = HTML('bouton_fermeture_panneau');
	if (boutonPanneau.className != "ferme") {
		HTML('panneau').className = 'ferme';
		boutonPanneau.className = 'ferme';
	} else {
		HTML('panneau').className = 'ouvert';
		boutonPanneau.className = 'ouvert';
	}
}

/**
 * Zoom sur la position et avec le niveau de zoom contenu dans la balise option
 * (pays ou ville) donnee en parametre
 */
function zoom(option) {

	var position = new OpenLayers.LonLat(option.getAttribute("longitude"),
			option.getAttribute("latitude"));
	var niveauDeZoom = option.getAttribute("zoom");

	window.map.setCenter(getPositionCarte(position), niveauDeZoom);
}

/**
 * Teste de la fonction precedente pour connaitre le niveau de zoom a mettre en
 * base de donnee selon chaque pays
 */

function testZoom(option) {
	var position = new OpenLayers.LonLat(option.getAttribute("longitude"),
			option.getAttribute("latitude"));
	var niveauDeZoom = 6;

	window.map.setCenter(getPositionCarte(position), niveauDeZoom);
}
