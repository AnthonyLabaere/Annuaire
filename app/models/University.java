package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class University extends Model{
	@Id
	public Integer id;
	
	public String name;
	@ManyToOne
	public City city;
	
	public static Finder<Integer,University> find = new Finder<Integer,University>(Integer.class, University.class);
}
