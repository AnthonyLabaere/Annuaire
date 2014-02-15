/** Constantes contenant le nombre total de filtre */
var NOMBRE_TOTAL_FILTRES = 6;

/**
 * Ces tableaux de constantes permettent de retenir des informations sur chacun
 * des filtres
 */
var ARRAY_FILTRE_ANNEEPROMOTION = new Array();
var ARRAY_FILTRE_ECOLE = new Array();
var ARRAY_FILTRE_ENTREPRISE = new Array();
var ARRAY_FILTRE_SECTEUR = new Array();
var ARRAY_FILTRE_PAYS = new Array();
var ARRAY_FILTRE_VILLE = new Array();

/** Ces constantes sont les ID des differents filtres */
ARRAY_FILTRE_ANNEEPROMOTION.push('filtre_anneePromotion');
ARRAY_FILTRE_ECOLE.push('filtre_ecole');
ARRAY_FILTRE_ENTREPRISE.push('filtre_entreprise');
ARRAY_FILTRE_SECTEUR.push('filtre_secteur');
ARRAY_FILTRE_PAYS.push('filtre_pays');
ARRAY_FILTRE_VILLE.push('filtre_ville');

/** Ces constantes sont les valeurs par defauts des differents filtres */
ARRAY_FILTRE_ANNEEPROMOTION
		.push('S&eacute;lectionnez l\'Ann&eacute;e de promotion recherch&eacute;e');
ARRAY_FILTRE_ECOLE
		.push('S&eacute;lectionnez l\'&Eacute;cole recherch&eacute;e');
ARRAY_FILTRE_ENTREPRISE
		.push('S&eacute;lectionnez l\'Entreprise recherch&eacute;e');
ARRAY_FILTRE_SECTEUR.push('S&eacute;lectionnez le Secteur recherch&eacute;');
ARRAY_FILTRE_PAYS.push('S&eacute;lectionnez le Pays recherch&eacute;');
ARRAY_FILTRE_VILLE.push('S&eacute;lectionnez le Ville recherch&eacute;e');

/**
 * Ces constantes sont utilisees pour connaitre l'ordre dans lequel les filtres
 * ont ete actives et donc lesquels il faut recalculer lorsque l'un d'entre eux
 * est reinitialise
 */
var ORDRE_ACTIVATION_DERNIERE_VALEUR = 1;
var ORDRE_ACTIVATION_PAR_DEFAUT = -1;
ARRAY_FILTRE_ANNEEPROMOTION.push(ORDRE_ACTIVATION_PAR_DEFAUT);
ARRAY_FILTRE_ECOLE.push(ORDRE_ACTIVATION_PAR_DEFAUT);
ARRAY_FILTRE_ENTREPRISE.push(ORDRE_ACTIVATION_PAR_DEFAUT);
ARRAY_FILTRE_SECTEUR.push(ORDRE_ACTIVATION_PAR_DEFAUT);
ARRAY_FILTRE_PAYS.push(ORDRE_ACTIVATION_PAR_DEFAUT);
ARRAY_FILTRE_VILLE.push(ORDRE_ACTIVATION_PAR_DEFAUT);

/**
 * Ces constantes indiquent simplement l'indice auquel chercher dans le tableau
 * pour trouver telle information
 */
var ARRAY_FILTRE_ID = 0;
var ARRAY_FILTRE_OPTION_PAR_DEFAUT = 1;
var ARRAY_FILTRE_ORDRE_ACTIVATION = 2;

function initialisationFiltres() {
	initialisationFiltreAnneePromotion();
	initialisationFiltreEcole();
	initialisationFiltreEntreprise();
	initialisationFiltreSecteur();
	initialisationFiltrePays();
}

/** Alimentation du filtre des annees de promotion */
function initialisationFiltreAnneePromotion() {
	jsRoutes.controllers.ServiceAnneePromotion
			.AJAX_listeDesAnneesPromotion()
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_anneePromotion = HTML(ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_ID]);
							filtre_anneePromotion.innerHTML = '';

							filtre_anneePromotion_option_par_defaut = document
									.createElement('option');
							filtre_anneePromotion_option_par_defaut.innerHTML = ARRAY_FILTRE_ANNEEPROMOTION[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
							filtre_anneePromotion
									.appendChild(filtre_anneePromotion_option_par_defaut);

							for ( var element in data) {
								filtre_anneePromotion.options[filtre_anneePromotion.options.length] = new Option(
										data[element]);
							}
						}
					});
}

