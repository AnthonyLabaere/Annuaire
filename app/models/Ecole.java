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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "Ecole")
@SequenceGenerator(name = "EcoleSequenceGenerator", sequenceName = "EcoleSequence")
public class Ecole extends Model {

	/** serial ID */
	private static final long serialVersionUID = -2300238816036696530L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EcoleSequenceGenerator")
	@Column(name = "ecole_ID")
	private Integer id;

	@Column(name = "ecole_nom")
	private String nom;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getId() {
		return id;
	}

}
