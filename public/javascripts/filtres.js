/**
 * Cette fonction reinitialise un filtre en particulier (simulation de
 * modification du filtre avec selectedIndex=0)
 */
function reset(bouton_reset_ID) {
	var filtre_ID = "filtre_" + bouton_reset_ID.split("_")[2];
	HTML(filtre_ID).selectedIndex = 0;

	miseAJourDesFiltres(filtre_ID);
}

/** Cette fonction reinitialise tous les filtres */
function resetAll() {

	if (HTML(ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ID]).selectedIndex != 0
			|| HTML(ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ID]).selectedIndex != 0
			|| (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID]) && HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID]).selectedIndex != 0)
			|| (HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID]) && HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID]).selectedIndex != 0)
			|| HTML(ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ID]).selectedIndex != 0
			|| HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]).selectedIndex != 0
			|| (HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]) && HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]).selectedIndex != 0)) {

		for (ARRAY_FILTRES_ID in ARRAY_FILTRES) {
			var filtre = HTML((ARRAY_FILTRES[ARRAY_FILTRES_ID])[ARRAY_FILTRE_ID]);
			// Il faut indiquer au serveur quel est le filtre ignore entre Ecole
			// et Entreprise
			if (!((ARRAY_FILTRES_ID == ARRAY_FILTRES_ECOLE
					|| ARRAY_FILTRES_ID == ARRAY_FILTRES_ENTREPRISE || ARRAY_FILTRES_ID == ARRAY_FILTRES_VILLE) && !filtre)) {
				filtre.selectedIndex = 0;
			}
		}

		miseAJourDesFiltres();

		// Ne pas oublier de reinitialiser l'ordre d'activation des filtres !
		ORDRE_ACTIVATION_DERNIERE_VALEUR = 1;
		ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
		ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
		ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
		ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
		ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
		ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
		ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;

	}
}

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

	// Remise a jour de tous les filtres
	resetAll();

	// Alimentation du filtre
	if (filtre_ecoleOuEntreprise == 'filtre_entreprise') {
		initialisationFiltreEntreprise();
	} else {
		initialisationFiltreEcole();
	}

}

/**
 * Cette fonction retourne le tableau de constantes correspondant a l'id donne
 * en parametre
 */