/** Alimentation du filtre des ecoles (si ce filtre existe) */
function initialisationFiltreEcole() {
	if (HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID])) {
		jsRoutes.controllers.ServiceEcole
				.AJAX_listeDesEcoles()
				.ajax(
						{
							success : function(data, textStatus, jqXHR) {
								var filtre_ecole = HTML(ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_ID]);
								filtre_ecole.innerHTML = '';

								filtre_ecole_option_par_defaut = document
										.createElement('option');
								filtre_ecole_option_par_defaut.innerHTML = ARRAY_FILTRE_ECOLE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
								filtre_ecole
										.appendChild(filtre_ecole_option_par_defaut);

								for ( var element in data) {
									filtre_ecole.options[filtre_ecole.options.length] = new Option(
											data[element]);
								}
							}
						});
	}
}

/** Alimentation du filtre des entreprises (si ce filtre existe) */
function initialisationFiltreEntreprise() {
	if (HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID])) {
		jsRoutes.controllers.ServiceEntreprise
				.AJAX_listeDesEntreprises()
				.ajax(
						{
							success : function(data, textStatus, jqXHR) {
								var filtre_entreprise = HTML(ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_ID]);
								filtre_entreprise.innerHTML = '';

								filtre_entreprise_option_par_defaut = document
										.createElement('option');
								filtre_entreprise_option_par_defaut.innerHTML = ARRAY_FILTRE_ENTREPRISE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
								filtre_entreprise
										.appendChild(filtre_entreprise_option_par_defaut);

								for ( var element in data) {
									filtre_entreprise.options[filtre_entreprise.options.length] = new Option(
											data[element]);
								}
							}
						});
	}
}

/** Alimentation du filtre des secteurs */
function initialisationFiltreSecteur() {
	jsRoutes.controllers.ServiceSecteur
			.AJAX_listeDesSecteurs()
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_secteur = HTML(ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_ID]);
							filtre_secteur.innerHTML = '';

							filtre_secteur_option_par_defaut = document
									.createElement('option');
							filtre_secteur_option_par_defaut.innerHTML = ARRAY_FILTRE_SECTEUR[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
							filtre_secteur
									.appendChild(filtre_secteur_option_par_defaut);

							for ( var element in data) {
								filtre_secteur.options[filtre_secteur.options.length] = new Option(
										data[element]);
							}
						}
					});
}

/** Alimentation du filtre des pays */
function initialisationFiltrePays() {
	jsRoutes.controllers.ServicePays
			.AJAX_listeDesPays()
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_pays = HTML(ARRAY_FILTRE_PAYS[ARRAY_FILTRE_ID]);
							filtre_pays.innerHTML = '';

							filtre_pays_option_par_defaut = document
									.createElement('option');
							filtre_pays_option_par_defaut.innerHTML = ARRAY_FILTRE_PAYS[ARRAY_FILTRE_OPTION_PAR_DEFAUT];
							filtre_pays
									.appendChild(filtre_pays_option_par_defaut);

							for ( var element in data) {
								filtre_pays.options[filtre_pays.options.length] = new Option(
										data[element]);
							}
						}
					});
}

initialisationFiltres();

// Creation et alimentation du filtre des villes en considérant le pays
// selectionne
function creationAlimentation_filtreVille(pays_nom) {
	// Creation du filtre
	var villeTd1 = document.createElement('td');
	villeTd1.innerHTML = 'Ville';

	var filtre_ville = document.createElement('select');
	filtre_ville.setAttribute('name', 'Ville');
	filtre_ville.setAttribute('id', ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]);
	filtre_ville.setAttribute('onChange', 'miseAJourDesFiltres(this.id)');

	var filtre_ville_option_par_defaut = document.createElement('option');
	filtre_ville_option_par_defaut.innerHTML = ARRAY_FILTRE_VILLE[ARRAY_FILTRE_OPTION_PAR_DEFAUT];

	filtre_ville.appendChild(filtre_ville_option_par_defaut);

	var villeTd2 = document.createElement('td');
	villeTd2.appendChild(filtre_ville);

	var villeTr = document.createElement('tr');
	villeTr.setAttribute('id', 'tr_ville');
	villeTr.appendChild(villeTd1);
	villeTr.appendChild(villeTd2);

	HTML('tableau_critere').appendChild(villeTr);

	// Alimentation du filtre
	jsRoutes.controllers.ServiceVille
			.AJAX_listeDesVillesDuPays(pays_nom)
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_ville = HTML(ARRAY_FILTRE_VILLE[ARRAY_FILTRE_ID]);

							// Suppression des elements existants dans le filtre
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
