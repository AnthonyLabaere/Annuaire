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

import models.Person;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Manage signing up
 * @author malik
 *
 */
public class SignUp extends Controller{
	
	/**
	 * Add a new member
	 * @return empty list template
	 */
	public static Result newMember(){
		DynamicForm info = Form.form().bindFromRequest();
		new Person(session("uid"),info.get("name"),info.get("surname"),info.get("birthday"),info.get("mail"),info.get("phone"),info.get("skype"),info.get("school"),info.get("nationality"));
		return Application.showMap();
	}
}
