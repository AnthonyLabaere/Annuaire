package controllers;

import java.util.List;

import models.AnneePromotion;
import play.Routes;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dao.AnneePromotionDao;

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
		        controllers.routes.javascript.ServiceAnneePromotion.demandeAJAX_listeDesAnneesdePromotion(),
		        controllers.routes.javascript.ServiceAnneePromotion.envoiAJAX_listeDesAnneesdePromotion(),
		        controllers.routes.javascript.ServiceEcole.demandeAJAX_listeDesEcoles(),
		        controllers.routes.javascript.ServiceEcole.envoiAJAX_listeDesEcoles(),
		        controllers.routes.javascript.ServiceEntreprise.demandeAJAX_listeDesEntreprises(),
		        controllers.routes.javascript.ServiceEntreprise.envoiAJAX_listeDesEntreprises(),
		        controllers.routes.javascript.ServiceSecteur.demandeAJAX_listeDesSecteurs(),
		        controllers.routes.javascript.ServiceSecteur.envoiAJAX_listeDesSecteurs(),
		        controllers.routes.javascript.ServicePays.demandeAJAX_listeDesPays(),
		        controllers.routes.javascript.ServicePays.envoiAJAX_listeDesPays(),
		        controllers.routes.javascript.ServiceVille.demandeAJAX_listeDesVillesDuPays(),
		        controllers.routes.javascript.ServiceVille.envoiAJAX_listeDesVillesDuPays()));
	}
}
