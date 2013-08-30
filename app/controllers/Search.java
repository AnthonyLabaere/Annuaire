package controllers;

import java.util.List;

import connections.Ebean;

import models.Person;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.list;

public class Search extends Controller{

	public static Result search(){
		DynamicForm info = Form.form().bindFromRequest();
		List<Person> persons = Ebean.getPersonList(info);
		return ok(list.render(persons));
	}
}
