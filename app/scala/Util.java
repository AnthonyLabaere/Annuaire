package scala;

import java.util.List;

import models.School;

public class Util {

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
}
