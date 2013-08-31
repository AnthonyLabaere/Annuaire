package controllers;

import java.util.ArrayList;

import models.Person;
import connections.LDAP;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.index;
import views.html.firstTime;
import views.html.list;
import views.html.editProfile;

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
    	if(session("uid")==null)	return ok(index.render());
    	else						return	showList();
    }
    
    /**
     * Login pour se connecter
     * @return
     */
    public static Result login(){
    	DynamicForm info = Form.form().bindFromRequest();
    	String login = info.get("login");
    	if(LDAP.check(login, info.get("passw"))){
    		session("uid",login);
    		if(firstTime(login))	return showFirst();
    		else					return showList();
    	}else{
    		return index();
    	}
    }
  
    public static boolean firstTime(String login){
    	return Person.find.byId(login)==null;
    }
    
    public static Result logOut(){
    	session().clear();
    	return redirect("/");
    }
    
    public static Result showList(){
    	return ok(list.render(new ArrayList<Person>()));
    }
    
    public static Result showFirst(){
    	return ok(firstTime.render());
    }
    
    public static Result editProfile(){
    	Person profile = Person.find.byId(session("uid"));
    	if(profile!=null)	return ok(editProfile.render(profile));
    	else				return index();
    }
}
