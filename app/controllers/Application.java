/****************************************************************************

Copyright 2014 Anthony Labaere

Contributeurs : 
François Neber francois.neber@centraliens-nantes.net
Malik Olivier Boussejra malik.boussejra@centraliens-nantes.net
Anthony Labaere anthony.labaere@centraliens-nantes.net

Ce logiciel est un programme informatique ayant pour but de faciliter 
les contacts entre étudiants et diplômés de l'École Centrale Nantes 
à l'étranger comme en France.

Ce logiciel est régi par la licence CeCILL soumise au droit français et
respectant les principes de diffusion des logiciels libres. Vous pouvez
utiliser, modifier et/ou redistribuer ce programme sous les conditions
de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA 
sur le site "http://www.cecill.info".

En contrepartie de l'accessibilité au code source et des droits de copie,
de modification et de redistribution accordés par cette licence, il n'est
offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
seule une responsabilité restreinte pèse sur l'auteur du programme,  le
titulaire des droits patrimoniaux et les concédants successifs.

A cet égard  l'attention de l'utilisateur est attirée sur les risques
associés au chargement,  à l'utilisation,  à la modification et/ou au
développement et à la reproduction du logiciel par l'utilisateur étant 
donné sa spécificité de logiciel libre, qui peut le rendre complexe à 
manipuler et qui le réserve donc à des développeurs et des professionnels
avertis possédant  des  connaissances  informatiques approfondies.  Les
utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
logiciel à leurs besoins dans des conditions permettant d'assurer la
sécurité de leurs systèmes et ou de leurs données et, plus généralement, 
à l'utiliser et l'exploiter dans les mêmes conditions de sécurité. 

Le fait que vous puissiez accéder à cet en-tête signifie que vous avez 
pris connaissance de la licence CeCILL et que vous en avez accepté les
termes.

******************************************************************************/

package controllers;

import connections.LDAP;
import geography.ThreadGeocoder;
import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Classe Application : detaille les pages a afficher
 * 
 * @author malik
 * 
 */
public class Application extends Controller {

	/**
	 * Affiche la page d'index avec un message d'erreur de connexion si le
	 * parametre renseigne est true
	 * 
	 * @return la page d'index si l'uid est null ou la carte sinon
	 */
	public static Result index(boolean erreur) {
		if (session("uid") == null) {
			return ok(views.html.index.render(erreur));
		} else {
			return showCarte();
		}
	}

	/**
	 * Gere le processus de login
	 * 
	 * @return la carte si le login/mdp est correct et la page de login sinon
	 */
	public static Result login() {
		DynamicForm info = Form.form().bindFromRequest();
		String login = info.get("login");
		if (LDAP.verification(login, info.get("passw"))) {
			session("uid", login);
			return showCarte();
		} else {
			return index(false);
		}
	}

	/**
	 * Deconnexion de l'utilisateur
	 * 
	 * @return la page de login
	 */
	public static Result logOut() {
		session().clear();
		return redirect("/");
	}

	/**
	 * Affiche la carte qui est le coeur de l'application
	 * 
	 * @return la page de la carte
	 */
	public static Result showCarte() {

		// Avant de montrer la carte on verifie que la base est correctement
		// alimentee en coordonnees GPS (uniquement en mode developpeur)
		if (Play.application().configuration().getString("developpeur.mode")
		        .equals("on")) {
			new ThreadGeocoder().start();
		}

		return ok(views.html.carte.render());
	}

}
