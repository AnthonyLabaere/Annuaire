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
	if (HTML('filtre_anneePromotion').selectedIndex != '0') {
		anneePromotion_libelle = HTML('filtre_anneePromotion').value;
	}
	if (HTML('filtre_ecole').selectedIndex != '0') {
		ecole_nom = HTML('filtre_ecole').value;
	}
	if (HTML('filtre_entreprise').selectedIndex != '0') {
		entreprise_nom = HTML('filtre_entreprise').value;
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

	// Les filtres actifs ne sont pas modifies
	if (anneePromotion_libelle == VIDE) {
		miseAJourDuFiltreAnneePromotion(entreprise_nom, secteur_nom, pays_nom,
				ville_nom);
	}
	if (ecole_nom == VIDE) {
		miseAJourDuFiltreEcole(anneePromotion_libelle, entreprise_nom,
				secteur_nom, pays_nom, ville_nom);
	}
	if (entreprise_nom == VIDE) {
		miseAJourDuFiltreEntreprise(anneePromotion_libelle, secteur_nom,
				pays_nom, ville_nom);
	}
	if (secteur_nom == VIDE) {
		miseAJourDuFiltreSecteur(anneePromotion_libelle, entreprise_nom,
				pays_nom, ville_nom);
	}
	if (pays_nom == VIDE) {
		miseAJourDuFiltrePays(anneePromotion_libelle, entreprise_nom,
				secteur_nom);
	}
	if (ville_nom == VIDE) {
		miseAJourDuFiltreVille(anneePromotion_libelle, entreprise_nom,
				secteur_nom, pays_nom);
	}

}

function miseAJourDuFiltreAnneePromotion(entreprise_nom, secteur_nom, pays_nom,
		ville_nom) {
	jsRoutes.controllers.ServiceAnneePromotion
			.AJAX_listeDesAnneesPromotionSelonCriteres(entreprise_nom,
					secteur_nom, pays_nom, ville_nom)
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_anneePromotion = HTML('filtre_anneePromotion');

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

function miseAJourDuFiltreEntreprise(anneePromotion_libelle, secteur_nom,
		pays_nom, ville_nom) {
	jsRoutes.controllers.ServiceEntreprise
			.AJAX_listeDesEntreprisesSelonCriteres(anneePromotion_libelle,
					secteur_nom, pays_nom, ville_nom)
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_entreprise = HTML('filtre_entreprise');

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

function miseAJourDuFiltreEcole() {

}

function miseAJourDuFiltreSecteur(anneePromotion_libelle, entreprise_nom,
		pays_nom, ville_nom) {

}

function miseAJourDuFiltrePays(anneePromotion_libelle, entreprise_nom,
		secteur_nom) {
	jsRoutes.controllers.ServicePays
			.AJAX_listeDesPaysSelonCriteres(anneePromotion_libelle, entreprise_nom,
					secteur_nom)
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_pays = HTML('filtre_pays');

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
function miseAJourDuFiltreVille(anneePromotion_libelle, entreprise_nom,
		secteur_nom, pays_nom) {

	if (!HTML('filtre_ville')) {
		// Si le filtre ville n'existe pas et qu'un un pays a ete selectionne,
		// ce premier est cree avec les informations du pays
		if (HTML('filtre_pays').selectedIndex != 0) {
			creationAlimentation_filtreVille(pays_nom);
		}
	} else {
		// Si le filtre pays est desactive alors le filtre ville est retire du
		// panneau
		if (HTML('filtre_pays').selectedIndex == 0) {
			suppression_filtreVille();
		} else {
			jsRoutes.controllers.ServiceVille
					.AJAX_listeDesVillesSelonCriteres(anneePromotion_libelle,
							entreprise_nom, secteur_nom, pays_nom)
					.ajax(
							{
								success : function(data, textStatus, jqXHR) {
									var filtre_ville = HTML('filtre_ville');

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
