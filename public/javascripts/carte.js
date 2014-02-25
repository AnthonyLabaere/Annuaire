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
function zoom(optionID) {

	var option = HTML(optionID);

	var position = new OpenLayers.LonLat(option.getAttribute("longitude"),
			option.getAttribute("latitude"));
	// var niveauDeZoom = option.getAttribute("zoom");

	window.map.setCenter(position, niveauDeZoom);
}

/**
 * Teste de la fonction precedente pour connaitre le niveau de zoom a mettre en
 * base de donnee selon chaque pays
 */
function testZoom(optionID) {

	console.log(optionID);
	var option = HTML(optionID);
	console.log(option);

	var position = new OpenLayers.LonLat(option.getAttribute("longitude"),
			option.getAttribute("latitude"));
	var niveauDeZoom = 6;

	window.map.setCenter(position, niveauDeZoom);
}
