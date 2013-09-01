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

package ajax;

import help.Parse;
import models.Person;
import models.School;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Manage ajax requests used to edit the database
 * @author malik
 *
 */
public class AjaxEdit extends Controller{

	/**
	 * Save changes made by user
	 * @param arg : type of value that must be updated
	 * @param value : new value
	 * @return a simple Result depending on whether the change
	 * was successfully updated.
	 */
	public static Result save(String arg, String value){
		Person profile = Person.find.byId(session("uid"));
		if(profile==null)	return unauthorized();
		switch(arg){
		case "name":
			profile.name=value;
			break;
		case "surname":
			profile.surname=value;
			break;
		case "birthday":
			profile.birthday=Parse.parseDateHyphen(value);
			break;
		case "mail":
			profile.mail=value;
			break;
		case "phone":
			profile.phone=value;
			break;
		case "skype":
			profile.skype=value;
			break;
		case "school":
			profile.schoolOfOrigin=School.find.byId(Parse.parseIntSchool(value));
			break;
		default :
			return badRequest("Haven't found specified argument");
		}
		profile.save();
		return ok("Edited with success");
	}
}
