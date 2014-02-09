// Alimentation du filtre des annees de promotion
jsRoutes.controllers.ServiceAnneePromotion
		.demandeAJAX_listeDesAnneesdePromotion()
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
		.demandeAJAX_listeDesEcoles()
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

function testAjax(arg) {
	jsRoutes.controllers.Ajax.testAjax(arg).ajax({
		success : function(data, textStatus, jqXHR) {
			console.log(data);
		}
	});
}

function testAjaxEnvoyer() {
	var nom = "Anthony";
	var destination = "Nantes";
	jsRoutes.controllers.Ajax.testAjaxEnvoyer().ajax({
		data : JSON.stringify({
			"nom" : "Lucas",
			"destination" : "Nantes"
		}),
		dataType : "json",
		contentType : "application/json",
		success : function(data, textStatus, jqXHR) {
			console.log(data);
		}
	});
}