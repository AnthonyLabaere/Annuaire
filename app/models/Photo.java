package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Photo extends Model{
	@Id
	public Integer id;
	
	public String filename;
	
	public static Finder<Integer,Photo> find = new Finder<Integer,Photo>(Integer.class, Photo.class);
}
