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
		alimenterModale(id);
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
	jQuery('#' + popID)
			.fadeIn()
			.css({
				'width' : 800,
				'height' : 500
			})
			.prepend(
					'<a href="#" class="fermer"><img src="/assets/images/fermer.png" class="bouton_fermeture" title="Close Window" alt="Close" /></a>');

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
function alimenterModale(ville_ID) {

	// On enregistre les valeurs des filtres dans des variables pour l'appel
	// Ajax
	var centralien_ID;
	var anneePromotion_ID;
	var ecole_ID;
	var entreprise_ID;
	var secteur_ID;

	// On indique au serveur quel est le filtre ignore entre Ecole
	// ou Entreprise
	if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
		entreprise_ID = ECOLE_OU_ENTREPRISE_INACTIF;
	} else {
		ecole_ID = ECOLE_OU_ENTREPRISE_INACTIF;
	}

	if (HTML(ARRAY_FILTRES[ARRAY_FILTRES_CENTRALIEN][ARRAY_FILTRE_ID]).selectedIndex != 0) {
		centralien_ID = HTML(ARRAY_FILTRES[ARRAY_FILTRES_CENTRALIEN][ARRAY_FILTRE_ID]).value;
	}
	if (HTML(ARRAY_FILTRES[ARRAY_FILTRES_ANNEEPROMOTION][ARRAY_FILTRE_ID]).selectedIndex != 0) {
		anneePromotion_ID = HTML(ARRAY_FILTRES[ARRAY_FILTRES_ANNEEPROMOTION][ARRAY_FILTRE_ID]).value;
	}
	if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])
			&& HTML(ARRAY_FILTRES[ARRAY_FILTRES_ECOLE][ARRAY_FILTRE_ID]).selectedIndex != 0) {
		ecole_ID = HTML(ARRAY_FILTRES[ARRAY_FILTRES_ECOLE][ARRAY_FILTRE_ID]).value;
	}
	if (HTML(ARRAY_FILTRES_ENTREPRISE[ARRAY_FILTRE_ID])
			&& HTML(ARRAY_FILTRES[ARRAY_FILTRES_ENTREPRISE][ARRAY_FILTRE_ID]).selectedIndex != 0) {
		entreprise_ID = HTML(ARRAY_FILTRES[ARRAY_FILTRES_ENTREPRISE][ARRAY_FILTRE_ID]).value;
	}
	if (HTML(ARRAY_FILTRES[ARRAY_FILTRES_SECTEUR][ARRAY_FILTRE_ID]).selectedIndex != 0) {
		secteur_ID = HTML(ARRAY_FILTRES[ARRAY_FILTRES_SECTEUR][ARRAY_FILTRE_ID]).value;
	}

	// On supprime le tableau precedent s'il existe
	var modale = HTML('modale');
	modale.innerHTML = '';

	// On rappelle a l'utilisateur les criteres precedemments renseignes :
	var rappel = document.createElement('p');
	var rappelTexte = '';
	if (centralien_ID) {
		rappelTexte += 'Coordonn&eacute;es de l\activité ';
		if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
			rappelTexte += ' &eacute;tudiante ';
		} else {
			rappelTexte += 'professionnelle ';
		}
		rappelTexte += 'du Centralien'
				+ HTML(ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ID] + "_"
						+ centralien_ID).text;
		rappelTexte += ' dans la ville '
				+ HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID] + "_" + ville_ID).text;
	} else {
		rappelTexte += 'Coordonn&eacute;es des Centraliens ayant';
		if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
			rappelTexte += ' &eacute;tudi&eacute; &agrave; ';
		} else {
			rappelTexte += ' travaill&eacute; &agrave; ';
		}
		rappelTexte += HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID] + "_"
				+ ville_ID).text;
		if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
			if (ecole_ID) {
				rappelTexte += ' , à l\&Eacute;cole '
						+ HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID] + "_"
								+ ecole_ID).text;
			}
		} else {
			if (entreprise_ID) {
				rappelTexte += ' pour l\'entreprise '
						+ HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID] + "_"
								+ entreprise_ID).text;
			}
		}
		if (secteur_ID) {
			rappelTexte += ' dans le secteur '
					+ HTML(ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ID] + "_"
							+ secteur_ID).text;
		}
	}

	rappel.innerHTML = rappelTexte;
	modale.appendChild(rappel);

	// On insere la base du tableau dans la modale
	var prenomTh = document.createElement('th');
	prenomTh.innerHTML = 'Pr&eacute;nom';
	var nomTh = document.createElement('th');
	nomTh.innerHTML = 'Nom';
	var anneePromotionTh = document.createElement('th');
	anneePromotionTh.innerHTML = 'Promotion';
	var ecoleOuEntrepriseTh = document.createElement('th');
	if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
		ecoleOuEntrepriseTh.innerHTML = "Ecole";
	} else {
		ecoleOuEntrepriseTh.innerHTML = "Entreprise";
	}
	var secteurTh = document.createElement('th');
	secteurTh.innerHTML = 'Secteur';

	var bodyTr = document.createElement('tr');
	bodyTr.appendChild(prenomTh);
	bodyTr.appendChild(nomTh);
	bodyTr.appendChild(anneePromotionTh);
	bodyTr.appendChild(ecoleOuEntrepriseTh);
	bodyTr.appendChild(secteurTh);

	var tableau_coordonnees = document.createElement('table');
	tableau_coordonnees.setAttribute('id', 'tableau_coordonnees');
	tableau_coordonnees.appendChild(bodyTr);

	modale.appendChild(tableau_coordonnees);

	// Et on ajoute les nouvelles a partir de la requete AJAX
	// "AJAX_listeDesCoordonneesDesCentraliens"
	jsRoutes.controllers.ServiceCentralien
			.AJAX_listeDesCoordonneesDesCentraliens(
					centralien_ID ? centralien_ID : "",
					anneePromotion_ID ? anneePromotion_ID : "",
					ecole_ID ? ecole_ID : "",
					entreprise_ID ? entreprise_ID : "",
					secteur_ID ? secteur_ID : "", ville_ID ? ville_ID : "")
			.ajax({
				async : false,
				success : function(data, textStatus, jqXHR) {
					var tableau_coordonnees = HTML('tableau_coordonnees');
					for ( var element in data) {

						var prenomTd = document.createElement('td');
						prenomTd.innerHTML = data[element][0];
						var nomTd = document.createElement('td');
						nomTd.innerHTML = data[element][1];
						var anneePromotionTd = document.createElement('td');
						anneePromotionTd.innerHTML = data[element][2];
						var ecoleOuEntrepriseTd = document.createElement('td');
						ecoleOuEntrepriseTd.innerHTML = data[element][3];
						var secteurTd = document.createElement('td');
						secteurTd.innerHTML = data[element][4];

						var modaleTr = document.createElement('tr');
						modaleTr.appendChild(prenomTd);
						modaleTr.appendChild(nomTd);
						modaleTr.appendChild(anneePromotionTd);
						modaleTr.appendChild(ecoleOuEntrepriseTd);
						modaleTr.appendChild(secteurTd);

						tableau_coordonnees.appendChild(modaleTr);
					}

				}
			});
}

/**
 * Cette fonction jQuery supprime la modale lors d'un clic sur son bouton de
 * fermeture ou sur la carte (en dehors de la modale)
 */
jQuery(function($) {
	// Fermer la modale et supprimer la transparence noire de l'arriere plan
	$('body').on('click', 'a.fermer, #modale_fond', function() {
		// Lors d'un clic sur body :
		$('#modale_fond , .modale_block').fadeOut(function() {
			$('#modale_fond, a.fermer').remove();
		});
		return false;
	});
});