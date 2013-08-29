package controllers;

import models.Person;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class SignUp extends Controller{
	
	public static Result newMember(){
		DynamicForm info = Form.form().bindFromRequest();
		new Person(session("uid"),info.get("name"),info.get("surname"),info.get("birthday"),info.get("mail"),info.get("phone"),info.get("skype"),info.get("school"),info.get("nationality"));
		return Application.showList();
	}
}
