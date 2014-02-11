/****************************************************************************

	This is a web application developed for the ACCENTS club from the
	Ecole Centrale de Nantes aiming to facilitate contact between travelling
	students.
	
    Copyright (C) 2013  Malik Olivier Boussejra

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see http://www.gnu.org/licenses/.

 ******************************************************************************/

package controllers;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import connections.LDAP;

/**
 * Application class. Contains basic display.
 * 
 * @author malik
 * 
 */
public class Application extends Controller {

	/**
	 * Display index
	 * 
	 * @return Index
	 */
	public static Result index() {
		if (session("uid") == null) {
			return ok(views.html.index.render());
		} else {
			return showCarte();
		}
	}

	/**
	 * Manage logging in
	 * 
	 * @return
	 */
	public static Result login() {
		DynamicForm info = Form.form().bindFromRequest();
		String login = info.get("login");
		if (LDAP.check(login, info.get("passw"))) {
			session("uid", login);
			return showCarte();
		} else {
			return index();
		}
	}

	/**
	 * log out the user
	 * 
	 * @return display login page
	 */
	public static Result logOut() {
		session().clear();
		return redirect("/");
	}

	/**
	 * Show list template but with an empty person list
	 * 
	 * @return display list template
	 */
	public static Result showCarte() {	
		
		return ok(views.html.carte.render());
	}

}
