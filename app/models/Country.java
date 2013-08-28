package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Country extends Model{
	@Id
	public Integer id;
	
	public String name;
	public String nationality;
	
	public static Finder<Integer,Country> find = new Finder<Integer,Country>(Integer.class, Country.class);
}
