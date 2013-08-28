package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class CV extends Model{
	@Id
	public Integer id;
	
	public String filename;
	
	public static Finder<Integer,CV> find = new Finder<Integer,CV>(Integer.class, CV.class);
}
