------------------------------------------------------------------------------------------------------------------------------
-- Ce script crée les tables suivantes :
------------------------------------------------------------------------------------------------------------------------------
-- AnneePromotion
-- Personne
-- Entreprise
-- PersonneEntreprise
-- Secteur
-- EntrepriseSecteur
-- Pays
-- Ville
-- EntrepriseVille
-- Ecole
------------------------------------------------------------------------------------------------------------------------------

# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

CREATE TABLE AnneePromotion (
  anneePromotion_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('AnneePromotionSequence'),
  anneePromotion_libelle VARCHAR(50) NOT NULL 
);

CREATE TABLE Personne (
  personne_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PersonneSequence'),
  personne_nom VARCHAR(50) NOT NULL,
  personne_prenom VARCHAR(50) NOT NULL,
  personne_annee_promotion_ID INTEGER REFERENCES AnneePromotion (anneePromotion_ID) NOT NULL
);

CREATE TABLE Entreprise (
  entreprise_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EntrepriseSequence'),
  entreprise_nom VARCHAR(50) NOT NULL
);

CREATE TABLE PersonneEntreprise (
  personneEntreprise_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PersonneEntrepriseSequence'),
  personneEntreprise_personne_ID INTEGER REFERENCES Personne (personne_ID) NOT NULL,
  personneEntreprise_entreprise_ID INTEGER REFERENCES Entreprise (entreprise_ID) NOT NULL
);

CREATE TABLE Secteur (
  secteur_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('SecteurSequence'),
  secteur_nom VARCHAR(50) NOT NULL
);

CREATE TABLE EntrepriseSecteur (
  entrepriseSecteur_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EntrepriseSecteurSequence'),
  entrepriseSecteur_entreprise_ID INTEGER REFERENCES Entreprise (entreprise_ID) NOT NULL,
  entrepriseSecteur_secteur_ID INTEGER REFERENCES Secteur (secteur_ID) NOT NULL
);

CREATE TABLE Pays (
  pays_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PaysSequence'),
  pays_nom VARCHAR(50) NOT NULL
);

CREATE TABLE Ville (
  ville_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('VilleSequence'),
  ville_nom VARCHAR(50) NOT NULL,
  ville_pays_ID INTEGER REFERENCES Pays (pays_ID) NOT NULL
);

CREATE TABLE EntrepriseVille (
  entrepriseVille_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EntrepriseVilleSequence'),
  entrepriseVille_entreprise_ID INTEGER REFERENCES Entreprise (entreprise_ID) NOT NULL,
  entrepriseVille_ville_ID INTEGER REFERENCES Ville (ville_ID) NOT NULL
);

CREATE TABLE Ecole (
  ecole_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EcoleSequence'),
  ecole_nom VARCHAR(50) NOT NULL,
  ecole_ville_ID INTEGER REFERENCES Entreprise (entreprise_ID) NOT NULL
);

# --- !Downs

DROP TABLE IF EXISTS AnneePromotion CASCADE;
DROP TABLE IF EXISTS Personne CASCADE;
DROP TABLE IF EXISTS Entreprise CASCADE;
DROP TABLE IF EXISTS PersonneEntreprise CASCADE;
DROP TABLE IF EXISTS Secteur CASCADE;
DROP TABLE IF EXISTS EntrepriseSecteur CASCADE;
DROP TABLE IF EXISTS Pays CASCADE;
DROP TABLE IF EXISTS Ville CASCADE;
DROP TABLE IF EXISTS EntrepriseVille CASCADE;
DROP TABLE IF EXISTS Ecole CASCADE; 

