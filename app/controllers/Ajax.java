package controllers;

import play.Routes;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Ajax extends Controller {

	public static Result testAjax(String arg) {
		return ok("Pays : " + arg);
	}

	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result testAjaxEnvoyer() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			return badRequest("Objet Json attendu");
		} else {
			String nom = json.findPath("nom").textValue();
			if (nom == null) {
				return badRequest("Il manque le parametre [nom]");
			} else {
				return testAjaxRecevoir(nom);
			}
		}

	}

	public static Result testAjaxRecevoir(String nom) {
		ObjectNode result = play.libs.Json.newObject();
		result.put("message", "Salut " + nom + "!");
		result.put("taille_du_nom", nom.length());
		return ok(result);
	}

	// Javascript routing
	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("jsRoutes",
		        controllers.routes.javascript.Ajax.testAjax(),
		        controllers.routes.javascript.Ajax.testAjaxEnvoyer(),
		        controllers.routes.javascript.Ajax.testAjaxRecevoir(),
		        controllers.routes.javascript.ServiceAnneePromotion.AJAX_listeDesAnneesdePromotion(),
		        controllers.routes.javascript.ServiceEcole.AJAX_listeDesEcoles(),
		        controllers.routes.javascript.ServiceEntreprise.AJAX_listeDesEntreprises(),
		        controllers.routes.javascript.ServiceSecteur.AJAX_listeDesSecteurs(),
		        controllers.routes.javascript.ServicePays.AJAX_listeDesPays(),
		        controllers.routes.javascript.ServiceVille.AJAX_listeDesVillesDuPays(),
		        controllers.routes.javascript.ServiceEntreprise.AJAX_listeDesEntreprisesSelonCriteres()));
	}
}
