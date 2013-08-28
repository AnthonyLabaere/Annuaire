package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Contact extends Model{
	@Id
	public Integer id;
	
	@ManyToOne
	public Person person1;
	@ManyToOne
	public Person person2;
	@ManyToOne
	public ContactType typeC;
	
	public static Finder<Integer,Contact> find = new Finder<Integer,Contact>(Integer.class, Contact.class);
}