function selectionneArrayFiltreSelonID(filtre_ID) {
	if (ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ID] == filtre_ID) {
		return ARRAY_FILTRE_CENTRALIEN;
	} else if (ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ID] == filtre_ID) {
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
 * Cette fonction retourne le numero du filtre dans le tableau de tableau de
 * constantes correspondant a l'id donne en parametre
 */
function selectionneNumeroFiltreSelonID(filtre_ID) {
	if (ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ID] == filtre_ID) {
		return ARRAY_FILTRES_CENTRALIEN;
	} else if (ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ID] == filtre_ID) {
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

/**
 * Cette fonction retourne les numeros des filtres (correspondant au tableau de
 * tableaux de constantes) classes dans un tableau par ordre d'activation
 */
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

/**
 * Cette fonction est appelee lorsque l'un des filtres a ete modifie. Les autres
 * filtres sont mis a jour en consequence apres requetage de la BDD. Si tous les
 * filtres doivent être modifies, il suffit de ne pas donner de parametre
 */
function miseAJourDesFiltres(filtre_ID) {
	// Si pas de parametre (et qu'au moins un des filtres doit etre reinitialise
	// !), alors tous les filtres sont reinitialise
	if (!filtre_ID) {
		var centralien_ID = null;
		var anneePromotion_ID = null;
		var ecole_ID = null;
		var entreprise_ID = null;
		var secteur_ID = null;
		var pays_ID = null;
		var ville_ID = null;

		// Il faut indiquer au serveur quel est le filtre ignore entre Ecole
		// et Entreprise
		if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
			entreprise_ID = ECOLE_OU_ENTREPRISE_INACTIF;
		} else {
			ecole_ID = ECOLE_OU_ENTREPRISE_INACTIF;
		}

		miseAJourDuFiltreCentralien(anneePromotion_ID, ecole_ID, entreprise_ID,
				secteur_ID, pays_ID, ville_ID);
		miseAJourDuFiltreAnneePromotion(centralien_ID, ecole_ID, entreprise_ID,
				secteur_ID, pays_ID, ville_ID);
		if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
			miseAJourDuFiltreEcole(centralien_ID, anneePromotion_ID,
					secteur_ID, pays_ID, ville_ID);
		} else {
			miseAJourDuFiltreEntreprise(centralien_ID, anneePromotion_ID,
					secteur_ID, pays_ID, ville_ID);
		}
		miseAJourDuFiltreSecteur(centralien_ID, anneePromotion_ID, ecole_ID,
				entreprise_ID, pays_ID, ville_ID);
		miseAJourDuFiltrePays(centralien_ID, anneePromotion_ID, ecole_ID,
				entreprise_ID, secteur_ID);
		miseAJourDuFiltreVille(centralien_ID, anneePromotion_ID, ecole_ID,
				entreprise_ID, secteur_ID, pays_ID);

	} else {
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
			var centralien_ID;
			var anneePromotion_ID;
			var ecole_ID;
			var entreprise_ID;
			var secteur_ID;
			var pays_ID;
			var ville_ID;

			// Il faut indiquer au serveur quel est le filtre ignore entre Ecole
			// et Entreprise
			if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
				entreprise_ID = ECOLE_OU_ENTREPRISE_INACTIF;
			} else {
				ecole_ID = ECOLE_OU_ENTREPRISE_INACTIF;
			}

			for ( var j = 0; j < i; j++) {
				if (ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ORDRE_ACTIVATION] != -1
						&& HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID])
						&& HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).selectedIndex != 0) {
					if (tableau_ordre_activation[j] == ARRAY_FILTRES_CENTRALIEN) {
						centralien_ID = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					} else if (tableau_ordre_activation[j] == ARRAY_FILTRES_ANNEEPROMOTION) {
						anneePromotion_ID = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					} else if (tableau_ordre_activation[j] == ARRAY_FILTRES_ECOLE) {
						ecole_ID = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					} else if (tableau_ordre_activation[j] == ARRAY_FILTRES_ENTREPRISE) {
						entreprise_ID = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					} else if (tableau_ordre_activation[j] == ARRAY_FILTRES_SECTEUR) {
						secteur_ID = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					} else if (tableau_ordre_activation[j] == ARRAY_FILTRES_PAYS) {
						pays_ID = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					} else if (tableau_ordre_activation[j] == ARRAY_FILTRES_VILLE) {
						ville_ID = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					}
				}
			}

			if (tableau_ordre_activation[i] == ARRAY_FILTRES_CENTRALIEN) {
				miseAJourDuFiltreCentralien(anneePromotion_ID, ecole_ID,
						entreprise_ID, secteur_ID, pays_ID, ville_ID);
			}
			if (tableau_ordre_activation[i] == ARRAY_FILTRES_ANNEEPROMOTION) {
				miseAJourDuFiltreAnneePromotion(centralien_ID, ecole_ID,
						entreprise_ID, secteur_ID, pays_ID, ville_ID);
			}
			if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])
					&& tableau_ordre_activation[i] == ARRAY_FILTRES_ECOLE) {
				miseAJourDuFiltreEcole(centralien_ID, anneePromotion_ID,
						secteur_ID, pays_ID, ville_ID);
			}
			if (HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID])
					&& tableau_ordre_activation[i] == ARRAY_FILTRES_ENTREPRISE) {
				miseAJourDuFiltreEntreprise(centralien_ID, anneePromotion_ID,
						secteur_ID, pays_ID, ville_ID);
			}
			if (tableau_ordre_activation[i] == ARRAY_FILTRES_SECTEUR) {
				miseAJourDuFiltreSecteur(centralien_ID, anneePromotion_ID,
						ecole_ID, entreprise_ID, pays_ID, ville_ID);
			}
			if (tableau_ordre_activation[i] == ARRAY_FILTRES_PAYS) {
				miseAJourDuFiltrePays(centralien_ID, anneePromotion_ID,
						ecole_ID, entreprise_ID, secteur_ID);
			}
			if (tableau_ordre_activation[i] == ARRAY_FILTRES_VILLE) {
				miseAJourDuFiltreVille(centralien_ID, anneePromotion_ID,
						ecole_ID, entreprise_ID, secteur_ID, pays_ID);
			}
		}

		// Si le filtre avait ete reinitialise alors on remet a -1 la valeur
		// d'ordre d'activation du filtre reinitialise et on decremente de 1 les
		// filtres actives apres lui
		if (HTML(filtre_ID).selectedIndex == '0') {
			// TODO : mettre une boucle for
			if (ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ORDRE_ACTIVATION] > ordreActivationDuFiltreReinitialise) {
				ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ORDRE_ACTIVATION]--;
			}
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

