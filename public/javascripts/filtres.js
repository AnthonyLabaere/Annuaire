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
	// console.log("resetAll");
	miseAJourDesFiltres();
	// HTML('bouton_reset_pays').setAttribute('disabled', 'true');
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

function selectionneNumeroFiltreSelonID(filtre_ID) {
	if (ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ID] == filtre_ID) {
		return ARRAY_FILTRES_ANNEEPROMOTION;
	} else if (ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID] == filtre_ID) {
		return ARRAY_FILTRES_ECOLE;
	} else if (ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID] == filtre_ID) {
		return ARRAY_FILTRES_ENTREPRISE;
	} else if (ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ID] == filtre_ID) {
		return ARRAY_FILTRES_SECTEUR;
	} else if (ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID] == filtre_ID) {
		return ARRAY_FILTRES_PAYS;
	} else if (ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID] == filtre_ID) {
		return ARRAY_FILTRES_VILLE;
	}
}

function calculTableauOrdreActivation() {
	var tableau_ordre_maj = new Array();
	var tableau_ordre_maj_idFiltre = new Array();

	for (id_array_filtres in ARRAY_FILTRES) {
		var ordre_activation = ARRAY_FILTRES[id_array_filtres][ARRAY_FILTRE_ORDRE_ACTIVATION];
		if (ordre_activation > 0) {
			var taille_tableau_ordre_maj = tableau_ordre_maj.length;
			if (taille_tableau_ordre_maj == 0) {
				tableau_ordre_maj.push(ordre_activation);
				tableau_ordre_maj_idFiltre.push(id_array_filtres);
			} else {
				var increment = 0;
				// Tant que l'on a pas trouve plus grand on continue a
				// chercher
				while (ordre_activation > tableau_ordre_maj[increment]
						&& increment < taille_tableau_ordre_maj) {
					increment++;
				}

				if (increment != taille_tableau_ordre_maj) {
					// on decale tout pour inserer le filtre plus
					// prioritaire
					var precedent_ordre = 0;
					var precedent_id = 0;
					for (i = increment; i < taille_tableau_ordre_maj; i++) {
						precedent_tmp = tableau_ordre_maj[i];
						tableau_ordre_maj[i] = precedent_ordre;
						precedent_ordre = precedent_tmp;

						precedent_tmp = tableau_ordre_maj_idFiltre[i];
						tableau_ordre_maj_idFiltre[i] = precedent_id;
						precedent_id = precedent_tmp;
					}
					// On insere la derniere valeur a la fin du tableau
					tableau_ordre_maj.push(precedent_ordre);
					tableau_ordre_maj_idFiltre.push(precedent_id);

					// Et on insere notre valeur
					tableau_ordre_maj[increment] = ordre_activation;
					tableau_ordre_maj_idFiltre[increment] = id_array_filtres;

				} else {
					// Sinon le filtre actuel est le plus grand donc il se
					// met a la fin du tableau
					tableau_ordre_maj.push(ordre_activation);
					tableau_ordre_maj_idFiltre.push(id_array_filtres);
				}
			}
		}
	}

	for (id_array_filtres in ARRAY_FILTRES) {
		var ordre_activation = ARRAY_FILTRES[id_array_filtres][ARRAY_FILTRE_ORDRE_ACTIVATION];
		// On rajoute les filtres non actives
		if (ordre_activation < 0) {
			tableau_ordre_maj_idFiltre.push(id_array_filtres);
		}
	}
	return tableau_ordre_maj_idFiltre;
}

