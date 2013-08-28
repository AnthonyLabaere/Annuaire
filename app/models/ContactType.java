package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class ContactType extends Model{
	@Id
	public Integer id;
	
	public String typeC;
	
	public static Finder<Integer,ContactType> find = new Finder<Integer,ContactType>(Integer.class, ContactType.class);
}
