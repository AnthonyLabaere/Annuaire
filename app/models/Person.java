package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Person extends Model{
	@Id
	public String uid;
	
	public String name;
	public String surname;
	public Date birthday;
	public String mail;
	public String phone;
	public String skype;
	@ManyToOne
	public School schoolOfOrigin;
	@ManyToOne
	public Country nationality;
	@OneToOne
	public Photo photo;
	@OneToOne
	public CV cv;
	@OneToOne
	public Options options;
	
	public static Finder<String,Person> find = new Finder<String,Person>(String.class, Person.class);
}
