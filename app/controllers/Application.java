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

import java.util.ArrayList;

import models.Person;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.editProfile;
import views.html.index;
import views.html.map;
import connections.LDAP;

/**
 * Application class. Contains basic display.
 * @author malik
 *
 */
public class Application extends Controller {
	
	/**
	 * Display index
	 * @return Index
	 */
    public static Result index() {
    	if(session("uid")==null)	return ok(index.render());
    	else						return	showMap();
    }
    
    /**
     * Manage logging in
     * @return
     */
    public static Result login(){
    	DynamicForm info = Form.form().bindFromRequest();
    	String login = info.get("login");
    	if(LDAP.check(login, info.get("passw"))){
    		session("uid",login);				
    		return showMap();
    	}else{
    		return index();
    	}
    }
    
    /**
     * returns true of you have no profile, else returns false
     * @param login
     * @return true or false
     */
    public static boolean firstTime(String login){
    	return Person.find.byId(login)==null;
    }
    
    /**
     * log out the user
     * @return display login page
     */
    public static Result logOut(){
    	session().clear();
    	return redirect("/");
    }
    
    /**
     * Show list template but with an empty person list
     * @return display list template
     */
    public static Result showMap(){
    	return ok(map.render(new ArrayList<Person>()));
    }
   
    /**
     * Display edit profile page
     * @return edit profile page
     */
    public static Result editProfile(){
    	Person profile = Person.find.byId(session("uid"));
    	if(profile!=null)	return ok(editProfile.render(profile));
    	else				return index();
    }
}
