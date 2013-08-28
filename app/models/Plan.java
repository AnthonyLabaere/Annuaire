package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Plan extends Model{
	@Id
	public Integer id;
	
	@ManyToOne
	public Person person;
	@ManyToOne
	public Country country;
	@ManyToOne
	public City city;
	
	@Column(columnDefinition = "TEXT")
	public String forWhat;
	@Column(columnDefinition = "TEXT")
	public String butWhen;
	
	public static Finder<Integer,Plan> find = new Finder<Integer,Plan>(Integer.class, Plan.class);
}