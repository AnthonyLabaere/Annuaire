/**
 * Cette fonction est utilisee lors de la selection du filtre_ecole ou du
 * filtre_entreprise par l'utilisateur afin de remplacer un filtre par l'autre
 */
function miseAJourEcoleOuEntreprise() {
	var filtre_ecoleOuEntreprise = HTML('filtre_ecoleOuEntreprise').value;

	var td_ecoleOuEntreprise = HTML('td_ecoleOuEntreprise');
	td_ecoleOuEntreprise.innerHTML = '';

	var filtre = document.createElement('select');
	var filtre_option_par_defaut = document.createElement('option');

	if (filtre_ecoleOuEntreprise == 'filtre_entreprise') {
		filtre.setAttribute('name', 'Entreprise');
		filtre.setAttribute('id', ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID]);

		filtre_option_par_defaut.innerHTML = ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];

	} else {
		filtre.setAttribute('name', 'Ecole');
		filtre.setAttribute('id', ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID]);

		filtre_option_par_defaut.innerHTML = ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];

	}

	filtre.setAttribute('onChange', 'miseAJourDesFiltres(this.id)');
	filtre.appendChild(filtre_option_par_defaut);
	HTML('td_ecoleOuEntreprise').appendChild(filtre);

	// Remise a jour des filtres
	resetAll();
}

/** Cette fonction reinitialise tous les filtres */
function resetAll() {
	miseAJourDesFiltres();
}

function selectionneArrayFiltreSelonID(filtre_ID) {
	if (ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ID] == filtre_ID) {
		return ARRAY_FILTRE_ANNEEPROMOTION;
	} else if (ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID] == filtre_ID) {
		return ARRAY_FILTRE_ECOLE;
	} else if (ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID] == filtre_ID) {
		return ARRAY_FILTRE_ENTREPRISE;
	} else if (ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ID] == filtre_ID) {
		return ARRAY_FILTRE_SECTEUR;
	} else if (ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID] == filtre_ID) {
		return ARRAY_FILTRE_PAYS;
	} else if (ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID] == filtre_ID) {
		return ARRAY_FILTRE_VILLE;
	}

}

/**
 * Cette fonction est appelee lorsque l'un des filtres a ete modifie. Les autres
 * filtres sont mis a jour en consequence apres requetage de la BDD. Si tous les
 * filtres doivent être modifies, il suffit de ne pas donner de parametre
 */
