/**
 * Cette fonction est utilisee lors de la selection du filtre_ecole ou du
 * filtre_entreprise par l'utilisateur afin de remplacer un filtre par l'autre
 */
function miseAJourEcoleOuEntreprise() {
	console.log('miseAJourEcoleOuEntreprise');

	var filtre_ecoleOuEntreprise = HTML('filtre_ecoleOuEntreprise').value;

	var filtresActifs = new Array();
	filtresActifs.push(filtre_ecoleOuEntreprise == 'filtre_entreprise');
	filtresActifs.push(filtre_ecoleOuEntreprise == 'filtre_ecole');

	var td_ecoleOuEntreprise = HTML('td_ecoleOuEntreprise');
	td_ecoleOuEntreprise.innerHTML = '';

	var filtre = document.createElement('select');
	var filtre_option_par_defaut = document.createElement('option');

	if (filtresActifs[0]) {
		filtre.setAttribute('name', 'Entreprise');
		filtre.setAttribute('id', FILTRE_ENTREPRISE_ID);

		filtre_option_par_defaut.innerHTML = FILTRE_ECOLE_OPTION_PAR_DEFAUT_TEXTE;

	} else {
		filtre.setAttribute('name', 'Ecole');
		filtre.setAttribute('id', FILTRE_ECOLE_ID);

		filtre_option_par_defaut.innerHTML = FILTRE_ECOLE_OPTION_PAR_DEFAUT_TEXTE;

	}

	filtre.setAttribute('onChange', 'miseAJourDesFiltres(this.id)');
	filtre.appendChild(filtre_option_par_defaut);
	HTML('td_ecoleOuEntreprise').appendChild(filtre);

	// Mise a jour des filtres - seul les filtres actifs sont pris en compte
	var anneePromotion_libelle = VIDE;
	var secteur_nom = VIDE;
	var pays_nom = VIDE;
	var ville_nom = VIDE;

	if (HTML('filtre_anneePromotion').selectedIndex != '0') {
		anneePromotion_libelle = HTML('filtre_anneePromotion').value;
	}
	if (HTML('filtre_secteur').selectedIndex != '0') {
		secteur_nom = HTML('filtre_secteur').value;
	}
	if (HTML('filtre_pays').selectedIndex != '0') {
		pays_nom = HTML('filtre_pays').value;
	}
	if (HTML('filtre_ville') && HTML('filtre_ville').selectedIndex != '0') {
		ville_nom = HTML('filtre_ville').value;
	}
	if (filtresActifs[0]) {
		miseAJourDuFiltreEntreprise(anneePromotion_libelle, secteur_nom,
				pays_nom, ville_nom);
	} else {
		miseAJourDuFiltreEcole(anneePromotion_libelle, secteur_nom, pays_nom,
				ville_nom);

	}
}

function resetAll() {
	initialisationFiltres();
	if (HTML('filtre_ville')) {
		suppression_filtreVille();
	}
}

/**
 * Cette fonction est appelee lorsque l'un des filtres a ete modifie. Les autres
 * filtres sont mis a jour en consequence apres requetage de la BDD. Si tous les
 * filtres doivent être modifies, il suffit de ne pas donner de parametre
 */
