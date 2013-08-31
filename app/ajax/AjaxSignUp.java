package ajax;

import java.util.List;

import models.City;
import models.Country;
import models.School;
import play.mvc.Controller;
import play.mvc.Result;

public class AjaxSignUp extends Controller{
	
	public static Result addSchool(String name, Integer city_id){
		School school = new School(name,city_id);
		return ok(school.toString());
	}
	
	public static Result addCity(String name, Integer country_id){
		new City(name,country_id);
		return ok("City added with success");
	}
	
	public static Result unusedCityID(){
		return ok(City.unusedID().toString());
	}
	
	public static Result loadCityList(Integer country_id){
		Country country = Country.find.ref(country_id);
		List<City> cities = City.find.where().eq("country", country).orderBy("name").findList();
		String result = "<select id=\"cityList\">";
		for(City city : cities){
			result+="<option value="+city.id+">"+city.name+"</option>";
		}
		result+="</select>";
		return ok(result);
	}
}