/**
 * Cette fonction permet d'alimenter le filtre donne en parametre avec les
 * donnees du serveur concernant ce filtre
 */
function miseAJourDuFiltre_AJAXSuccess(data, ARRAY_FILTRE) {
	var filtre = HTML(ARRAY_FILTRE[ARRAY_FILTRE_ID]);

	// Suppression des elements existants dans le filtre
	var valeurPrecedemmentSelectionnee;

	if (filtre.selectedIndex != 0) {
		valeurPrecedemmentSelectionnee = filtre.value;
	}
	filtre.innerHTML = "";
	filtre_option_par_defaut = document.createElement('option');
	filtre_option_par_defaut.innerHTML = ARRAY_FILTRE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
	filtre.appendChild(filtre_option_par_defaut);

	// Ajout des nouveaux elements
	for ( var element in data) {
		if (data[element][0] == valeurPrecedemmentSelectionnee) {
			option_precedemment_selectionnee = document.createElement('option');
			option_precedemment_selectionnee.value = data[element][0];
			option_precedemment_selectionnee.text = data[element][1];
			option_precedemment_selectionnee.setAttribute('selected',
					'selected');
			filtre.appendChild(option_precedemment_selectionnee);
		} else {
			filtre.options[filtre.options.length] = new Option(
					data[element][1], data[element][0]);
		}
	}
}

/**
 * Met a jour le filtre Centralien
 */
function miseAJourDuFiltreCentralien(anneePromotion_ID, ecole_ID,
		entreprise_ID, secteur_ID, pays_ID, ville_ID) {
	jsRoutes.controllers.ServiceCentralien
			.AJAX_listeDesCentraliensSelonCriteres(
					anneePromotion_ID ? anneePromotion_ID : "",
					ecole_ID ? ecole_ID : "",
					entreprise_ID ? entreprise_ID : "",
					secteur_ID ? secteur_ID : "", pays_ID ? pays_ID : "",
					ville_ID ? ville_ID : "").ajax(
					{
						async : false,
						success : function(data, textStatus, jqXHR) {
							miseAJourDuFiltre_AJAXSuccess(data,
									ARRAY_FILTRE_CENTRALIEN);
						}
					});
}

/**
 * Met a jour le filtre AnneePromotion
 */
function miseAJourDuFiltreAnneePromotion(centralien_ID, ecole_ID,
		entreprise_ID, secteur_ID, pays_ID, ville_ID) {

	jsRoutes.controllers.ServiceAnneePromotion
			.AJAX_listeDesAnneesPromotionSelonCriteres(
					centralien_ID ? centralien_ID : "",
					ecole_ID ? ecole_ID : "",
					entreprise_ID ? entreprise_ID : "",
					secteur_ID ? secteur_ID : "", pays_ID ? pays_ID : "",
					ville_ID ? ville_ID : "").ajax(
					{
						async : false,
						success : function(data, textStatus, jqXHR) {
							miseAJourDuFiltre_AJAXSuccess(data,
									ARRAY_FILTRE_ANNEEPROMOTION);
						}
					});
}

/**
 * Met a jour le filtre Ecole
 */
function miseAJourDuFiltreEcole(centralien_ID, anneePromotion_ID, secteur_ID,
		pays_ID, ville_ID) {
	jsRoutes.controllers.ServiceEcole.AJAX_listeDesEcolesSelonCriteres(
			centralien_ID ? centralien_ID : "",
			anneePromotion_ID ? anneePromotion_ID : "",
			secteur_ID ? secteur_ID : "", pays_ID ? pays_ID : "",
			ville_ID ? ville_ID : "").ajax({
		async : false,
		success : function(data, textStatus, jqXHR) {
			miseAJourDuFiltre_AJAXSuccess(data, ARRAY_FILTRE_ECOLE);
		}
	});
}

