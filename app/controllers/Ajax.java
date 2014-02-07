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
	
	public static Result demandeAJAX_listeDesAnneesdePromotion() {
		return envoiAJAX_listeDesAnneesdePromotion();
	}

	public static Result envoiAJAX_listeDesAnneesdePromotion() {
		List<AnneePromotion> listeDesAnneesdePromotion = AnneePromotionDao.find
		        .orderBy("libelle asc").findList();
		return ok(Json.toJson(listeDesAnneesdePromotion));
	}

	// Javascript routing
	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("jsRoutes",
		        routes.javascript.Ajax.testAjax(),
		        routes.javascript.Ajax.testAjaxEnvoyer(),
		        routes.javascript.Ajax.testAjaxRecevoir(),
		        routes.javascript.Ajax.demandeAJAX_listeDesAnneesdePromotion(),
		        routes.javascript.Ajax.envoiAJAX_listeDesAnneesdePromotion()));
	}
}
