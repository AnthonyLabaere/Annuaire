package models;

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

import java.util.List;

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
	
	public City(String name_, String country_id){
		name=name_;
		country=Country.find.ref(country_id);
		save();
	}
	
	public static Integer unusedID(){
		List<City> cities = find.orderBy("id desc").setMaxRows(1).findList();
		if(cities.isEmpty())	return 1;
		else					return cities.get(0).id+1;
	}
}
