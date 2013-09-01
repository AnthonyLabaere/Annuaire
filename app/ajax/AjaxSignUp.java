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

import java.util.List;

import models.City;
import models.Country;
import models.School;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Ajax requests used on first.scala.html template
 * @author malik
 *
 */
public class AjaxSignUp extends Controller{
	
	/**
	 * Add a school to the database
	 * @param name
	 * @param city_id
	 * @return the school added converted to string
	 */
	public static Result addSchool(String name, Integer city_id){
		School school = new School(name,city_id);
		return ok(school.toString());
	}
	
	/**
	 * Add a city to the database
	 * @param name
	 * @param country_id
	 * @return nothing special
	 */
	public static Result addCity(String name, String country_id){
		new City(name,country_id);
		return ok("City added with success");
	}
	
	/**
	 * For now, useless function
	 * @return
	 */
	public static Result unusedCityID(){
		return ok(City.unusedID().toString());
	}
	
	/**
	 * Loads all the city in a specified country.
	 * They are stored in a select.
	 * @param country_id
	 * @return the html string containing the city list
	 */
	public static Result loadCityList(String country_id){
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
