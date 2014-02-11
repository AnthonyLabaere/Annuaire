package controllers;

import play.Routes;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RoutingAjax extends Controller {

	// Javascript routing
	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("jsRoutes",
		        controllers.routes.javascript.ServiceAnneePromotion.AJAX_listeDesAnneesPromotion(),
		        controllers.routes.javascript.ServiceAnneePromotion.AJAX_listeDesAnneesPromotionSelonCriteres(),
		        controllers.routes.javascript.ServiceEcole.AJAX_listeDesEcoles(),
		        controllers.routes.javascript.ServiceEntreprise.AJAX_listeDesEntreprises(),
		        controllers.routes.javascript.ServiceEntreprise.AJAX_listeDesEntreprisesSelonCriteres(),
		        controllers.routes.javascript.ServiceSecteur.AJAX_listeDesSecteurs(),
		        controllers.routes.javascript.ServicePays.AJAX_listeDesPays(),
		        controllers.routes.javascript.ServiceVille.AJAX_listeDesVillesDuPays(),
		        controllers.routes.javascript.ServiceVille.AJAX_listeDesVillesSelonCriteres()));
	}
}
