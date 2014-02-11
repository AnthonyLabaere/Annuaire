/** Ces constantes sont les ID des differents filtres */
var FILTRE_ANNEEPROMOTION_ID = 'filtre_anneePromotion';
var FILTRE_ECOLE_ID = 'filtre_ecole';
var FILTRE_ENTREPRISE_ID = 'filtre_entreprise';
var FILTRE_SECTEUR_ID = 'filtre_secteur';
var FILTRE_PAYS_ID = 'filtre_pays';
var FILTRE_VILLE_ID = 'filtre_ville';

/** Ces constantes sont les valeurs par defauts des differents filtres */
var FILTRE_ANNEEPROMOTION_OPTION_PAR_DEFAUT_TEXTE = 'S&eacute;lectionnez l\'Ann&eacute;e de promotion recherch&eacute;e';
var FILTRE_ECOLE_OPTION_PAR_DEFAUT_TEXTE = 'S&eacute;lectionnez l\'&Eacute;cole recherch&eacute;e';
var FILTRE_ENTREPRISE_OPTION_PAR_DEFAUT_TEXTE = 'S&eacute;lectionnez l\'Entreprise recherch&eacute;e';
var FILTRE_SECTEUR_OPTION_PAR_DEFAUT_TEXTE = 'S&eacute;lectionnez le Secteur recherch&eacute;';
var FILTRE_PAYS_OPTION_PAR_DEFAUT_TEXTE = 'S&eacute;lectionnez le Pays recherch&eacute;';
var FILTRE_VILLE_OPTION_PAR_DEFAUT_TEXTE = 'S&eacute;lectionnez le Ville recherch&eacute;e';

function initialisationFiltres() {
	// Alimentation du filtre des annees de promotion
	jsRoutes.controllers.ServiceAnneePromotion
			.AJAX_listeDesAnneesPromotion()
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_anneePromotion = HTML('filtre_anneePromotion');
							filtre_anneePromotion.innerHTML = '';

							filtre_anneePromotion_option_par_defaut = document
									.createElement('option');
							filtre_anneePromotion_option_par_defaut.innerHTML = FILTRE_ANNEEPROMOTION_OPTION_PAR_DEFAUT_TEXTE;
							filtre_anneePromotion
									.appendChild(filtre_anneePromotion_option_par_defaut);

							for ( var element in data) {
								filtre_anneePromotion.options[filtre_anneePromotion.options.length] = new Option(
										data[element]);
							}
						}
					});

	// Alimentation du filtre des ecoles
	jsRoutes.controllers.ServiceEcole
			.AJAX_listeDesEcoles()
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_ecole = HTML('filtre_ecole');
							filtre_ecole.innerHTML = '';

							filtre_ecole_option_par_defaut = document
									.createElement('option');
							filtre_ecole_option_par_defaut.innerHTML = FILTRE_ECOLE_OPTION_PAR_DEFAUT_TEXTE;
							filtre_ecole
									.appendChild(filtre_ecole_option_par_defaut);

							for ( var element in data) {
								filtre_ecole.options[filtre_ecole.options.length] = new Option(
										data[element]);
							}
						}
					});

	// Alimentation du filtre des entreprises
	jsRoutes.controllers.ServiceEntreprise
			.AJAX_listeDesEntreprises()
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_entreprise = HTML('filtre_entreprise');
							filtre_entreprise.innerHTML = '';

							filtre_entreprise_option_par_defaut = document
									.createElement('option');
							filtre_entreprise_option_par_defaut.innerHTML = FILTRE_ENTREPRISE_OPTION_PAR_DEFAUT_TEXTE;
							filtre_entreprise
									.appendChild(filtre_entreprise_option_par_defaut);

							for ( var element in data) {
								filtre_entreprise.options[filtre_entreprise.options.length] = new Option(
										data[element]);
							}
						}
					});

	// Alimentation du filtre des secteurs
	jsRoutes.controllers.ServiceSecteur
			.AJAX_listeDesSecteurs()
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_secteur = HTML('filtre_secteur');
							filtre_secteur.innerHTML = '';

							filtre_secteur_option_par_defaut = document
									.createElement('option');
							filtre_secteur_option_par_defaut.innerHTML = FILTRE_SECTEUR_OPTION_PAR_DEFAUT_TEXTE;
							filtre_secteur
									.appendChild(filtre_secteur_option_par_defaut);

							for ( var element in data) {
								filtre_secteur.options[filtre_secteur.options.length] = new Option(
										data[element]);
							}
						}
					});

	// Alimentation du filtre des pays
	jsRoutes.controllers.ServicePays
			.AJAX_listeDesPays()
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_pays = HTML('filtre_pays');
							filtre_pays.innerHTML = '';

							filtre_pays_option_par_defaut = document
									.createElement('option');
							filtre_pays_option_par_defaut.innerHTML = FILTRE_PAYS_OPTION_PAR_DEFAUT_TEXTE;
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
	filtre_ville.setAttribute('id', 'filtre_ville');
	filtre_ville.setAttribute('onChange', 'miseAJourDesFiltres(this.id)');

	var filtre_ville_option_par_defaut = document.createElement('option');
	filtre_ville_option_par_defaut.innerHTML = FILTRE_VILLE_OPTION_PAR_DEFAUT_TEXTE;

	filtre_ville.appendChild(filtre_ville_option_par_defaut);

	var villeTd2 = document.createElement('td');
	villeTd2.appendChild(filtre_ville);

	var villeTd3 = document.createElement('td');
	var bouton_reset_ville = document.createElement('img');
	bouton_reset_ville.setAttribute('src', '/assets/images/reset.png');
	bouton_reset_ville.setAttribute('id', 'bouton_reset_ville');
	bouton_reset_ville.setAttribute('alt', 'bouton_reset_ville');
	bouton_reset_ville.setAttribute('onClick', "reset(this.id)");
	bouton_reset_ville.setAttribute('class', 'bouton_reset');
	bouton_reset_ville.setAttribute('title', 'R&eacute;initialiser ce filtre');
	villeTd3.appendChild(bouton_reset_ville);

	var villeTr = document.createElement('tr');
	villeTr.setAttribute('id', 'tr_ville');
	villeTr.appendChild(villeTd1);
	villeTr.appendChild(villeTd2);
	villeTr.appendChild(villeTd3);

	HTML('tableau_critere').appendChild(villeTr);

	// Alimentation du filtre
	jsRoutes.controllers.ServiceVille
			.AJAX_listeDesVillesDuPays(pays_nom)
			.ajax(
					{
						success : function(data, textStatus, jqXHR) {
							var filtre_ville = HTML('filtre_ville');

							// Suppression des elements existants dans le filtre
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

function suppression_filtreVille() {
	HTML('tableau_critere').removeChild(HTML('tr_ville'));
}
