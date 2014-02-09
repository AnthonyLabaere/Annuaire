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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "Personne")
@SequenceGenerator(name = "PersonneSequenceGenerator", sequenceName = "PersonneSequence")
public class Personne extends Model {

	/** serial ID */
    private static final long serialVersionUID = 3323426835413150186L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PersonneSequenceGenerator")
	@Column(name = "personne_ID")
	private Integer id;

	@Column(name = "personne_nom")
	private String nom;

	@Column(name = "personne_prenom")
	private String prenom;

	@ManyToOne
	@Column(name = "personne_annee_promotion_ID")
	private AnneePromotion personne;

	@ManyToMany(mappedBy = "personnes", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Entreprise> entreprises = new ArrayList<Entreprise>();

	public Integer getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public AnneePromotion getAnneePromotion() {
		return personne;
	}

	public void setAnneePromotion(AnneePromotion anneePromotion) {
		this.personne = anneePromotion;
	}

	public List<Entreprise> getEntreprises() {
		return entreprises;
	}

	public void setEntreprises(List<Entreprise> entreprises) {
		this.entreprises = entreprises;
	}

}
