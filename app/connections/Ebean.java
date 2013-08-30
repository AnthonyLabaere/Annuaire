package connections;

import java.util.List;

import models.Country;

public class Ebean {
	
	public static List<Country> findAllCountries(){
		return Country.find.all();
	}
}
