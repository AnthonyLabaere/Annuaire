/**
 * Cette fonction est appelee lorsque le filtre pays est modifie : les autres
 * filtres sont alors modifies en consequence en interrogant la BDD. Si le
 * filtre pays n'etait pas renseigne alors le filtre ville apparait. La carte
 * zoome sur le pays concerne. Les marqueurs des villes où des centraliens sont
 * presents apparaissent
 */
function onChangeFiltrePays() {

	if (!HTML('filtre_ville')) {
		villeTd1 = document.createElement('td');
		villeTd1.innerHTML = 'Ville';

		filtre_ville = document.createElement('select');
		filtre_ville.setAttribute('name', 'Ville');
		filtre_ville.setAttribute('id', 'filtre_ville');

		filtre_ville_option_par_defaut = document.createElement('option');
		filtre_ville_option_par_defaut.innerHTML = 'S&eacute;lectionnez le Ville recherch&eacute;e';

		filtre_ville.appendChild(filtre_ville_option_par_defaut);

		villeTd2 = document.createElement('td');
		villeTd2.appendChild(filtre_ville);

		villeTr = document.createElement('tr');
		villeTr.appendChild(villeTd1);
		villeTr.appendChild(villeTd2);

		HTML('tableau_critere').appendChild(villeTr);
	}

	miseAJourDesFiltres();
}

/**
 * Cette fonction est appelee lorsque l'un des filtres a ete modifie. Les autres
 * filtres sont mis a jour en consequence apres requetage de la BDD
 */
function miseAJourDesFiltres() {

	alimentation_filtreVille(HTML('filtre_pays').value);
	
	
	
	
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
	if (HTML('filtre_ville').selectedIndex != '0') {
		ville_nom = HTML('filtre_ville').value;
	}

	miseAJourDuFiltreEntreprise(anneePromotion_libelle, secteur_nom, pays_nom,
			ville_nom);

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
							filtre_entreprise_option_par_defaut.innerHTML = 'S&eacute;lectionnez l\'Entreprise recherch&eacute;e';
							filtre_entreprise
									.appendChild(filtre_entreprise_option_par_defaut);

							// Ajout des nouveaux elements
							for ( var element in data) {
								console.log(data[element]);
								filtre_entreprise.options[filtre_entreprise.options.length] = new Option(
										data[element]);
							}

						}
					});

}
