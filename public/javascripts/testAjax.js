//TODO : supprimer ce fichier

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