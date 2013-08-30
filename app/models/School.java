package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class School extends Model{
	@Id
	public Integer id;
	
	public String name;
	@ManyToOne
	public City city;
	
	public static Finder<Integer,School> find = new Finder<Integer,School>(Integer.class, School.class);
	
	public School(String name_, Integer city_id){
		name=name_;
		city=City.find.ref(city_id);
		save();
	}
	
	@Override
	public String toString(){
		return id + ". " + name + " in " + city.name;
	}
}