function miseAJourDesFiltres(filtre_ID) {
	var anneePromotion_libelle = VIDE;
	var ecole_nom = VIDE;
	var entreprise_nom = VIDE;
	var secteur_nom = VIDE;
	var pays_nom = VIDE;
	var ville_nom = VIDE;

	// Seul les filtres actifs sont pris en compte
	if (HTML(FILTRE_ANNEEPROMOTION_ID).selectedIndex != '0') {
		anneePromotion_libelle = HTML(FILTRE_ANNEEPROMOTION_ID).value;
	}
	if (HTML(FILTRE_ECOLE_ID) && HTML(FILTRE_ECOLE_ID).selectedIndex != '0') {
		ecole_nom = HTML(FILTRE_ECOLE_ID).value;
	}
	if (HTML(FILTRE_ENTREPRISE_ID)
			&& HTML(FILTRE_ENTREPRISE_ID).selectedIndex != '0') {
		entreprise_nom = HTML(FILTRE_ENTREPRISE_ID).value;
	}
	if (HTML(FILTRE_SECTEUR_ID).selectedIndex != '0') {
		secteur_nom = HTML(FILTRE_SECTEUR_ID).value;
	}
	if (HTML(FILTRE_PAYS_ID).selectedIndex != '0') {
		pays_nom = HTML(FILTRE_PAYS_ID).value;
	}
	if (HTML(FILTRE_VILLE_ID) && HTML(FILTRE_VILLE_ID).selectedIndex != '0') {
		ville_nom = HTML(FILTRE_VILLE_ID).value;
	}

	// Les filtres actifs ne sont pas modifies
	if (anneePromotion_libelle == VIDE) {
		miseAJourDuFiltreAnneePromotion(ecole_nom, entreprise_nom, secteur_nom,
				pays_nom, ville_nom);
	}
	if (ecole_nom == VIDE && HTML(FILTRE_ECOLE_ID)) {
		miseAJourDuFiltreEcole(anneePromotion_libelle, secteur_nom, pays_nom,
				ville_nom);
	}
	if (entreprise_nom == VIDE && HTML(FILTRE_ENTREPRISE_ID)) {
		miseAJourDuFiltreEntreprise(anneePromotion_libelle, secteur_nom,
				pays_nom, ville_nom);
	}
	if (secteur_nom == VIDE) {
		miseAJourDuFiltreSecteur(anneePromotion_libelle, ecole_nom,
				entreprise_nom, pays_nom, ville_nom);
	}
	if (pays_nom == VIDE) {
		miseAJourDuFiltrePays(anneePromotion_libelle, ecole_nom,
				entreprise_nom, secteur_nom);
	}
	if (ville_nom == VIDE) {
		miseAJourDuFiltreVille(anneePromotion_libelle, ecole_nom,
				entreprise_nom, secteur_nom, pays_nom);
	}

}

function miseAJourDuFiltreAnneePromotion(ecole_nom, entreprise_nom,
		secteur_nom, pays_nom, ville_nom) {
	jsRoutes.controllers.ServiceAnneePromotion
			.AJAX_listeDesAnneesPromotionSelonCriteres(ecole_nom,
					entreprise_nom, secteur_nom, pays_nom, ville_nom)
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_anneePromotion = HTML(FILTRE_ANNEEPROMOTION_ID);

							// Suppression des elements existants dans le filtre
							filtre_anneePromotion.innerHTML = "";
							filtre_anneePromotion_option_par_defaut = document
									.createElement('option');
							filtre_anneePromotion_option_par_defaut.innerHTML = FILTRE_ANNEEPROMOTION_OPTION_PAR_DEFAUT_TEXTE;
							filtre_anneePromotion
									.appendChild(filtre_anneePromotion_option_par_defaut);

							// Ajout des nouveaux elements
							for ( var element in data) {
								filtre_anneePromotion.options[filtre_anneePromotion.options.length] = new Option(
										data[element]);
							}

						}
					});
}

function miseAJourDuFiltreEcole(anneePromotion_libelle, secteur_nom, pays_nom,
		ville_nom) {
	jsRoutes.controllers.ServiceEcole
			.AJAX_listeDesEcolesSelonCriteres(anneePromotion_libelle,
					secteur_nom, pays_nom, ville_nom)
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_ecole = HTML(FILTRE_ECOLE_ID);

							// Suppression des elements existants dans le filtre
							filtre_ecole.innerHTML = "";
							filtre_ecole_option_par_defaut = document
									.createElement('option');
							filtre_ecole_option_par_defaut.innerHTML = FILTRE_ECOLE_OPTION_PAR_DEFAUT_TEXTE;
							filtre_ecole
									.appendChild(filtre_ecole_option_par_defaut);

							// Ajout des nouveaux elements
							for ( var element in data) {
								filtre_ecole.options[filtre_ecole.options.length] = new Option(
										data[element]);
							}

						}
					});
}

