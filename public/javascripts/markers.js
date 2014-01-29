//A obtenir via la base de données
var POSITIONS_CAPITALES = new Array();

var POSITION_PARIS = new OpenLayers.LonLat(2.3522219, 48.8566140);
var POSITION_MADRID = new OpenLayers.LonLat(-3.7037902, 40.4167754);
var POSITION_LONDRES = new OpenLayers.LonLat(-0.1198244, 51.5112139);
var POSITION_DUBLIN = new OpenLayers.LonLat(-6.2603097, 53.3498053);
var POSITION_BRUXELLE = new OpenLayers.LonLat(4.3500000, 50.8500000);
var POSITION_BERLIN = new OpenLayers.LonLat(13.4049540, 52.5200066);
	
POSITIONS_CAPITALES.push(POSITION_PARIS);	
POSITIONS_CAPITALES.push(POSITION_MADRID);	
POSITIONS_CAPITALES.push(POSITION_LONDRES);	
POSITIONS_CAPITALES.push(POSITION_DUBLIN);	
POSITIONS_CAPITALES.push(POSITION_BRUXELLE);	
POSITIONS_CAPITALES.push(POSITION_BERLIN);	


/**
 * Fonction d'ajout d'un marqueur
 * @param coordonnees position du marker
 */
function ajout_marqueur(coordonnees){	
	var vectorLayer = new OpenLayers.Layer.Vector("Overlay");
	
	var position=getPositionCarte(coordonnees);	
    var point = new OpenLayers.Geometry.Point(position.lon,position.lat);
	
    // TODO Mettre les drapeaux du monde au lieu de balises classiques
	var feature = new OpenLayers.Feature.Vector(
	  point,
	 {some:'data'},
	 {externalGraphic: '/assets/images/map/marqueur.png', graphicHeight: 21, graphicWidth: 16});
	vectorLayer.addFeatures(feature);
	CARTE.addLayer(vectorLayer);
	
    // A popup with some information about our location
    var popup = new OpenLayers.Popup.FramedCloud("Popup", 
    		point.getBounds().getCenterLonLat(), null,
        '50 Centraliens', null, null
    );
    // and add the popup to it.
    CARTE.addPopup(popup);
}

for (var index_position in POSITIONS_CAPITALES) {
	ajout_marqueur(POSITIONS_CAPITALES[index_position]);
}
