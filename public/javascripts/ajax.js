// Alimentation du filtre des annees de promotion
jsRoutes.controllers.Ajax
		.demandeAJAX_listeDesAnneesdePromotion()
		.ajax(
				{
					success : function(data, textStatus, jqXHR) {
						var filtre_annee_de_promotion = HTML('filtre_annee_de_promotion');

						for ( var element in data) {
							filtre_annee_de_promotion.options[filtre_annee_de_promotion.options.length] = new Option(
									data[element].libelle);
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