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
 * Ce fichier contient les fonctions portant sur l'initialisation des filtres
 * -----------------------------------------------------------------------------
 */

/**
 * Cette fonction appelle les fonctions d'initialisation des filtres et donc les
 * initialise
 */
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
function initialisationFiltre_AJAX_Success(data, ARRAY_FILTRE) {
	var filtre_ID = ARRAY_FILTRE[ARRAY_FILTRE_ID];
	var filtre = HTML(filtre_ID);
	filtre.innerHTML = '';

	filtre_option_par_defaut = document.createElement('option');
	filtre_option_par_defaut.innerHTML = ARRAY_FILTRE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
	filtre.appendChild(filtre_option_par_defaut);

	for ( var element in data) {
		var option = document.createElement('option');
		option.value = data[element][0];
		option.text = data[element][1];
		option.id = filtre_ID + "_" + data[element][0];
		// Si le filtre est pays ou ville alors on y ajoute les coordonnes GPS
		if (filtre_ID == ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]
				|| filtre_ID == ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]) {
			option.setAttribute('latitude', data[element][2]);
			option.setAttribute('longitude', data[element][3]);
			option.setAttribute('zoom', data[element][4]);
		}
		filtre.appendChild(option);
	}
}

/** Alimentation du filtre des annees de promotion */
function initialisationFiltreCentralien() {
	jsRoutes.controllers.ServiceCentralien.AJAX_listeDesCentraliens().ajax({
		async : false,
		success : function(data, textStatus, jqXHR) {
			initialisationFiltre_AJAX_Success(data, ARRAY_FILTRE_CENTRALIEN);
		}
	});
}

/** Alimentation du filtre des annees de promotion */
function initialisationFiltreAnneePromotion() {
	jsRoutes.controllers.ServiceAnneePromotion.AJAX_listeDesAnneesPromotion()
			.ajax(
					{
						async : false,
						success : function(data, textStatus, jqXHR) {
							initialisationFiltre_AJAX_Success(data,
									ARRAY_FILTRE_ANNEEPROMOTION);
						}
					});
}

/** Alimentation du filtre des ecoles (si ce filtre existe) */
function initialisationFiltreEcole() {
	if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
		jsRoutes.controllers.ServiceEcole.AJAX_listeDesEcoles().ajax({
			async : false,
			success : function(data, textStatus, jqXHR) {
				initialisationFiltre_AJAX_Success(data, ARRAY_FILTRE_ECOLE);
			}
		});
	}
}

/** Alimentation du filtre des entreprises (si ce filtre existe) */
function initialisationFiltreEntreprise() {
	if (HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID])) {
		jsRoutes.controllers.ServiceEntreprise.AJAX_listeDesEntreprises().ajax(
				{
					async : false,
					success : function(data, textStatus, jqXHR) {
						initialisationFiltre_AJAX_Success(data,
								ARRAY_FILTRE_ENTREPRISE);
					}
				});
	}
}

/** Alimentation du filtre des secteurs */
function initialisationFiltreSecteur() {
	jsRoutes.controllers.ServiceSecteur.AJAX_listeDesSecteurs().ajax({
		async : false,
		success : function(data, textStatus, jqXHR) {
			initialisationFiltre_AJAX_Success(data, ARRAY_FILTRE_SECTEUR);
		}
	});
}

/** Alimentation du filtre des pays */
function initialisationFiltrePays() {
	jsRoutes.controllers.ServicePays.AJAX_listeDesPays().ajax({
		async : false,
		success : function(data, textStatus, jqXHR) {
			initialisationFiltre_AJAX_Success(data, ARRAY_FILTRE_PAYS);
		}
	});
}

/** Creation du filtre des villes */
function creation_filtreVille(pays_nom) {
	var villeTd1 = document.createElement('td');
	villeTd1.innerHTML = 'Ville';

	var filtre_ville = document.createElement('select');
	filtre_ville.setAttribute('name', 'Ville');
	filtre_ville.setAttribute('id', ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]);
	filtre_ville.setAttribute('onChange', 'action_modificationFiltre(this.id)');

	var villeTd2 = document.createElement('td');
	villeTd2.appendChild(filtre_ville);

	var bouton_reset = document.createElement('img');
	bouton_reset.setAttribute('src', '/assets/images/reset.png');
	bouton_reset.setAttribute('id', 'bouton_reset_ville');
	bouton_reset.setAttribute('alt', 'bouton_reset_ville');
	bouton_reset.setAttribute('onClick', 'action_reset(this.id)');
	bouton_reset.setAttribute('class', 'bouton_reset');
	bouton_reset
			.setAttribute('title', 'R&eacute;initialisation du champ Ville');

	var villeTd3 = document.createElement('td');
	villeTd3.appendChild(bouton_reset);

	var villeTr = document.createElement('tr');
	villeTr.setAttribute('id', 'tr_ville');
	villeTr.appendChild(villeTd1);
	villeTr.appendChild(villeTd2);
	villeTr.appendChild(villeTd3);

	HTML('tableau_critere').appendChild(villeTr);
}
