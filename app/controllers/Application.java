package controllers;

import connections.LDAP;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.index;
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
    	if(LDAP.check(info.get("login"), info.get("passw"))){
    		return ok(list.render());
    	}else{
    		return index();
    	}
    }
  
}
