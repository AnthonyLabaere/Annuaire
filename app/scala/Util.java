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

package scala;

import java.util.List;

import play.mvc.Controller;

import models.Country;
import models.Person;
import models.School;

/**
 * Some method used in scala templates
 * @author malik
 *
 */
public class Util extends Controller{

	/**
	 * Get school list
	 * @return
	 */
	public static String getSchools(){
		String liste = "";
		List<School> schools = School.find.all();
		if(schools.isEmpty())	return "";
		else{
			for(School s : schools){
				liste+="'"+s+"',";
			}
			return liste.substring(0, liste.length()-1);
		}
	}
	
	/**
	 * Returns true if the user has signed up, else returns false
	 * @return true or false
	 */
	public static boolean signedIn(){
		return Person.find.byId(session("uid"))!=null;
	}
	
	/**
	 * Adds escape character to strings used in Javascripts
	 * @param str
	 * @return string in argument with escape characters added
	 */
	public static String addEscChar(String str){
		return str.replace("'", "\'");
	}
	
	/**
	 * Gets how many students there are in each country in the world.
	 * @return A string used in Javascript (for the world map).
	 */
	public static String getStudentData(){
		String data = "";
		List<Country> countries = Country.find.all();
		for(Country country : countries){
			Integer howMany = Person.find.where().eq("nationality",country).findList().size();
			data+="\""+country.id+"\":"+howMany+",";
		}
		return data.substring(0, data.length()-1);
	}
}
