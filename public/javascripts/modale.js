/**
 * -----------------------------------------------------------------------------
 * Ce fichier contient les fonctions portant sur les modale des coordonnées
 * -----------------------------------------------------------------------------
 */

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
function alimenterModale(ville_ID, limite, numeroBloc, tri) {

	// On enregistre les valeurs des filtres dans des variables pour l'appel
	// Ajax
	var centralien_ID;
	var anneePromotion_ID;
	var ecole_ID;
	var entreprise_ID;
	var secteur_ID;
	
	if (!tri){
		tri = "defaut";
	}
	
	// On indique au serveur a partir de quelles lignes il doit chercher les resultats
	var offset = (numeroBloc-1)*limite;
	
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
	var rappel = document.createElement('div');
	var rappelTexte = '';
	if (centralien_ID) {
		rappelTexte += 'Coordonn&eacute;es de l\'activit&eacute; ';
		if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
			rappelTexte += ' &eacute;tudiante ';
		} else {
			rappelTexte += ' professionnelle ';
		}
		rappelTexte += 'du Centralien '
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
	prenomTh.setAttribute('onClick', 'alimenterModale('+ville_ID+', '+limite+', '+numeroBloc+', "prenom");afficherModale();');
	prenomTh.innerHTML = 'Pr&eacute;nom';
	var nomTh = document.createElement('th');
	nomTh.setAttribute('onClick', 'alimenterModale('+ville_ID+', '+limite+', '+numeroBloc+', "nom");afficherModale();');
	nomTh.innerHTML = 'Nom';
	var anneePromotionTh = document.createElement('th');
	anneePromotionTh.setAttribute('onClick', 'alimenterModale('+ville_ID+', '+limite+', '+numeroBloc+', "anneePromotion");afficherModale();');
	anneePromotionTh.innerHTML = 'Promotion';
	var ecoleOuEntrepriseTh = document.createElement('th');
	if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
		ecoleOuEntrepriseTh.setAttribute('onClick', 'alimenterModale('+ville_ID+', '+limite+', '+numeroBloc+', "ecole");afficherModale();');
		ecoleOuEntrepriseTh.innerHTML = "Ecole";
	} else {
		ecoleOuEntrepriseTh.setAttribute('onClick', 'alimenterModale('+ville_ID+', '+limite+', '+numeroBloc+', "entreprise");afficherModale();');
		ecoleOuEntrepriseTh.innerHTML = "Entreprise";
	}
	var secteurTh = document.createElement('th');
	secteurTh.setAttribute('onClick', 'alimenterModale('+ville_ID+', '+limite+', '+numeroBloc+', "secteur");afficherModale();');
	secteurTh.innerHTML = 'Secteur';

	var bodyTr = document.createElement('tr');
	bodyTr.appendChild(prenomTh);
	bodyTr.appendChild(nomTh);
	bodyTr.appendChild(anneePromotionTh);
	bodyTr.appendChild(ecoleOuEntrepriseTh);
	bodyTr.appendChild(secteurTh);
	
	var enTeteTableau = document.createElement('thead');
	enTeteTableau.appendChild(bodyTr);
	
	var tableau_coordonnees = document.createElement('table');
	tableau_coordonnees.setAttribute('id', 'tableau_coordonnees');
	tableau_coordonnees.appendChild(enTeteTableau);

	modale.appendChild(tableau_coordonnees);

	// Et on ajoute les nouvelles a partir de la requete AJAX
	// "AJAX_listeDesCoordonneesDesCentraliens"
	jsRoutes.controllers.ServiceCentralien
			.AJAX_listeDesCoordonneesDesCentraliens(
					centralien_ID ? centralien_ID : "",
					anneePromotion_ID ? anneePromotion_ID : "",
					ecole_ID ? ecole_ID : "",
					entreprise_ID ? entreprise_ID : "",
					secteur_ID ? secteur_ID : "",
					ville_ID ? ville_ID : "",
					limite,
					offset,
				    tri ? tri : "")
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