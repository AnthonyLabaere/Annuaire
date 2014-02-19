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
function initialisationFiltre_AJAX_Success_avecID(data, ARRAY_FILTRE) {
	var filtre = HTML(ARRAY_FILTRE[ARRAY_FILTRE_ID]);
	filtre.innerHTML = '';

	filtre_option_par_defaut = document.createElement('option');
	filtre_option_par_defaut.innerHTML = ARRAY_FILTRE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
	filtre.appendChild(filtre_option_par_defaut);

	for ( var element in data) {
		filtre.options[filtre.options.length] = new Option(data[element][1],
				data[element][0]);
	}
}

function initialisationFiltre_AJAX_Success_sansID(data, ARRAY_FILTRE) {
	var filtre = HTML(ARRAY_FILTRE[ARRAY_FILTRE_ID]);
	filtre.innerHTML = '';

	filtre_option_par_defaut = document.createElement('option');
	filtre_option_par_defaut.innerHTML = ARRAY_FILTRE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
	filtre.appendChild(filtre_option_par_defaut);

	for ( var element in data) {
		filtre.options[filtre.options.length] = new Option(data[element]);
	}
}

/** Alimentation du filtre des annees de promotion */
function initialisationFiltreCentralien() {
	jsRoutes.controllers.ServiceCentralien.AJAX_listeDesCentraliens().ajax(
			{
				success : function(data, textStatus, jqXHR) {
					initialisationFiltre_AJAX_Success_avecID(data,
							ARRAY_FILTRE_CENTRALIEN);
				}
			});
}

/** Alimentation du filtre des annees de promotion */
function initialisationFiltreAnneePromotion() {
	jsRoutes.controllers.ServiceAnneePromotion.AJAX_listeDesAnneesPromotion()
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							initialisationFiltre_AJAX_Success_avecID(data,
									ARRAY_FILTRE_ANNEEPROMOTION);
						}
					});
}

/** Alimentation du filtre des ecoles (si ce filtre existe) */
function initialisationFiltreEcole() {
	if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
		jsRoutes.controllers.ServiceEcole.AJAX_listeDesEcoles().ajax(
				{
					success : function(data, textStatus, jqXHR) {
						initialisationFiltre_AJAX_Success_avecID(data,
								ARRAY_FILTRE_ECOLE);
					}
				});
	}
}

/** Alimentation du filtre des entreprises (si ce filtre existe) */
function initialisationFiltreEntreprise() {
	if (HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID])) {
		jsRoutes.controllers.ServiceEntreprise.AJAX_listeDesEntreprises().ajax(
				{
					success : function(data, textStatus, jqXHR) {
						initialisationFiltre_AJAX_Success_avecID(data,
								ARRAY_FILTRE_ENTREPRISE);
					}
				});
	}
}

/** Alimentation du filtre des secteurs */
function initialisationFiltreSecteur() {
	jsRoutes.controllers.ServiceSecteur.AJAX_listeDesSecteurs().ajax(
			{
				success : function(data, textStatus, jqXHR) {
					initialisationFiltre_AJAX_Success_avecID(data,
							ARRAY_FILTRE_SECTEUR);
				}
			});
}

/** Alimentation du filtre des pays */
function initialisationFiltrePays() {
	jsRoutes.controllers.ServicePays.AJAX_listeDesPays().ajax({
		success : function(data, textStatus, jqXHR) {
			initialisationFiltre_AJAX_Success_sansID(data, ARRAY_FILTRE_PAYS);
		}
	});
}

// On initialise les filtres au demarrage de l'application web
initialisationFiltres();

//Creation du filtre des villes
function creation_filtreVille(pays_nom) {
	// Creation du filtre
	var villeTd1 = document.createElement('td');
	villeTd1.innerHTML = 'Ville';

	var filtre_ville = document.createElement('select');
	filtre_ville.setAttribute('name', 'Ville');
	filtre_ville.setAttribute('id', ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]);
	filtre_ville.setAttribute('onChange', 'miseAJourDesFiltres(this.id)');

	var filtre_ville_option_par_defaut = document.createElement('option');
	filtre_ville_option_par_defaut.innerHTML = ARRAY_FILTRE_VILLE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];

	filtre_ville.appendChild(filtre_ville_option_par_defaut);

	var villeTd2 = document.createElement('td');
	villeTd2.appendChild(filtre_ville);

	var villeTr = document.createElement('tr');
	villeTr.setAttribute('id', 'tr_ville');
	villeTr.appendChild(villeTd1);
	villeTr.appendChild(villeTd2);

	HTML('tableau_critere').appendChild(villeTr);
}
