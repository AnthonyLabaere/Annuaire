/** Le layer des marqueurs de pays */
var MARKER_LAYER = new Array();

// TODO : faire en sorte de savoir s'il y a eu des modifications dans les
// villes... bref ne pas tout recharger a chaque fois
// var NOMBRE_PAYS;
// var NOMBRE_VILLE;

/**
 * Fonction d'ajout d'un marqueur
 * 
 * @param coordonnees
 *            position du marker
 */
function ajout_marqueur(nom, coordonnees) {
	MARKER_LAYER.push(new OpenLayers.Layer.Vector("Overlay"));

	var position = getPositionCarte(coordonnees);
	var point = new OpenLayers.Geometry.Point(position.lon, position.lat);

	// TODO Mettre les drapeaux du monde au lieu de balises classiques
	var feature = new OpenLayers.Feature.Vector(point, {
		some : 'data'
	}, {
		externalGraphic : '/assets/images/map/marqueur.png',
		graphicHeight : 21,
		graphicWidth : 16
	});

	MARKER_LAYER[MARKER_LAYER.length - 1].addFeatures(feature);
	CARTE.addLayer(MARKER_LAYER[MARKER_LAYER.length - 1]);

	// // Une popup avec quelques informations sur la location
	// var popup = new OpenLayers.Popup.FramedCloud("Popup", point.getBounds()
	// .getCenterLonLat(), null, '50 Centraliens', null, null);
	// // Ajout de cette popup sur la carte
	// CARTE.addPopup(popup);
}

function miseAjourDesMarqueurs() {
	// Les marqueurs precedemment places sont supprimes
	if (MARKER_LAYER[0]) {
		for (indice in MARKER_LAYER) {
			MARKER_LAYER[indice].destroyFeatures();
		}
	}

	var filtre_pays = HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]);
	var filtre_ville = HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]);

	if (filtre_pays.selectedIndex == 0) {
		// Si le filtre pays n'est pas renseigne alors l'application affiche un
		// marqueur par Pays concerne par la recherche actuelle
		var nombre_pays = filtre_pays.options.length;
		for ( var i = 1; i < nombre_pays; i++) {
			var coordonnees = new OpenLayers.LonLat(filtre_pays.options[i]
					.getAttribute("longitude"), filtre_pays.options[i]
					.getAttribute("latitude"));
			ajout_marqueur(filtre_pays.options[i].text, coordonnees);
		}

	} else {
		if (filtre_ville.selectedIndex == 0) {
			// Si le filtre pays est renseigne mais pas le filtre ville
			// l'application affiche les villes du pays apres avoir effectue un
			// zoom sur le pays

			var nombre_ville = filtre_ville.options.length;
			for ( var i = 1; i < nombre_ville; i++) {
				var optionVilleSelectionne = filtre_ville.options[i];
				var coordonnees = new OpenLayers.LonLat(optionVilleSelectionne
						.getAttribute("longitude"), optionVilleSelectionne
						.getAttribute("latitude"));
				ajout_marqueur(optionVilleSelectionne.text, coordonnees);
				console.log(optionVilleSelectionne.text);
			}

		} else {
			// Si le filtre ville est renseigne l'application affiche un
			// marqueur sur la ville apres un zoom sur cette derniere
			
			var filtreville_ID = filtre_ville.selectedIndex;
			var optionVilleSelectionne = filtre_ville.options[filtreville_ID];
			var coordonnees = new OpenLayers.LonLat(optionVilleSelectionne
					.getAttribute("longitude"), optionVilleSelectionne
					.getAttribute("latitude"));
			ajout_marqueur(optionVilleSelectionne.text, coordonnees);

		}
	}
}
