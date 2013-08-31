package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Country extends Model{
	@Id
	public String id;
	
	public String name;
	public String nationality;
	
	public static Finder<String,Country> find = new Finder<String,Country>(String.class, Country.class);
}