function pauseDegueulasse(duree) {
	var debutDate = new Date().getTime();
	while (debutDate + duree > new Date().getTime()) {
	}
	return;
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
		// HTML('bouton_reset_pays').disabled = false;

		// Si le filtre vient d'etre re-modifie, il faut remettre à jour les
		// filtres pour lesquels l'activation a
		// ete realisee apres celle de ce premier
		var arrayDuFiltreModifie = selectionneArrayFiltreSelonID(filtre_ID);
		var ordreActivationDuFiltreReinitialise = NOMBRE_TOTAL_FILTRES + 1;
		if (arrayDuFiltreModifie[ARRAY_FILTRE_ORDRE_ACTIVATION] != ORDRE_ACTIVATION_PAR_DEFAUT) {
			ordreActivationDuFiltreReinitialise = arrayDuFiltreModifie[ARRAY_FILTRE_ORDRE_ACTIVATION];
		} else {
			arrayDuFiltreModifie[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_DERNIERE_VALEUR;
			ORDRE_ACTIVATION_DERNIERE_VALEUR++;
		}

		tableau_ordre_activation = calculTableauOrdreActivation();

		// On trouve ou est situe le filtre concerne par l'appel dans le
		// tableau tableau_ordre_activation
		var numeroFiltreModifie = selectionneNumeroFiltreSelonID(filtre_ID);
		var numeroFiltreModifieDansOA = 0;
		while (numeroFiltreModifie != tableau_ordre_activation[numeroFiltreModifieDansOA]) {
			numeroFiltreModifieDansOA++;
		}
		// Les filtres qui ont ete active apres sont mis a jour par rapport a
		// (tout) ceux active avant
		var tailleTableauOA = tableau_ordre_activation.length;
		for ( var i = numeroFiltreModifieDansOA + 1; i < tailleTableauOA; i++) {
//			pauseDegueulasse(500);

			var anneePromotion_libelle;
			var ecole_nom;
			var entreprise_nom;
			var secteur_nom;
			var pays_nom;
			var ville_nom;
			
			for ( var j = 0; j < i; j++) {
				if (ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ORDRE_ACTIVATION] != -1) {
					if (tableau_ordre_activation[j] == ARRAY_FILTRES_ANNEEPROMOTION) {
						anneePromotion_libelle = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					} else if (tableau_ordre_activation[j] == ARRAY_FILTRES_ECOLE) {
						ecole_nom = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					} else if (tableau_ordre_activation[j] == ARRAY_FILTRES_ENTREPRISE) {
						entreprise_nom = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					} else if (tableau_ordre_activation[j] == ARRAY_FILTRES_SECTEUR) {
						secteur_nom = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					} else if (tableau_ordre_activation[j] == ARRAY_FILTRES_PAYS) {
						pays_nom = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					} else if (tableau_ordre_activation[j] == ARRAY_FILTRES_VILLE
							&& HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).selectedIndex != 0) {
						// setTimeout(function() {
						 var filtre_ville = HTML('filtre_ville');
						 console.log(filtre_ville);
						 console.log(filtre_ville.options.length);
						 console.log(filtre_ville.selectedIndex);
						 console.log(filtre_ville.value);
						// }, 50);
						ville_nom = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					}
				}
			}

//			console.log(i);
//			console.log(tableau_ordre_activation[i]);
			console.log("anneePromotion_libelle : " + anneePromotion_libelle);
			console.log("ecole_nom : " + ecole_nom);
			console.log("entreprise_nom : " + entreprise_nom);
			console.log("secteur_nom : " + secteur_nom);
			console.log("pays_nom : " + pays_nom);
			console.log("ville_nom : " + ville_nom);
			console.log("--------------------------------");

			if (tableau_ordre_activation[i] == ARRAY_FILTRES_ANNEEPROMOTION) {
				miseAJourDuFiltreAnneePromotion(ecole_nom, entreprise_nom,
						secteur_nom, pays_nom, ville_nom);
			}
			if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])
					&& tableau_ordre_activation[i] == ARRAY_FILTRES_ECOLE) {
				miseAJourDuFiltreEcole(anneePromotion_libelle, secteur_nom,
						pays_nom, ville_nom);
			}
			if (HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID])
					&& tableau_ordre_activation[i] == ARRAY_FILTRES_ENTREPRISE) {
				miseAJourDuFiltreEntreprise(anneePromotion_libelle,
						secteur_nom, pays_nom, ville_nom);
			}
			if (tableau_ordre_activation[i] == ARRAY_FILTRES_SECTEUR) {
				miseAJourDuFiltreSecteur(anneePromotion_libelle, ecole_nom,
						entreprise_nom, pays_nom, ville_nom);
			}
			if (tableau_ordre_activation[i] == ARRAY_FILTRES_PAYS) {
				miseAJourDuFiltrePays(anneePromotion_libelle, ecole_nom,
						entreprise_nom, secteur_nom);
			}
			if (tableau_ordre_activation[i] == ARRAY_FILTRES_VILLE) {
				miseAJourDuFiltreVille(anneePromotion_libelle, ecole_nom,
						entreprise_nom, secteur_nom, pays_nom);
			}
		}

		// Si le filtre avait ete reinitialise alors on remet a -1 la valeur
		// d'ordre d'activation du filtre reinitialise et on decremente de 1 les
		// filtres actives apres lui
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
			arrayDuFiltreModifie[ARRAY_FILTRE_ORDRE_ACTIVATION] = -1;
		}

	}

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
							var valeurPrecedemmentSelectionnee;
							if (filtre_anneePromotion.selectedIndex != 0
									&& (ecole_nom || entreprise_nom
											|| secteur_nom || pays_nom || ville_nom)) {
								valeurPrecedemmentSelectionnee = filtre_anneePromotion.value;
							}
							filtre_anneePromotion.innerHTML = "";
							filtre_anneePromotion_option_par_defaut = document
									.createElement('option');
							filtre_anneePromotion_option_par_defaut.innerHTML = ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
							filtre_anneePromotion
									.appendChild(filtre_anneePromotion_option_par_defaut);

							// Ajout des nouveaux elements
							for ( var element in data) {
								if (data[element] == valeurPrecedemmentSelectionnee) {
									option_precedemment_selectionnee = document
											.createElement('option');
									option_precedemment_selectionnee.innerHTML = data[element];
									option_precedemment_selectionnee
											.setAttribute('selected',
													'selected');
									filtre_anneePromotion
											.appendChild(option_precedemment_selectionnee);
								} else {
									filtre_anneePromotion.options[filtre_anneePromotion.options.length] = new Option(
											data[element]);
								}
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
							var valeurPrecedemmentSelectionnee;
							if (filtre_ecole.selectedIndex != 0
									&& (anneePromotion_libelle || secteur_nom
											|| pays_nom || ville_nom)) {
								valeurPrecedemmentSelectionnee = filtre_ecole.value;
							}
							filtre_ecole.innerHTML = "";
							filtre_ecole_option_par_defaut = document
									.createElement('option');
							filtre_ecole_option_par_defaut.innerHTML = ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
							filtre_ecole
									.appendChild(filtre_ecole_option_par_defaut);

							// Ajout des nouveaux elements
							for ( var element in data) {
								if (data[element] == valeurPrecedemmentSelectionnee) {
									option_precedemment_selectionnee = document
											.createElement('option');
									option_precedemment_selectionnee.innerHTML = data[element];
									option_precedemment_selectionnee
											.setAttribute('selected',
													'selected');
									filtre_ecole
											.appendChild(option_precedemment_selectionnee);
								} else {
									filtre_ecole.options[filtre_ecole.options.length] = new Option(
											data[element]);
								}
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
							var valeurPrecedemmentSelectionnee;
							if (filtre_entreprise.selectedIndex != 0
									&& (anneePromotion_libelle || secteur_nom
											|| pays_nom || ville_nom)) {
								valeurPrecedemmentSelectionnee = filtre_entreprise.value;
							}
							filtre_entreprise.innerHTML = "";
							filtre_entreprise_option_par_defaut = document
									.createElement('option');
							filtre_entreprise_option_par_defaut.innerHTML = ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
							filtre_entreprise
									.appendChild(filtre_entreprise_option_par_defaut);

							// Ajout des nouveaux elements
							for ( var element in data) {
								if (data[element] == valeurPrecedemmentSelectionnee) {
									option_precedemment_selectionnee = document
											.createElement('option');
									option_precedemment_selectionnee.innerHTML = data[element];
									option_precedemment_selectionnee
											.setAttribute('selected',
													'selected');
									filtre_entreprise
											.appendChild(option_precedemment_selectionnee);
								} else {
									filtre_entreprise.options[filtre_entreprise.options.length] = new Option(
											data[element]);
								}
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
							var valeurPrecedemmentSelectionnee;
							if (filtre_secteur.selectedIndex != 0
									&& (anneePromotion_libelle || ecole_nom
											|| entreprise_nom || pays_nom || ville_nom)) {
								valeurPrecedemmentSelectionnee = filtre_secteur.value;
							}
							filtre_secteur.innerHTML = "";
							filtre_secteur_option_par_defaut = document
									.createElement('option');
							filtre_secteur_option_par_defaut.innerHTML = ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
							filtre_secteur
									.appendChild(filtre_secteur_option_par_defaut);

							// Ajout des nouveaux elements
							for ( var element in data) {
								if (data[element] == valeurPrecedemmentSelectionnee) {
									option_precedemment_selectionnee = document
											.createElement('option');
									option_precedemment_selectionnee.innerHTML = data[element];
									option_precedemment_selectionnee
											.setAttribute('selected',
													'selected');

									filtre_secteur
											.appendChild(option_precedemment_selectionnee);
								} else {
									filtre_secteur.options[filtre_secteur.options.length] = new Option(
											data[element]);
								}
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
							var valeurPrecedemmentSelectionnee;
							if (filtre_pays.selectedIndex != 0
									&& (anneePromotion_libelle || ecole_nom
											|| entreprise_nom || secteur_nom)) {
								valeurPrecedemmentSelectionnee = filtre_pays.value;
							}
							filtre_pays.innerHTML = "";
							filtre_pays_option_par_defaut = document
									.createElement('option');
							filtre_pays_option_par_defaut.innerHTML = ARRAY_FILTRE_PAYS[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
							filtre_pays
									.appendChild(filtre_pays_option_par_defaut);

							// Ajout des nouveaux elements
							for ( var element in data) {
								if (data[element] == valeurPrecedemmentSelectionnee) {
									option_precedemment_selectionnee = document
											.createElement('option');
									option_precedemment_selectionnee.innerHTML = data[element];
									option_precedemment_selectionnee
											.setAttribute('selected',
													'selected');
									filtre_pays
											.appendChild(option_precedemment_selectionnee);
								} else {
									filtre_pays.options[filtre_pays.options.length] = new Option(
											data[element]);
								}
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
									// le filtre
									var valeurPrecedemmentSelectionnee;
									if (filtre_ville.selectedIndex != 0
											&& (anneePromotion_libelle
													|| ecole_nom
													|| entreprise_nom
													|| secteur_nom || pays_nom)) {
										valeurPrecedemmentSelectionnee = filtre_ville.value;
									}
									filtre_ville.innerHTML = "";
									filtre_ville_option_par_defaut = document
											.createElement('option');
									filtre_ville_option_par_defaut.innerHTML = ARRAY_FILTRE_VILLE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
									// filtre_ville_option_par_defaut
									// .setAttribute('selected', 'true');
									filtre_ville
											.appendChild(filtre_ville_option_par_defaut);

									// Ajout des nouveaux elements
									for ( var element in data) {
										if (data[element] == valeurPrecedemmentSelectionnee) {
											option_precedemment_selectionnee = document
													.createElement('option');
											option_precedemment_selectionnee.innerHTML = data[element];
											option_precedemment_selectionnee
													.setAttribute('selected',
															'selected');
											filtre_ville
													.appendChild(option_precedemment_selectionnee);
										} else {
											filtre_ville.options[filtre_ville.options.length] = new Option(
													data[element]);
										}
									}

								}
							});
		}

	}
}

function suppression_filtreVille() {
	HTML('tableau_critere').removeChild(HTML('tr_ville'));
}
