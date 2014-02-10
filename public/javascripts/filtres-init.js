//Alimentation du filtre des annees de promotion
jsRoutes.controllers.ServiceAnneePromotion
		.AJAX_listeDesAnneesdePromotion()
		.ajax(
				{
					success : function(data, textStatus, jqXHR) {
						// console.log(data);
						var filtre_annee_de_promotion = HTML('filtre_annee_de_promotion');

						for ( var element in data) {
							filtre_annee_de_promotion.options[filtre_annee_de_promotion.options.length] = new Option(
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

						for ( var element in data) {
							filtre_pays.options[filtre_pays.options.length] = new Option(
									data[element]);
						}
					}
				});

// Alimentation du filtre des villes en considérant le pays
function alimentation_filtreVille(pays_nom) {
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
							filtre_ville_option_par_defaut.innerHTML = 'S&eacute;lectionnez le Ville recherch&eacute;e';
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
