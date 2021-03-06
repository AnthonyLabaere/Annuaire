/****************************************************************************

Copyright 2014 Anthony Labaere

Contributeurs : 
François Neber francois.neber@centraliens-nantes.net
Malik Olivier Boussejra malik.boussejra@centraliens-nantes.net
Anthony Labaere anthony.labaere@centraliens-nantes.net

Ce logiciel est un programme informatique ayant pour but de faciliter 
les contacts entre étudiants et diplômés de l'École Centrale Nantes 
à l'étranger comme en France.

Ce logiciel est régi par la licence CeCILL soumise au droit français et
respectant les principes de diffusion des logiciels libres. Vous pouvez
utiliser, modifier et/ou redistribuer ce programme sous les conditions
de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA 
sur le site "http://www.cecill.info".

En contrepartie de l'accessibilité au code source et des droits de copie,
de modification et de redistribution accordés par cette licence, il n'est
offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
seule une responsabilité restreinte pèse sur l'auteur du programme,  le
titulaire des droits patrimoniaux et les concédants successifs.

A cet égard  l'attention de l'utilisateur est attirée sur les risques
associés au chargement,  à l'utilisation,  à la modification et/ou au
développement et à la reproduction du logiciel par l'utilisateur étant 
donné sa spécificité de logiciel libre, qui peut le rendre complexe à 
manipuler et qui le réserve donc à des développeurs et des professionnels
avertis possédant  des  connaissances  informatiques approfondies.  Les
utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
logiciel à leurs besoins dans des conditions permettant d'assurer la
sécurité de leurs systèmes et ou de leurs données et, plus généralement, 
à l'utiliser et l'exploiter dans les mêmes conditions de sécurité. 

Le fait que vous puissiez accéder à cet en-tête signifie que vous avez 
pris connaissance de la licence CeCILL et que vous en avez accepté les
termes.

******************************************************************************/

/**
 * -----------------------------------------------------------------------------
 * Ce fichier contient les fonctions portant sur les filtres (sauf leurs initialisations)
 * -----------------------------------------------------------------------------
 */

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

	// On recree le filtre ecole ou le filtre entreprise
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

	filtre.setAttribute('onChange', 'action_modificationFiltre(this.id)');
	filtre.appendChild(filtre_option_par_defaut);
	HTML('td_ecoleOuEntreprise').appendChild(filtre);

	// On recree le bouton reset d'ecole ou d'entreprise
	var bouton_reset = document.createElement('img');
	bouton_reset.setAttribute('src', '/assets/images/reset.png');
	if (filtre_ecoleOuEntreprise == 'filtre_entreprise') {
		bouton_reset.setAttribute('id', 'bouton_reset_entreprise');
		bouton_reset.setAttribute('alt', 'bouton_reset_entreprise');
		bouton_reset.setAttribute('title',
				'R&eacute;initialisation du champ Entreprise');
	} else {
		bouton_reset.setAttribute('id', 'bouton_reset_ecole');
		bouton_reset.setAttribute('alt', 'bouton_reset_ecole');
		bouton_reset.setAttribute('title',
				'R&eacute;initialisation du champ Ecole');
	}
	bouton_reset.setAttribute('onClick', 'action_reset(this.id)');
	bouton_reset.setAttribute('class', 'bouton_reset');

	var td_ecoleOuEntreprise_reset = HTML('td_ecoleOuEntreprise_reset');
	td_ecoleOuEntreprise_reset.innerHTML = "";
	td_ecoleOuEntreprise_reset.appendChild(bouton_reset);

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
		var ordreActivationDuFiltreReinitialise = ARRAY_FILTRES.length + 1;
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
	var filtre_ID = ARRAY_FILTRE[ARRAY_FILTRE_ID];
	var filtre = HTML(filtre_ID);

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
			var option = document.createElement('option');
			option.value = data[element][0];
			option.text = data[element][1];
			option.id = filtre_ID + "_" + data[element][0];
			// Si le filtre est pays ou ville alors on y ajoute les coordonnes
			// GPS
			if (filtre_ID == ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]
					|| filtre_ID == ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]) {
				option.setAttribute('latitude', data[element][2]);
				option.setAttribute('longitude', data[element][3]);
				option.setAttribute('zoom', data[element][4]);
			}
			filtre.appendChild(option);
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
					HTML(CHECKBOX_HISTORIQUE_ID).checked,
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
			HTML(CHECKBOX_HISTORIQUE_ID).checked,
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
					HTML(CHECKBOX_HISTORIQUE_ID).checked,
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
