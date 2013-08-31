package scala;

import java.util.List;

import play.mvc.Controller;

import models.Person;
import models.School;

public class Util extends Controller{

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
	
	public static boolean signedIn(){
		return Person.find.byId(session("uid"))!=null;
	}
	
	public static String addEscChar(String str){
		return str.replace("'", "\'");
	}
}