/**
 * Met a jour le filtre Entreprise
 */
function miseAJourDuFiltreEntreprise(centralien_ID, anneePromotion_ID,
		secteur_ID, pays_ID, ville_ID) {
	jsRoutes.controllers.ServiceEntreprise
			.AJAX_listeDesEntreprisesSelonCriteres(
					centralien_ID ? centralien_ID : "",
					anneePromotion_ID ? anneePromotion_ID : "",
					secteur_ID ? secteur_ID : "", pays_ID ? pays_ID : "",
					ville_ID ? ville_ID : "").ajax(
					{
						async : false,
						success : function(data, textStatus, jqXHR) {
							miseAJourDuFiltre_AJAXSuccess(data,
									ARRAY_FILTRE_ENTREPRISE);
						}
					});
}

/**
 * Met a jour le filtre Secteur
 */
function miseAJourDuFiltreSecteur(centralien_ID, anneePromotion_ID, ecole_ID,
		entreprise_ID, pays_ID, ville_ID) {
	jsRoutes.controllers.ServiceSecteur.AJAX_listeDesSecteursSelonCriteres(
			centralien_ID ? centralien_ID : "",
			anneePromotion_ID ? anneePromotion_ID : "",
			ecole_ID ? ecole_ID : "", entreprise_ID ? entreprise_ID : "",
			pays_ID ? pays_ID : "", ville_ID ? ville_ID : "").ajax({
		async : false,
		success : function(data, textStatus, jqXHR) {
			miseAJourDuFiltre_AJAXSuccess(data, ARRAY_FILTRE_SECTEUR);
		}
	});
}

/**
 * Met a jour le filtre Pays
 */
function miseAJourDuFiltrePays(centralien_ID, anneePromotion_ID, ecole_ID,
		entreprise_ID, secteur_ID) {
	jsRoutes.controllers.ServicePays.AJAX_listeDesPaysSelonCriteres(
			centralien_ID ? centralien_ID : "",
			anneePromotion_ID ? anneePromotion_ID : "",
			ecole_ID ? ecole_ID : "", entreprise_ID ? entreprise_ID : "",
			secteur_ID ? secteur_ID : "").ajax({
		async : false,
		success : function(data, textStatus, jqXHR) {
			miseAJourDuFiltre_AJAXSuccess(data, ARRAY_FILTRE_PAYS);
		}
	});

}

/**
 * Met a jour le filtre Ville Si le filtre pays n'etait pas renseigne alors le
 * filtre ville apparait. La carte zoome sur le pays concerne. Les marqueurs des
 * villes où des centraliens sont presents apparaissent
 */
function miseAJourDuFiltreVille(centralien_ID, anneePromotion_ID, ecole_ID,
		entreprise_ID, secteur_ID, pays_ID) {
	if (HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]).selectedIndex != 0) {
		// Si le filtre ville n'existe pas, il est cree
		if (!HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID])) {
			creation_filtreVille(pays_ID);
		}
		// Dans tous les cas on l'alimente
		jsRoutes.controllers.ServiceVille.AJAX_listeDesVillesSelonCriteres(
				centralien_ID ? centralien_ID : "",
				anneePromotion_ID ? anneePromotion_ID : "",
				ecole_ID ? ecole_ID : "", entreprise_ID ? entreprise_ID : "",
				secteur_ID ? secteur_ID : "", pays_ID ? pays_ID : "").ajax({
			async : false,
			success : function(data, textStatus, jqXHR) {
				miseAJourDuFiltre_AJAXSuccess(data, ARRAY_FILTRE_VILLE);
			}
		});
	} else if (HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID])) {
		// Si le filtre pays n'est pas selectionne et que le filtre ville
		// existe, ce dernier est supprime
		suppression_filtreVille();
	}
}

/**
 * Cette fonction supprime le filtre ville
 */
function suppression_filtreVille() {
	HTML('tableau_critere').removeChild(HTML('tr_ville'));
	ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
}
