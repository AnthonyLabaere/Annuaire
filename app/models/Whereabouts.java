package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Whereabouts extends Model{
	@Id
	public Integer id;
	
	@OneToOne
	public Person person;
	@ManyToOne
	public City city;
	
	public static Finder<Integer,Whereabouts> find = new Finder<Integer,Whereabouts>(Integer.class, Whereabouts.class);
}
