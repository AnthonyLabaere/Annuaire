function initialisationFiltres() {
	initialisationFiltreCentralien();
	initialisationFiltreAnneePromotion();
	initialisationFiltreEcole();
	initialisationFiltreEntreprise();
	initialisationFiltreSecteur();
	initialisationFiltrePays();
}

/** Alimentation du filtre des annees de promotion */
function initialisationFiltreCentralien() {
	jsRoutes.controllers.ServiceCentralien
			.AJAX_listeDesCentraliens()
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_centralien = HTML(ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ID]);
							filtre_centralien.innerHTML = '';

							filtre_centralien_option_par_defaut = document
									.createElement('option');
							filtre_centralien_option_par_defaut.innerHTML = ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
							filtre_centralien
									.appendChild(filtre_centralien_option_par_defaut);

							for ( var element in data) {
								filtre_centralien.options[filtre_centralien.options.length] = new Option(
										data[element][1], data[element][0]);
							}
						}
					});
}

/** Alimentation du filtre des annees de promotion */
function initialisationFiltreAnneePromotion() {
	jsRoutes.controllers.ServiceAnneePromotion
			.AJAX_listeDesAnneesPromotion()
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_anneePromotion = HTML(ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ID]);
							filtre_anneePromotion.innerHTML = '';

							filtre_anneePromotion_option_par_defaut = document
									.createElement('option');
							filtre_anneePromotion_option_par_defaut.innerHTML = ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
							filtre_anneePromotion
									.appendChild(filtre_anneePromotion_option_par_defaut);

							for ( var element in data) {
								filtre_anneePromotion.options[filtre_anneePromotion.options.length] = new Option(
										data[element]);
							}
						}
					});
}

/** Alimentation du filtre des ecoles (si ce filtre existe) */
function initialisationFiltreEcole() {
	if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
		jsRoutes.controllers.ServiceEcole
				.AJAX_listeDesEcoles()
				.ajax(
						{
							success : function(data, textStatus, jqXHR) {
								var filtre_ecole = HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID]);
								filtre_ecole.innerHTML = '';

								filtre_ecole_option_par_defaut = document
										.createElement('option');
								filtre_ecole_option_par_defaut.innerHTML = ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
								filtre_ecole
										.appendChild(filtre_ecole_option_par_defaut);

								for ( var element in data) {
									filtre_ecole.options[filtre_ecole.options.length] = new Option(
											data[element]);
								}
							}
						});
	}
}

/** Alimentation du filtre des entreprises (si ce filtre existe) */
function initialisationFiltreEntreprise() {
	if (HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID])) {
		jsRoutes.controllers.ServiceEntreprise
				.AJAX_listeDesEntreprises()
				.ajax(
						{
							success : function(data, textStatus, jqXHR) {
								var filtre_entreprise = HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID]);
								filtre_entreprise.innerHTML = '';

								filtre_entreprise_option_par_defaut = document
										.createElement('option');
								filtre_entreprise_option_par_defaut.innerHTML = ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
								filtre_entreprise
										.appendChild(filtre_entreprise_option_par_defaut);

								for ( var element in data) {
									filtre_entreprise.options[filtre_entreprise.options.length] = new Option(
											data[element]);
								}
							}
						});
	}
}

/** Alimentation du filtre des secteurs */
function initialisationFiltreSecteur() {
	jsRoutes.controllers.ServiceSecteur
			.AJAX_listeDesSecteurs()
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_secteur = HTML(ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ID]);
							filtre_secteur.innerHTML = '';

							filtre_secteur_option_par_defaut = document
									.createElement('option');
							filtre_secteur_option_par_defaut.innerHTML = ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
							filtre_secteur
									.appendChild(filtre_secteur_option_par_defaut);

							for ( var element in data) {
								filtre_secteur.options[filtre_secteur.options.length] = new Option(
										data[element]);
							}
						}
					});
}

/** Alimentation du filtre des pays */
function initialisationFiltrePays() {
	jsRoutes.controllers.ServicePays
			.AJAX_listeDesPays()
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_pays = HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]);
							filtre_pays.innerHTML = '';

							filtre_pays_option_par_defaut = document
									.createElement('option');
							filtre_pays_option_par_defaut.innerHTML = ARRAY_FILTRE_PAYS[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
							filtre_pays
									.appendChild(filtre_pays_option_par_defaut);

							for ( var element in data) {
								filtre_pays.options[filtre_pays.options.length] = new Option(
										data[element]);
							}
						}
					});
}

initialisationFiltres();

// Creation et alimentation du filtre des villes en considérant le pays
// selectionne
function creationAlimentation_filtreVille(pays_nom) {
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

	// Alimentation du filtre
	jsRoutes.controllers.ServiceVille
			.AJAX_listeDesVillesDuPays(pays_nom)
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_ville = HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]);

							// Suppression des elements existants dans le filtre
							filtre_ville.innerHTML = "";
							filtre_ville_option_par_defaut = document
									.createElement('option');
							filtre_ville_option_par_defaut.innerHTML = ARRAY_FILTRE_VILLE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
							filtre_ville
									.appendChild(filtre_ville_option_par_defaut);

							// Ajout des nouveaux elements
							for ( var element in data) {
								filtre_ville.options[filtre_ville.options.length] = new Option(
										data[element]);
							}

						}
					});
}