function miseAJourDuFiltreEntreprise(anneePromotion_libelle, secteur_nom,
		pays_nom, ville_nom) {
	jsRoutes.controllers.ServiceEntreprise
			.AJAX_listeDesEntreprisesSelonCriteres(anneePromotion_libelle,
					secteur_nom, pays_nom, ville_nom)
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_entreprise = HTML(FILTRE_ENTREPRISE_ID);

							// Suppression des elements existants dans le filtre
							filtre_entreprise.innerHTML = "";
							filtre_entreprise_option_par_defaut = document
									.createElement('option');
							filtre_entreprise_option_par_defaut.innerHTML = FILTRE_ENTREPRISE_OPTION_PAR_DEFAUT_TEXTE;
							filtre_entreprise
									.appendChild(filtre_entreprise_option_par_defaut);

							// Ajout des nouveaux elements
							for ( var element in data) {
								filtre_entreprise.options[filtre_entreprise.options.length] = new Option(
										data[element]);
							}

						}
					});
}

function miseAJourDuFiltreSecteur(anneePromotion_libelle, ecole_nom,
		entreprise_nom, pays_nom, ville_nom) {
	jsRoutes.controllers.ServiceSecteur
			.AJAX_listeDesSecteursSelonCriteres(anneePromotion_libelle,
					ecole_nom, entreprise_nom, pays_nom, ville_nom)
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_secteur = HTML(FILTRE_SECTEUR_ID);

							// Suppression des elements existants dans le filtre
							filtre_secteur.innerHTML = "";
							filtre_secteur_option_par_defaut = document
									.createElement('option');
							filtre_secteur_option_par_defaut.innerHTML = FILTRE_SECTEUR_OPTION_PAR_DEFAUT_TEXTE;
							filtre_secteur
									.appendChild(filtre_secteur_option_par_defaut);

							// Ajout des nouveaux elements
							for ( var element in data) {
								filtre_secteur.options[filtre_secteur.options.length] = new Option(
										data[element]);
							}

						}
					});
}

function miseAJourDuFiltrePays(anneePromotion_libelle, ecole_nom,
		entreprise_nom, secteur_nom) {
	jsRoutes.controllers.ServicePays
			.AJAX_listeDesPaysSelonCriteres(anneePromotion_libelle, ecole_nom,
					entreprise_nom, secteur_nom)
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_pays = HTML(FILTRE_PAYS_ID);

							// Suppression des elements existants dans le filtre
							filtre_pays.innerHTML = "";
							filtre_pays_option_par_defaut = document
									.createElement('option');
							filtre_pays_option_par_defaut.innerHTML = FILTRE_PAYS_OPTION_PAR_DEFAUT_TEXTE;
							filtre_pays
									.appendChild(filtre_pays_option_par_defaut);

							// Ajout des nouveaux elements
							for ( var element in data) {
								filtre_pays.options[filtre_pays.options.length] = new Option(
										data[element]);
							}

						}
					});

}

/**
 * Si le filtre pays n'etait pas renseigne alors le filtre ville apparait. La
 * carte zoome sur le pays concerne. Les marqueurs des villes où des centraliens
 * sont presents apparaissent
 */
function miseAJourDuFiltreVille(anneePromotion_libelle, ecole_nom,
		entreprise_nom, secteur_nom, pays_nom) {

	if (!HTML(FILTRE_VILLE_ID)) {
		// Si le filtre ville n'existe pas et qu'un un pays a ete selectionne,
		// ce premier est cree avec les informations du pays
		if (HTML(FILTRE_PAYS_ID).selectedIndex != 0) {
			creationAlimentation_filtreVille(pays_nom);
		}
	} else {
		// Si le filtre pays est desactive alors le filtre ville est retire du
		// panneau
		if (HTML(FILTRE_PAYS_ID).selectedIndex == 0) {
			suppression_filtreVille();
		} else {
			jsRoutes.controllers.ServiceVille
					.AJAX_listeDesVillesSelonCriteres(anneePromotion_libelle,
							ecole_nom, entreprise_nom, secteur_nom, pays_nom)
					.ajax(
							{
								success : function(data, textStatus, jqXHR) {
									var filtre_ville = HTML(FILTRE_VILLE_ID);

									// Suppression des elements existants dans
									// le
									// filtre
									filtre_ville.innerHTML = "";
									filtre_ville_option_par_defaut = document
											.createElement('option');
									filtre_ville_option_par_defaut.innerHTML = FILTRE_VILLE_OPTION_PAR_DEFAUT_TEXTE;
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

	}
}
