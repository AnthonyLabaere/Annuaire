/**
 * -----------------------------------------------------------------------------
 * Ce fichier contient les fonctions dites de service utilisables par les autres fichiers
 * pour se concentrer plus sur la partie metier
 * -----------------------------------------------------------------------------
 */

/** Cette variable est nécessaire à l'utilisation de OpenLayers */
var ATTRIBUTION = 'Ce site utilise <a href="http://openlayers.org/" target="_blank">OpenLayers</a> pour l\'affichage d\' <a href="http://www.openstreetmap.org/" target="_blank">Openstreet Maps</a>, sous license ODbL';

/**
 * Cette fonction simplifie le code de récupération d'une balise HTML
 * 
 * @param element_id
 *            ID de la balise HTML à récupérer
 * @returns la balise concernée
 */
function HTML(element_id) {
	return document.getElementById(element_id);
}