function miseAJourDesFiltres(filtre_ID) {

	// Si pas de parametre, tous les filtres sont reinitialise
	if (!filtre_ID) {
		miseAJourDuFiltreAnneePromotion();
		if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
			miseAJourDuFiltreEcole();
		} else {
			miseAJourDuFiltreEntreprise();
		}
		miseAJourDuFiltreSecteur();
		miseAJourDuFiltrePays();
		HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]).selectedIndex = 0;
		miseAJourDuFiltreVille();

		// Ne pas oublier de reinitialiser l'ordre d'activation des filtres !
		ORDRE_ACTIVATION_DERNIERE_VALEUR = 1;
		ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
		ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
		ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
		ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
		ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
		ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;

	} else {
		// Si le filtre vient d'etre re-modifie, il faut remettre à jour les
		// filtres pour lesquels l'activation a
		// ete realisee apres celle de ce premier
		var arrayDuFiltreReinitialise = selectionneArrayFiltreSelonID(filtre_ID);
		var ordreActivationDuFiltreReinitialise = NOMBRE_TOTAL_FILTRES + 1;
		if (arrayDuFiltreReinitialise[ARRAY_FILTRE_ORDRE_ACTIVATION] != ORDRE_ACTIVATION_PAR_DEFAUT) {
			ordreActivationDuFiltreReinitialise = arrayDuFiltreReinitialise[ARRAY_FILTRE_ORDRE_ACTIVATION];
		} else {
			arrayDuFiltreReinitialise[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_DERNIERE_VALEUR;
			ORDRE_ACTIVATION_DERNIERE_VALEUR++;
		}
		var anneePromotion_libelle;
		var ecole_nom;
		var entreprise_nom;
		var secteur_nom;
		var pays_nom;
		var ville_nom;

		// Recuperation de la valeur des differents filtres actives pour le
		// requetage
		if (HTML(ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ID]).selectedIndex != '0') {
			anneePromotion_libelle = HTML(ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ID]).value;
		}
		if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])
				&& HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID]).selectedIndex != '0') {
			ecole_nom = HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID]).value;
		}
		if (HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID])
				&& HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID]).selectedIndex != '0') {
			entreprise_nom = HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID]).value;
		}
		if (HTML(ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ID]).selectedIndex != '0') {
			secteur_nom = HTML(ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ID]).value;
		}
		if (HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]).selectedIndex != '0') {
			pays_nom = HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]).value;
		}
		if (HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID])
				&& HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]).selectedIndex != '0') {
			ville_nom = HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]).value;
		}

		// Les filtres sont modifiees s'ils sont inactifs ou si leur activation
		// a
		// ete realisee apres celle d'un filtre qui vient d'etre reinitialise
		if (!anneePromotion_libelle
				|| ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ORDRE_ACTIVATION] > ordreActivationDuFiltreReinitialise) {
			miseAJourDuFiltreAnneePromotion(ecole_nom, entreprise_nom,
					secteur_nom, pays_nom, ville_nom);
		}
		if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])
				&& (!ecole_nom || ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ORDRE_ACTIVATION] > ordreActivationDuFiltreReinitialise)) {
			miseAJourDuFiltreEcole(anneePromotion_libelle, secteur_nom,
					pays_nom, ville_nom);
		}
		if (HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID])
				&& (!entreprise_nom || ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ORDRE_ACTIVATION] > ordreActivationDuFiltreReinitialise)) {
			miseAJourDuFiltreEntreprise(anneePromotion_libelle, secteur_nom,
					pays_nom, ville_nom);
		}
		if (!secteur_nom
				|| ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ORDRE_ACTIVATION] > ordreActivationDuFiltreReinitialise) {
			miseAJourDuFiltreSecteur(anneePromotion_libelle, ecole_nom,
					entreprise_nom, pays_nom, ville_nom);
		}
		if (!pays_nom
				|| ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ORDRE_ACTIVATION] > ordreActivationDuFiltreReinitialise) {
			miseAJourDuFiltrePays(anneePromotion_libelle, ecole_nom,
					entreprise_nom, secteur_nom);
		}
		if (!ville_nom
				|| ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ORDRE_ACTIVATION] > ordreActivationDuFiltreReinitialise) {
			miseAJourDuFiltreVille(anneePromotion_libelle, ecole_nom,
					entreprise_nom, secteur_nom, pays_nom);
		}

		// Si le filtre avait ete reinitialise alors on remet a -1 la valeur du
		// filtre reinitialise et on decremente de 1 les filtres actives apres
		// lui
		if (HTML(filtre_ID).selectedIndex == '0') {
			if (ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ORDRE_ACTIVATION] > ordreActivationDuFiltreReinitialise) {
				ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ORDRE_ACTIVATION]--;
			}
			if (ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ORDRE_ACTIVATION] > ordreActivationDuFiltreReinitialise) {
				ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ORDRE_ACTIVATION]--;
			}
			if (ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ORDRE_ACTIVATION] > ordreActivationDuFiltreReinitialise) {
				ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ORDRE_ACTIVATION]--;
			}
			if (ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ORDRE_ACTIVATION] > ordreActivationDuFiltreReinitialise) {
				ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ORDRE_ACTIVATION]--;
			}
			if (ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ORDRE_ACTIVATION] > ordreActivationDuFiltreReinitialise) {
				ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ORDRE_ACTIVATION]--;
			}
			if (ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ORDRE_ACTIVATION] > ordreActivationDuFiltreReinitialise) {
				ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ORDRE_ACTIVATION]--;
			}
			arrayDuFiltreReinitialise[ARRAY_FILTRE_ORDRE_ACTIVATION] = -1;
		}

	}

	console.log("Ordre activation - AnneePromotion : "
			+ ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ORDRE_ACTIVATION]);
	console.log("Ordre activation - Ecole : "
			+ ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ORDRE_ACTIVATION]);
	console.log("Ordre activation - Entreprise : "
			+ ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ORDRE_ACTIVATION]);
	console.log("Ordre activation - Secteur : "
			+ ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ORDRE_ACTIVATION]);
	console.log("Ordre activation - Pays : "
			+ ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ORDRE_ACTIVATION]);
	console.log("Ordre activation - Ville : "
			+ ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ORDRE_ACTIVATION]);
	console.log("----------------------------------");

}

