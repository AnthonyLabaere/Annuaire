package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class City extends Model{
	@Id
	public Integer id;
	
	public String name;
	@ManyToOne
	public Country country;
	
	public static Finder<Integer,City> find = new Finder<Integer,City>(Integer.class, City.class);
	
	public City(String name_, String country_id){
		name=name_;
		country=Country.find.ref(country_id);
		save();
	}
	
	public static Integer unusedID(){
		List<City> cities = find.orderBy("id desc").setMaxRows(1).findList();
		if(cities.isEmpty())	return 1;
		else					return cities.get(0).id+1;
	}
}
