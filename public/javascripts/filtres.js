function reset(idFiltre) {
	HTML(idFiltre).selectedIndex = '0';
	miseAJourDesFiltres();
}

/**
 * Cette fonction est appelee lorsque l'un des filtres a ete modifie. Les autres
 * filtres sont mis a jour en consequence apres requetage de la BDD
 */
function miseAJourDesFiltres() {
	var anneePromotion_libelle = "";
	var entreprise_nom = "";
	var secteur_nom = "";
	var pays_nom = "";
	var ville_nom = "";

	if (HTML('filtre_anneePromotion').selectedIndex != '0') {
		anneePromotion_libelle = HTML('filtre_anneePromotion').value;
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

	miseAJourDuFiltreAnneePromotion(entreprise_nom, secteur_nom, pays_nom,
			ville_nom);
	// miseAJourDuFiltreEcole(anneePromotion_libelle, entreprise_nom,
	// secteur_nom, pays_nom, ville_nom);
	miseAJourDuFiltreEntreprise(anneePromotion_libelle, secteur_nom, pays_nom,
			ville_nom);
	miseAJourDuFiltreSecteur(anneePromotion_libelle, entreprise_nom, pays_nom,
			ville_nom);
	miseAJourDuFiltrePays(anneePromotion_libelle, entreprise_nom, secteur_nom,
			ville_nom);
	miseAJourDuFiltreVille(anneePromotion_libelle, entreprise_nom, secteur_nom,
			pays_nom);

}

function miseAJourDuFiltreAnneePromotion(entreprise_nom, secteur_nom, pays_nom,
		ville_nom) {

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

// function miseAJourDuFiltreEcole() {
//
// }

function miseAJourDuFiltreSecteur(anneePromotion_libelle, entreprise_nom,
		pays_nom, ville_nom) {

}

function miseAJourDuFiltrePays(anneePromotion_libelle, entreprise_nom,
		secteur_nom, ville_nom) {

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
