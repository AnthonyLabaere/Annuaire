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

/** Cette fonction reinitialise tous les filtres */
function resetAll() {
	miseAJourDesFiltres();
}

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
		if (HTML(ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ID]).selectedIndex != 0
				|| HTML(ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ID]).selectedIndex != 0
				|| (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID]) && HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID]).selectedIndex != 0)
				|| (HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID]) && HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID]).selectedIndex != 0)
				|| HTML(ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ID]).selectedIndex != 0
				|| HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]).selectedIndex != 0
				|| (HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]) && HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]).selectedIndex != 0)) {
			var centralien_ID = null;
			var anneePromotion_ID = null;
			var ecole_ID = null;
			var entreprise_ID = null;
			var secteur_nom = null;
			var pays_nom = null;
			var ville_nom = null;

			// Il faut indiquer au serveur quel est le filtre ignore entre Ecole
			// et Entreprise
			if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
				entreprise_ID = ECOLE_OU_ENTREPRISE_INACTIF;
			} else {
				ecole_ID = ECOLE_OU_ENTREPRISE_INACTIF;
			}

			miseAJourDuFiltreCentralien(anneePromotion_ID, ecole_ID,
					entreprise_ID, secteur_nom, pays_nom, ville_nom);
			miseAJourDuFiltreAnneePromotion(centralien_ID, ecole_ID,
					entreprise_ID, secteur_nom, pays_nom, ville_nom);
			if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
				miseAJourDuFiltreEcole(centralien_ID, anneePromotion_ID,
						secteur_nom, pays_nom, ville_nom);
			} else {
				miseAJourDuFiltreEntreprise(centralien_ID, anneePromotion_ID,
						secteur_nom, pays_nom, ville_nom);
			}
			miseAJourDuFiltreSecteur(centralien_ID, anneePromotion_ID,
					ecole_ID, entreprise_ID, pays_nom, ville_nom);
			miseAJourDuFiltrePays(centralien_ID, anneePromotion_ID, ecole_ID,
					entreprise_ID, secteur_nom);
			// TODO : supprimer la ligne d'apres ?
			HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]).selectedIndex = 0;
			miseAJourDuFiltreVille(centralien_ID, anneePromotion_ID, ecole_ID,
					entreprise_ID, secteur_nom, pays_nom);

			// Ne pas oublier de reinitialiser l'ordre d'activation des filtres
			// !
			ORDRE_ACTIVATION_DERNIERE_VALEUR = 1;
			ARRAY_FILTRE_CENTRALIEN[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
			ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
			ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
			ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
			ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
			ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
			ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
		}

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
			var secteur_nom;
			var pays_nom;
			var ville_nom;

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
						secteur_nom = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					} else if (tableau_ordre_activation[j] == ARRAY_FILTRES_PAYS) {
						pays_nom = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					} else if (tableau_ordre_activation[j] == ARRAY_FILTRES_VILLE) {
						ville_nom = HTML(ARRAY_FILTRES[tableau_ordre_activation[j]][ARRAY_FILTRE_ID]).value;
					}
				}
			}

			if (tableau_ordre_activation[i] == ARRAY_FILTRES_CENTRALIEN) {
				miseAJourDuFiltreCentralien(anneePromotion_ID, ecole_ID,
						entreprise_ID, secteur_nom, pays_nom, ville_nom);
			}
			if (tableau_ordre_activation[i] == ARRAY_FILTRES_ANNEEPROMOTION) {
				miseAJourDuFiltreAnneePromotion(centralien_ID, ecole_ID,
						entreprise_ID, secteur_nom, pays_nom, ville_nom);
			}
			if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])
					&& tableau_ordre_activation[i] == ARRAY_FILTRES_ECOLE) {
				miseAJourDuFiltreEcole(centralien_ID, anneePromotion_ID,
						secteur_nom, pays_nom, ville_nom);
			}
			if (HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID])
					&& tableau_ordre_activation[i] == ARRAY_FILTRES_ENTREPRISE) {
				miseAJourDuFiltreEntreprise(centralien_ID, anneePromotion_ID,
						secteur_nom, pays_nom, ville_nom);
			}
			if (tableau_ordre_activation[i] == ARRAY_FILTRES_SECTEUR) {
				miseAJourDuFiltreSecteur(centralien_ID, anneePromotion_ID,
						ecole_ID, entreprise_ID, pays_nom, ville_nom);
			}
			if (tableau_ordre_activation[i] == ARRAY_FILTRES_PAYS) {
				miseAJourDuFiltrePays(centralien_ID, anneePromotion_ID,
						ecole_ID, entreprise_ID, secteur_nom);
			}
			if (tableau_ordre_activation[i] == ARRAY_FILTRES_VILLE) {
				miseAJourDuFiltreVille(centralien_ID, anneePromotion_ID,
						ecole_ID, entreprise_ID, secteur_nom, pays_nom);
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
function miseAJourDuFiltre_AJAXSuccess_avecID(data,
		booleenValeurPrecedemmentSelectionnee, ARRAY_FILTRE) {
	var filtre = HTML(ARRAY_FILTRE[ARRAY_FILTRE_ID]);

	// Suppression des elements existants dans le filtre
	var valeurPrecedemmentSelectionnee;
	if (booleenValeurPrecedemmentSelectionnee) {
		valeurPrecedemmentSelectionnee = filtre.value;
	}
	filtre.innerHTML = "";
	filtre_option_par_defaut = document.createElement('option');
	filtre_option_par_defaut.innerHTML = ARRAY_FILTRE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
	filtre.appendChild(filtre_option_par_defaut);

	// Ajout des nouveaux elements
	for ( var element in data) {
		if (data[element] == valeurPrecedemmentSelectionnee) {
			option_precedemment_selectionnee = document.createElement('option');
			option_precedemment_selectionnee.innerHTML = data[element];
			option_precedemment_selectionnee.setAttribute('selected',
					'selected');
			filtre.appendChild(option_precedemment_selectionnee);
		} else {
			filtre.options[filtre.options.length] = new Option(
					data[element][1], data[element][0]);
		}
	}
}

function miseAJourDuFiltre_AJAXSuccess_sansID(data,
		booleenValeurPrecedemmentSelectionnee, ARRAY_FILTRE) {
	var filtre = HTML(ARRAY_FILTRE[ARRAY_FILTRE_ID]);

	// Suppression des elements existants dans le filtre
	var valeurPrecedemmentSelectionnee;
	if (booleenValeurPrecedemmentSelectionnee) {
		valeurPrecedemmentSelectionnee = filtre.value;
	}
	filtre.innerHTML = "";
	filtre_option_par_defaut = document.createElement('option');
	filtre_option_par_defaut.innerHTML = ARRAY_FILTRE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
	filtre.appendChild(filtre_option_par_defaut);

	// Ajout des nouveaux elements
	for ( var element in data) {
		if (data[element] == valeurPrecedemmentSelectionnee) {
			option_precedemment_selectionnee = document.createElement('option');
			option_precedemment_selectionnee.innerHTML = data[element];
			option_precedemment_selectionnee.setAttribute('selected',
					'selected');
			filtre.appendChild(option_precedemment_selectionnee);
		} else {
			filtre.options[filtre.options.length] = new Option(data[element]);
		}
	}
}

function miseAJourDuFiltreCentralien(anneePromotion_ID, ecole_ID,
		entreprise_ID, secteur_nom, pays_nom, ville_nom) {
	jsRoutes.controllers.ServiceCentralien
			.AJAX_listeDesCentraliensSelonCriteres(
					anneePromotion_ID ? anneePromotion_ID : "",
					ecole_ID ? ecole_ID : "",
					entreprise_ID ? entreprise_ID : "",
					secteur_nom ? secteur_nom : "", pays_nom ? pays_nom : "",
					ville_nom ? ville_nom : "")
			.ajax(
					{
						async : false,
						success : function(data, textStatus, jqXHR) {
							booleenValeurPrecedemmentSelectionnee = (filtre_centralien.selectedIndex != 0 && (anneePromotion_ID
									|| ecole_ID
									&& ecole_ID != ECOLE_OU_ENTREPRISE_INACTIF
									|| entreprise_ID
									&& entreprise_ID != ECOLE_OU_ENTREPRISE_INACTIF
									|| secteur_nom || pays_nom || ville_nom));

							miseAJourDuFiltre_AJAXSuccess_avecID(data,
									booleenValeurPrecedemmentSelectionnee,
									ARRAY_FILTRE_CENTRALIEN);
						}
					});
}

function miseAJourDuFiltreAnneePromotion(centralien_ID, ecole_ID,
		entreprise_ID, secteur_nom, pays_nom, ville_nom) {

	jsRoutes.controllers.ServiceAnneePromotion
			.AJAX_listeDesAnneesPromotionSelonCriteres(
					centralien_ID ? centralien_ID : "",
					ecole_ID ? ecole_ID : "",
					entreprise_ID ? entreprise_ID : "",
					secteur_nom ? secteur_nom : "", pays_nom ? pays_nom : "",
					ville_nom ? ville_nom : "")
			.ajax(
					{
						async : false,
						success : function(data, textStatus, jqXHR) {
							var booleenValeurPrecedemmentSelectionnee = (filtre_anneePromotion.selectedIndex != 0 && (ecole_ID
									&& ecole_ID != ECOLE_OU_ENTREPRISE_INACTIF
									|| entreprise_ID
									&& entreprise_ID != ECOLE_OU_ENTREPRISE_INACTIF
									|| secteur_nom || pays_nom || ville_nom));

							miseAJourDuFiltre_AJAXSuccess_avecID(data,
									booleenValeurPrecedemmentSelectionnee,
									ARRAY_FILTRE_ANNEEPROMOTION);
						}
					});
}

function miseAJourDuFiltreEcole(centralien_ID, anneePromotion_ID, secteur_nom,
		pays_nom, ville_nom) {
	jsRoutes.controllers.ServiceEcole
			.AJAX_listeDesEcolesSelonCriteres(
					centralien_ID ? centralien_ID : "",
					anneePromotion_ID ? anneePromotion_ID : "",
					secteur_nom ? secteur_nom : "", pays_nom ? pays_nom : "",
					ville_nom ? ville_nom : "")
			.ajax(
					{
						async : false,
						success : function(data, textStatus, jqXHR) {
							var booleenValeurPrecedemmentSelectionnee = (filtre_ecole.selectedIndex != 0 && (anneePromotion_ID
									|| secteur_nom || pays_nom || ville_nom));

							miseAJourDuFiltre_AJAXSuccess_avecID(data,
									booleenValeurPrecedemmentSelectionnee,
									ARRAY_FILTRE_ECOLE);
						}
					});
}

function miseAJourDuFiltreEntreprise(centralien_ID, anneePromotion_ID,
		secteur_nom, pays_nom, ville_nom) {
	jsRoutes.controllers.ServiceEntreprise
			.AJAX_listeDesEntreprisesSelonCriteres(
					centralien_ID ? centralien_ID : "",
					anneePromotion_ID ? anneePromotion_ID : "",
					secteur_nom ? secteur_nom : "", pays_nom ? pays_nom : "",
					ville_nom ? ville_nom : "")
			.ajax(
					{
						async : false,
						success : function(data, textStatus, jqXHR) {
							var booleenValeurPrecedemmentSelectionnee = (filtre_entreprise.selectedIndex != 0 && (anneePromotion_ID
									|| secteur_nom || pays_nom || ville_nom));

							miseAJourDuFiltre_AJAXSuccess_avecID(data,
									booleenValeurPrecedemmentSelectionnee,
									ARRAY_FILTRE_ENTREPRISE);
						}
					});
}

function miseAJourDuFiltreSecteur(centralien_ID, anneePromotion_ID, ecole_ID,
		entreprise_ID, pays_nom, ville_nom) {
	jsRoutes.controllers.ServiceSecteur
			.AJAX_listeDesSecteursSelonCriteres(
					centralien_ID ? centralien_ID : "",
					anneePromotion_ID ? anneePromotion_ID : "",
					ecole_ID ? ecole_ID : "",
					entreprise_ID ? entreprise_ID : "",
					pays_nom ? pays_nom : "", ville_nom ? ville_nom : "")
			.ajax(
					{
						async : false,
						success : function(data, textStatus, jqXHR) {
							var booleenValeurPrecedemmentSelectionnee = (filtre_secteur.selectedIndex != 0 && (anneePromotion_ID
									|| ecole_ID
									&& ecole_ID != ECOLE_OU_ENTREPRISE_INACTIF
									|| entreprise_ID
									&& entreprise_ID != ECOLE_OU_ENTREPRISE_INACTIF
									|| pays_nom || ville_nom));

							miseAJourDuFiltre_AJAXSuccess_sansID(data,
									booleenValeurPrecedemmentSelectionnee,
									ARRAY_FILTRE_SECTEUR);
						}
					});
}

function miseAJourDuFiltrePays(centralien_ID, anneePromotion_ID, ecole_ID,
		entreprise_ID, secteur_nom) {
	jsRoutes.controllers.ServicePays
			.AJAX_listeDesPaysSelonCriteres(centralien_ID ? centralien_ID : "",
					anneePromotion_ID ? anneePromotion_ID : "",
					ecole_ID ? ecole_ID : "",
					entreprise_ID ? entreprise_ID : "",
					secteur_nom ? secteur_nom : "")
			.ajax(
					{
						async : false,
						success : function(data, textStatus, jqXHR) {
							var booleenValeurPrecedemmentSelectionnee = (filtre_pays.selectedIndex != 0 && (anneePromotion_ID
									|| ecole_ID
									&& ecole_ID != ECOLE_OU_ENTREPRISE_INACTIF
									|| entreprise_ID
									&& entreprise_ID != ECOLE_OU_ENTREPRISE_INACTIF || secteur_nom));

							miseAJourDuFiltre_AJAXSuccess_sansID(data,
									booleenValeurPrecedemmentSelectionnee,
									ARRAY_FILTRE_PAYS);
						}
					});

}

/**
 * Si le filtre pays n'etait pas renseigne alors le filtre ville apparait. La
 * carte zoome sur le pays concerne. Les marqueurs des villes où des centraliens
 * sont presents apparaissent
 */
function miseAJourDuFiltreVille(centralien_ID, anneePromotion_ID, ecole_ID,
		entreprise_ID, secteur_nom, pays_nom) {
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
			ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ORDRE_ACTIVATION] = ORDRE_ACTIVATION_PAR_DEFAUT;
		} else {
			jsRoutes.controllers.ServiceVille
					.AJAX_listeDesVillesSelonCriteres(
							centralien_ID ? centralien_ID : "",
							anneePromotion_ID ? anneePromotion_ID : "",
							ecole_ID ? ecole_ID : "",
							entreprise_ID ? entreprise_ID : "",
							secteur_nom ? secteur_nom : "",
							pays_nom ? pays_nom : "")
					.ajax(
							{
								async : false,
								success : function(data, textStatus, jqXHR) {
									var booleenValeurPrecedemmentSelectionnee = (filtre_ville.selectedIndex != 0 && (anneePromotion_ID
											|| ecole_ID
											&& ecole_ID != ECOLE_OU_ENTREPRISE_INACTIF
											|| entreprise_ID
											&& entreprise_ID != ECOLE_OU_ENTREPRISE_INACTIF
											|| secteur_nom || pays_nom));

									miseAJourDuFiltre_AJAXSuccess_sansID(
											data,
											booleenValeurPrecedemmentSelectionnee,
											ARRAY_FILTRE_VILLE);
								}
							});
		}
	}
}

function suppression_filtreVille() {
	HTML('tableau_critere').removeChild(HTML('tr_ville'));
}
