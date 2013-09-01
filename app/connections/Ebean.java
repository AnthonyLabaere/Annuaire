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

package connections;

import java.util.List;

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;

import play.data.DynamicForm;

import models.Country;
import models.Person;

public class Ebean {
	
	public static List<Country> findAllCountries(){
		return Country.find.orderBy("name").findList();
	}
	
	public static List<Person> getPersonList(DynamicForm info){
		ExpressionList<Person> query = Person.find.where();
		query = query.like("name", "%"+info.get("name")+"%");
		query = query.like("surname", "%"+info.get("surname")+"%");
		query = query.like("schoolOfOrigin.name", "%"+info.get("school")+"%");
		String nationality = info.get("nationality");
		query = query.or(
				Expr.like("nationality.name", "%"+nationality+"%"),
				Expr.like("nationality.nationality", "%"+nationality+"%")
					);
		return query.findList();
	}
}
