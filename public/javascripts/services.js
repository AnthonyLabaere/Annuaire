/** Cette variable est nécessaire à l'utilisation de OpenLayers */
var ATTRIBUTION = 'This app is using <a href="http://openlayers.org/" target="_blank">OpenLayers</a> for display of <a href="http://www.openstreetmap.org/" target="_blank">Openstreet Maps</a>, under data license attribution ODbL';

/**
 * Cette fonction simplifie le code de récupération d'une balise HTML
 * @param element_id ID de la balise HTML à récupérer
 * @returns la balise concernée
 */
function HTML(element_id){
	return document.getElementById(element_id);
}
