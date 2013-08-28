package models;

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
}
