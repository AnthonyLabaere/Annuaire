package connections;

import java.util.List;

import com.avaje.ebean.ExpressionList;

import play.data.DynamicForm;

import models.Country;
import models.Person;

public class Ebean {
	
	public static List<Country> findAllCountries(){
		return Country.find.all();
	}
	
	public static List<Person> getPersonList(DynamicForm info){
		ExpressionList<Person> query = Person.find.where();
		query = query.like("name", "%"+info.get("name")+"%");
		return query.findList();
	}
}
