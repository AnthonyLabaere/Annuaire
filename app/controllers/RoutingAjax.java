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

import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Routing des differents service Ajax de l'application
 * 
 * @author Anthony
 * 
 */
public class RoutingAjax extends Controller {

	// Javascript routing
	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("jsRoutes",
		        controllers.routes.javascript.ServiceCentralien.AJAX_listeDesCentraliens(),
		        controllers.routes.javascript.ServiceCentralien.AJAX_listeDesCentraliensSelonCriteres(),
		        controllers.routes.javascript.ServiceCentralien.AJAX_listeDesCoordonneesDesCentraliens(),
		        controllers.routes.javascript.ServiceAnneePromotion.AJAX_listeDesAnneesPromotion(),
		        controllers.routes.javascript.ServiceAnneePromotion.AJAX_listeDesAnneesPromotionSelonCriteres(),
		        controllers.routes.javascript.ServiceEcole.AJAX_listeDesEcoles(),
		        controllers.routes.javascript.ServiceEcole.AJAX_listeDesEcolesSelonCriteres(),
		        controllers.routes.javascript.ServiceEntreprise.AJAX_listeDesEntreprises(),
		        controllers.routes.javascript.ServiceEntreprise.AJAX_listeDesEntreprisesSelonCriteres(),
		        controllers.routes.javascript.ServiceSecteur.AJAX_listeDesSecteurs(),
		        controllers.routes.javascript.ServiceSecteur.AJAX_listeDesSecteursSelonCriteres(),
		        controllers.routes.javascript.ServicePays.AJAX_listeDesPays(),
		        controllers.routes.javascript.ServicePays.AJAX_listeDesPaysSelonCriteres(),
		        controllers.routes.javascript.ServiceVille.AJAX_listeDesVillesDuPays(),
		        controllers.routes.javascript.ServiceVille.AJAX_listeDesVillesSelonCriteres()));
	}
}