function miseAJourDuFiltreAnneePromotion(ecole_nom, entreprise_nom,
		secteur_nom, pays_nom, ville_nom) {

	jsRoutes.controllers.ServiceAnneePromotion
			.AJAX_listeDesAnneesPromotionSelonCriteres(
					ecole_nom ? ecole_nom : "",
					entreprise_nom ? entreprise_nom : "",
					secteur_nom ? secteur_nom : "", pays_nom ? pays_nom : "",
					ville_nom ? ville_nom : "")
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_anneePromotion = HTML(ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ID]);

							// Suppression des elements existants dans le filtre
							filtre_anneePromotion.innerHTML = "";
							filtre_anneePromotion_option_par_defaut = document
									.createElement('option');
							filtre_anneePromotion_option_par_defaut.innerHTML = ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
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
			.AJAX_listeDesEcolesSelonCriteres(
					anneePromotion_libelle ? anneePromotion_libelle : "",
					secteur_nom ? secteur_nom : "", pays_nom ? pays_nom : "",
					ville_nom ? ville_nom : "")
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_ecole = HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID]);

							// Suppression des elements existants dans le filtre
							filtre_ecole.innerHTML = "";
							filtre_ecole_option_par_defaut = document
									.createElement('option');
							filtre_ecole_option_par_defaut.innerHTML = ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
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
			.AJAX_listeDesEntreprisesSelonCriteres(
					anneePromotion_libelle ? anneePromotion_libelle : "",
					secteur_nom ? secteur_nom : "", pays_nom ? pays_nom : "",
					ville_nom ? ville_nom : "")
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_entreprise = HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID]);

							// Suppression des elements existants dans le filtre
							filtre_entreprise.innerHTML = "";
							filtre_entreprise_option_par_defaut = document
									.createElement('option');
							filtre_entreprise_option_par_defaut.innerHTML = ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
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
			.AJAX_listeDesSecteursSelonCriteres(
					anneePromotion_libelle ? anneePromotion_libelle : "",
					ecole_nom ? ecole_nom : "",
					entreprise_nom ? entreprise_nom : "",
					pays_nom ? pays_nom : "", ville_nom ? ville_nom : "")
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_secteur = HTML(ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ID]);

							// Suppression des elements existants dans le filtre
							filtre_secteur.innerHTML = "";
							filtre_secteur_option_par_defaut = document
									.createElement('option');
							filtre_secteur_option_par_defaut.innerHTML = ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
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
			.AJAX_listeDesPaysSelonCriteres(
					anneePromotion_libelle ? anneePromotion_libelle : "",
					ecole_nom ? ecole_nom : "",
					entreprise_nom ? entreprise_nom : "",
					secteur_nom ? secteur_nom : "")
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_pays = HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]);

							// Suppression des elements existants dans le filtre
							filtre_pays.innerHTML = "";
							filtre_pays_option_par_defaut = document
									.createElement('option');
							filtre_pays_option_par_defaut.innerHTML = ARRAY_FILTRE_PAYS[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
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

	if (!HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID])) {
		// Si le filtre ville n'existe pas et qu'un un pays a ete selectionne,
		// ce premier est cree avec les informations du pays
		if (HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]).selectedIndex != 0) {
			creationAlimentation_filtreVille(pays_nom);
		}
	} else {
		// Si le filtre pays est desactive alors le filtre ville est retire du
		// panneau
		if (HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]).selectedIndex == 0) {
			suppression_filtreVille();
		} else {
			jsRoutes.controllers.ServiceVille
					.AJAX_listeDesVillesSelonCriteres(
							anneePromotion_libelle ? anneePromotion_libelle
									: "", ecole_nom ? ecole_nom : "",
							entreprise_nom ? entreprise_nom : "",
							secteur_nom ? secteur_nom : "",
							pays_nom ? pays_nom : "")
					.ajax(
							{
								success : function(data, textStatus, jqXHR) {
									var filtre_ville = HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]);

									// Suppression des elements existants dans
									// le
									// filtre
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

	}
}

function suppression_filtreVille() {
	HTML('tableau_critere').removeChild(HTML('tr_ville'));
}
