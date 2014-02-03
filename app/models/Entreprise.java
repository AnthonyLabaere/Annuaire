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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "Entreprise")
@SequenceGenerator(name = "EntrepriseSequenceGenerator", sequenceName = "EntrepriseSequence")
public class Entreprise extends Model {

	/** serial ID */
	private static final long serialVersionUID = -771052943631292213L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EntrepriseSequenceGenerator")
	@Column(name = "entreprise_ID")
	private Integer id;

	@Column(name = "entreprise_nom")
	private String nom;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "EntreprisePersonne", joinColumns = { @JoinColumn(name = "entreprisePersonne_personne_ID", referencedColumnName = "entreprise_ID") }, inverseJoinColumns = { @JoinColumn(name = "entreprisePersonne_entreprise_ID", referencedColumnName = "personne_ID") })
	private List<Personne> personnes = new ArrayList<Personne>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "EntrepriseSecteur", joinColumns = { @JoinColumn(name = "entrepriseSecteur_entreprise_ID", referencedColumnName = "entreprise_ID") }, inverseJoinColumns = { @JoinColumn(name = "entrepriseSecteur_secteur_ID", referencedColumnName = "secteur_ID") })
	private List<Secteur> secteurs = new ArrayList<Secteur>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "EntrepriseVille", joinColumns = { @JoinColumn(name = "entrepriseVille_entreprise_ID", referencedColumnName = "entreprise_ID") }, inverseJoinColumns = { @JoinColumn(name = "entrepriseVille_ville_ID", referencedColumnName = "ville_ID") })
	private List<Ville> villes = new ArrayList<Ville>();

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getId() {
		return id;
	}

	public List<Personne> getPersonnes() {
		return personnes;
	}

	public void setPersonnes(List<Personne> personnes) {
		this.personnes = personnes;
	}

	public List<Secteur> getSecteurs() {
		return secteurs;
	}

	public void setSecteurs(List<Secteur> secteurs) {
		this.secteurs = secteurs;
	}

	public List<Ville> getVilles() {
		return villes;
	}

	public void setVilles(List<Ville> villes) {
		this.villes = villes;
	}

}
