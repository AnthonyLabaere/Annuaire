package controllers;

import models.Person;
import connections.LDAP;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.index;
import views.html.firstTime;
import views.html.list;

/**
 * Application
 * @author malik
 *
 */
public class Application extends Controller {
	
	/**
	 * Affiche l'index
	 * @return Index
	 */
    public static Result index() {
        return ok(index.render());
    }
    
    /**
     * Login pour se connecter
     * @return
     */
    public static Result login(){
    	DynamicForm info = Form.form().bindFromRequest();
    	String login = info.get("login");
    	if(LDAP.check(login, info.get("passw"))){
    		if(firstTime(login))	return ok(firstTime.render());
    		else					return ok(list.render());
    	}else{
    		return index();
    	}
    }
  
    public static boolean firstTime(String login){
    	return Person.find.byId(login)==null;
    }
}
