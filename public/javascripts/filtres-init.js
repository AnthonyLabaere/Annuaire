function initialisationFiltres() {
	initialisationFiltreCentralien();
	initialisationFiltreAnneePromotion();
	initialisationFiltreEcole();
	initialisationFiltreEntreprise();
	initialisationFiltreSecteur();
	initialisationFiltrePays();
}

/**
 * Cette fonction permet d'alimenter le filtre donne en parametre avec les
 * donnees du serveur concernant ce filtre
 */
function initialisationFiltre_AJAX_Success(data, ARRAY_FILTRE) {
	var filtre_ID = ARRAY_FILTRE[ARRAY_FILTRE_ID];
	var filtre = HTML(filtre_ID);
	filtre.innerHTML = '';

	filtre_option_par_defaut = document.createElement('option');
	filtre_option_par_defaut.innerHTML = ARRAY_FILTRE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
	filtre.appendChild(filtre_option_par_defaut);

	for ( var element in data) {
		var option = document.createElement('option');
		option.value = data[element][0];
		option.text = data[element][1];
		// Si le filtre est pays ou ville alors on y ajoute les coordonnes GPS
		if (filtre_ID == ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]
				|| filtre_ID == ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]) {
			option.setAttribute('latitude', data[element][2]);
			option.setAttribute('longitude', data[element][3]);
		}
		filtre.appendChild(option);
	}
}

/** Alimentation du filtre des annees de promotion */
function initialisationFiltreCentralien() {
	jsRoutes.controllers.ServiceCentralien.AJAX_listeDesCentraliens().ajax({
		async : false,
		success : function(data, textStatus, jqXHR) {
			initialisationFiltre_AJAX_Success(data, ARRAY_FILTRE_CENTRALIEN);
		}
	});
}

/** Alimentation du filtre des annees de promotion */
function initialisationFiltreAnneePromotion() {
	jsRoutes.controllers.ServiceAnneePromotion.AJAX_listeDesAnneesPromotion()
			.ajax(
					{
						async : false,
						success : function(data, textStatus, jqXHR) {
							initialisationFiltre_AJAX_Success(data,
									ARRAY_FILTRE_ANNEEPROMOTION);
						}
					});
}

/** Alimentation du filtre des ecoles (si ce filtre existe) */
function initialisationFiltreEcole() {
	if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
		jsRoutes.controllers.ServiceEcole.AJAX_listeDesEcoles().ajax({
			async : false,
			success : function(data, textStatus, jqXHR) {
				initialisationFiltre_AJAX_Success(data, ARRAY_FILTRE_ECOLE);
			}
		});
	}
}

/** Alimentation du filtre des entreprises (si ce filtre existe) */
function initialisationFiltreEntreprise() {
	if (HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID])) {
		jsRoutes.controllers.ServiceEntreprise.AJAX_listeDesEntreprises().ajax(
				{
					async : false,
					success : function(data, textStatus, jqXHR) {
						initialisationFiltre_AJAX_Success(data,
								ARRAY_FILTRE_ENTREPRISE);
					}
				});
	}
}

/** Alimentation du filtre des secteurs */
function initialisationFiltreSecteur() {
	jsRoutes.controllers.ServiceSecteur.AJAX_listeDesSecteurs().ajax({
		async : false,
		success : function(data, textStatus, jqXHR) {
			initialisationFiltre_AJAX_Success(data, ARRAY_FILTRE_SECTEUR);
		}
	});
}

/** Alimentation du filtre des pays */
function initialisationFiltrePays() {
	jsRoutes.controllers.ServicePays.AJAX_listeDesPays().ajax({
		async : false,
		success : function(data, textStatus, jqXHR) {
			initialisationFiltre_AJAX_Success(data, ARRAY_FILTRE_PAYS);
		}
	});
}

// Creation du filtre des villes
function creation_filtreVille(pays_nom) {
	// Creation du filtre
	var villeTd1 = document.createElement('td');
	villeTd1.innerHTML = 'Ville';

	var filtre_ville = document.createElement('select');
	filtre_ville.setAttribute('name', 'Ville');
	filtre_ville.setAttribute('id', ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]);
	filtre_ville.setAttribute('onChange', 'miseAJourDesFiltres(this.id)');

	var villeTd2 = document.createElement('td');
	villeTd2.appendChild(filtre_ville);

	var bouton_reset = document.createElement('img');
	bouton_reset.setAttribute('src', '/assets/images/reset.png');
	bouton_reset.setAttribute('id', 'bouton_reset_ville');
	bouton_reset.setAttribute('alt', 'bouton_reset_ville');
	bouton_reset.setAttribute('onClick', 'reset(this.id)');
	bouton_reset.setAttribute('class', 'bouton_reset');
	bouton_reset
			.setAttribute('title', 'R&eacute;initialisation du champ Ville');

	var villeTd3 = document.createElement('td');
	villeTd3.appendChild(bouton_reset);

	var villeTr = document.createElement('tr');
	villeTr.setAttribute('id', 'tr_ville');
	villeTr.appendChild(villeTd1);
	villeTr.appendChild(villeTd2);
	villeTr.appendChild(villeTd3);

	HTML('tableau_critere').appendChild(villeTr);
}
