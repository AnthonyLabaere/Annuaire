/****************************************************************************

	This is a web application developed for the ACCENTS club from the
	Ecole Centrale de Nantes aiming to facilitate contact between travelling
	students.
	
    Copyright (C) 2013  Malik Olivier Boussejra

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see http://www.gnu.org/licenses/.

******************************************************************************/

package models;

import help.Parse;

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
	
	public Person(String uid_,String name_,String surname_,String birthday_,String mail_,String phone_,String skype_,String school_,String nationality_){
		uid=uid_;
		name=name_;
		surname=surname_;
		birthday=Parse.parseDate(birthday_);
		mail=mail_;
		phone=phone_;
		skype=skype_;
		schoolOfOrigin=School.find.byId(Parse.parseIntSchool(school_));
		nationality=Country.find.byId(nationality_);
		save();
	}
}
