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

/** Le marqueur selectionne */
var SELECTED_FEATURE;

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
		miseAJourDesFiltres(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]);
	} else {
		// Si le marqueur est de type ville alors on affiche une modale avec les
		// informations sur les centraliens correspondants aux filtres
		// selectionnes
		console.log(feature);
		console.log(feature.attributes.type);
		afficherModale();
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

	// TODO : on effectue le zoom a la fin si on n'a qu'un seul marqueur place
}

/**
 * Cette fonction permet d'afficher une modale lors d'un clic sur un marqueur
 * ville
 */
function afficherModale() {
	var popID = 'modale';
	var popWidth = 500;

	// Faire apparaitre la pop-up et ajouter le bouton de
	// fermeture
	$('#' + popID)
			.fadeIn()
			.css({
				'width' : popWidth
			})
			.prepend(
					'<a href="#" class="close"><img src="/assets/images/fermer.png" class="bouton_fermeture" title="Close Window" alt="Close" /></a>');

	// Récupération du margin, qui permettra de centrer la
	// fenêtre - on ajuste de 80px en conformité avec le CSS
	var popMargTop = ($('#' + popID).height() + 80) / 2;
	var popMargLeft = ($('#' + popID).width() + 80) / 2;

	// Apply Margin to Popup
	$('#' + popID).css({
		'margin-top' : -popMargTop,
		'margin-left' : -popMargLeft
	});

	// Apparition du fond - .css({'filter' :
	// 'alpha(opacity=80)'}) pour corriger les bogues
	// d'anciennes versions de IE
	$('body').append('<div id="modale_fond"></div>');
	$('#modale_fond').css({
		'filter' : 'alpha(opacity=80)'
	}).fadeIn();

	return false;
}

/**
 * Cette fonction alimente la modale en fonction des informations a afficher
 */
function alimenterModale() {

}

/**
 * Cette fonction jQuery supprime la modale lors d'un clic sur son bouton de
 * fermeture ou sur la carte (en dehors de la modale)
 */
jQuery(function($) {
	// Fermer la modale et supprimer la transparence noire de l'arriere plan
	$('body').on('click', 'a.close, #modale_fond', function() {
		// Lors d'un clic sur body :
		$('#modale_fond , .modale_block').fadeOut(function() {
			$('#modale_fond, a.close').remove();
		});
		return false;
	});
});