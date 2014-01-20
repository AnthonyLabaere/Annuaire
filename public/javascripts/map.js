var ZOOM_INITIAL = 6;
var POSITION_NANTES = new OpenLayers.LonLat(-1.583,47.233);

/**
 * Initialisation de la carte OpenLayers
 * @param div_nom ID de la balise div ou est affiche la carte
 */
function map_init(div_nom){	
	OpenLayers.ImgPath = "/assets/javascripts/OpenLayers/theme/default/img/";
	 
	/*---------------- Initialisation de la carte -------------------*/
	
	window.map = new OpenLayers.Map(div_nom);      
	window.map.addLayer(new OpenLayers.Layer.OSM());
    window.map.setCenter(getPositionCarte(POSITION_NANTES),ZOOM_INITIAL);
	//window.map.zoomToMaxExtent();
}


/**
 * Cette fonction retournes la position dans un format utilisable par OpenLayers
 * @param coordonnees coordonnees dans le système longitude latitude
 * @returns Une position au format OpenLayers
 */
function getPositionCarte(coordonnees) {
	var format_depart = new OpenLayers.Projection("EPSG:4326");
    var format_arrive = new OpenLayers.Projection("EPSG:900913");
    var position = coordonnees.transform(format_depart,format_arrive);
    
    return position;
}

/**
 * Fonction qui fait glisser le panneau (le montre ou le cache)
 */
function glisserPanneau(){
	var boutonPanneau = HTML('bouton_fermeture_panneau');
	if(boutonPanneau.className != "ferme"){
		HTML('panneau').className='ferme';
		boutonPanneau.className='ferme';
	}
	else {
		HTML('panneau').className='ouvert';
		boutonPanneau.className='ouvert';
	}
}

// On initialise la carte
map_init('carte');
