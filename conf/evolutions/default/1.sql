------------------------------------------------------------------------------------------------------------------------------
-- Ce script crée les séquences suivantes :
------------------------------------------------------------------------------------------------------------------------------
-- AnneePromotionSequence
-- PersonneSequence
-- EntrepriseSequence
-- PersonneEntrepriseSequence
-- SecteurSequence
-- EntrepriseSecteurSequence
-- PaysSequence
-- VilleSequence
-- EntrepriseVilleSequence
-- EcoleSequence
------------------------------------------------------------------------------------------------------------------------------
-- et les tables suivantes :
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
-- et insere des donnees de test dans les tables suivantes :
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

CREATE SEQUENCE AnneePromotionSequence;
CREATE SEQUENCE PersonneSequence;
CREATE SEQUENCE EntrepriseSequence;
CREATE SEQUENCE PersonneEntrepriseSequence;
CREATE SEQUENCE SecteurSequence;
CREATE SEQUENCE EntrepriseSecteurSequence;
CREATE SEQUENCE PaysSequence;
CREATE SEQUENCE VilleSequence;
CREATE SEQUENCE EntrepriseVilleSequence;
CREATE SEQUENCE EcoleSequence;


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


INSERT INTO Pays (pays_ID, pays_nom) VALUES (nextval('PaysSequence'), 'France');
INSERT INTO Pays (pays_ID, pays_nom) VALUES (nextval('PaysSequence'), 'Espagne');
INSERT INTO Pays (pays_ID, pays_nom) VALUES (nextval('PaysSequence'), 'Allemagne');

INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Nantes', 1);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Paris', 1);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Lille', 1);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Lyon', 1);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Marseille', 1);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Bordeaux', 1);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Toulouse', 1);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Rennes', 1);

# --- !Downs

DROP SEQUENCE IF EXISTS AnneePromotionSequence CASCADE;
DROP SEQUENCE IF EXISTS PersonneSequence CASCADE;
DROP SEQUENCE IF EXISTS EntrepriseSequence CASCADE;
DROP SEQUENCE IF EXISTS PersonneEntrepriseSequence CASCADE;
DROP SEQUENCE IF EXISTS SecteurSequence CASCADE;
DROP SEQUENCE IF EXISTS EntrepriseSecteurSequence CASCADE;
DROP SEQUENCE IF EXISTS PaysSequence CASCADE;
DROP SEQUENCE IF EXISTS VilleSequence CASCADE;
DROP SEQUENCE IF EXISTS EntrepriseVilleSequence CASCADE;
DROP SEQUENCE IF EXISTS EcoleSequence CASCADE;

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