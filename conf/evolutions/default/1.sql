------------------------------------------------------------------------------------------------------------------------------
-- Ce script crée les séquences suivantes :
------------------------------------------------------------------------------------------------------------------------------
-- AnneePromotionSequence
-- PersonneSequence
-- EntrepriseSequence
-- EntreprisePersonneSequence
-- SecteurSequence
-- EntrepriseSecteurSequence
-- PaysSequence
-- VilleSequence
-- EntrepriseVilleSequence
-- EcoleSequence
-- EcoleSecteurSequence
-- EcolePersonneSequence
-- PosteActuelSequence
------------------------------------------------------------------------------------------------------------------------------
-- et les tables suivantes :
------------------------------------------------------------------------------------------------------------------------------
-- AnneePromotion
-- Personne
-- Entreprise
-- EntreprisePersonne
-- Secteur
-- EntrepriseSecteur
-- Pays
-- Ville
-- EntrepriseVille
-- Ecole
-- EcoleSecteur
-- EcolePersonne
-- PosteActuel
------------------------------------------------------------------------------------------------------------------------------
-- et insere des donnees de test dans les tables suivantes :
------------------------------------------------------------------------------------------------------------------------------
-- AnneePromotion
-- Personne
-- Entreprise
-- EntreprisePersonne
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
CREATE SEQUENCE EntreprisePersonneSequence;
CREATE SEQUENCE SecteurSequence;
CREATE SEQUENCE EntrepriseSecteurSequence;
CREATE SEQUENCE PaysSequence;
CREATE SEQUENCE VilleSequence;
CREATE SEQUENCE EntrepriseVilleSequence;
CREATE SEQUENCE EcoleSequence;
CREATE SEQUENCE EcoleSecteurSequence;
CREATE SEQUENCE EcolePersonneSequence;
CREATE SEQUENCE PosteActuelSequence;


CREATE TABLE AnneePromotion (
  anneePromotion_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('AnneePromotionSequence'),
  anneePromotion_libelle INTEGER NOT NULL 
);

CREATE TABLE Personne (
  personne_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PersonneSequence'),
  personne_nom VARCHAR(50) NOT NULL,
  personne_prenom VARCHAR(50) NOT NULL,
  personne_anneePromotion_ID INTEGER REFERENCES AnneePromotion (anneePromotion_ID) NOT NULL
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

CREATE TABLE Entreprise (
  entreprise_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EntrepriseSequence'),
  entreprise_nom VARCHAR(50) NOT NULL
);

CREATE TABLE Secteur (
  secteur_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('SecteurSequence'),
  secteur_nom VARCHAR(50) NOT NULL
);

--TODO contraintes pour entreprisePersonne_secteur_ID, entreprisePersonne_ville_ID
CREATE TABLE EntreprisePersonne (
  entreprisePersonne_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EntreprisePersonneSequence'),
  entreprisePersonne_personne_ID INTEGER REFERENCES Personne (personne_ID) NOT NULL,
  entreprisePersonne_entreprise_ID INTEGER REFERENCES Entreprise (entreprise_ID) NOT NULL,
  entreprisePersonne_secteur_ID INTEGER REFERENCES Secteur (secteur_ID) NOT NULL,
  entreprisePersonne_ville_ID INTEGER REFERENCES Ville (ville_ID) NOT NULL
  );

-- ALTER TABLE EntreprisePersonne 
--    ADD CONSTRAINT CK_EntreprisePersonne_Pays 
--       CHECK (entreprisePersonne_pays_ID IN (SELECT DISTINCT ville_pays_ID
--                                             FROM Ville
--                                             WHERE ville_ID IN (SELECT entrepriseVille_ville_ID
--                                                                FROM EntrepriseVille
--                                                                WHERE entreprisePersonne_entreprise_ID = entreprisePersonne_entreprise_ID) ) )

CREATE TABLE EntrepriseSecteur (
  entrepriseSecteur_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EntrepriseSecteurSequence'),
  entrepriseSecteur_entreprise_ID INTEGER REFERENCES Entreprise (entreprise_ID) NOT NULL,
  entrepriseSecteur_secteur_ID INTEGER REFERENCES Secteur (secteur_ID) NOT NULL
);

CREATE TABLE EntrepriseVille (
  entrepriseVille_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EntrepriseVilleSequence'),
  entrepriseVille_entreprise_ID INTEGER REFERENCES Entreprise (entreprise_ID) NOT NULL,
  entrepriseVille_ville_ID INTEGER REFERENCES Ville (ville_ID) NOT NULL
);

--CREATE TABLE EntrepriseVilleSecteur (
--  entrepriseVilleSecteur_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EntrepriseVilleSecteurSequence'),
--  entrepriseVilleSecteur_entreprise_ID INTEGER REFERENCES Entreprise (entreprise_ID) NOT NULL,
--  entrepriseVilleSecteur_ville_ID INTEGER REFERENCES Ville (ville_ID) NOT NULL,
--  entrepriseVilleSecteur_secteur_ID INTEGER REFERENCES Secteur (secteur_ID) NOT NULL
--);

CREATE TABLE Ecole (
  ecole_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EcoleSequence'),
  ecole_nom VARCHAR(50) NOT NULL,
  ecole_ville_ID INTEGER REFERENCES Ville (ville_ID) NOT NULL
);

CREATE TABLE EcoleSecteur (
  ecoleSecteur_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EcoleSecteurSequence'),
  ecoleSecteur_ecole_ID INTEGER REFERENCES Ecole (ecole_ID) NOT NULL,
  ecoleSecteur_secteur_ID INTEGER REFERENCES Secteur (secteur_ID) NOT NULL
);

--TODO contraintes pour ecolePersonne_secteur_ID
CREATE TABLE EcolePersonne (
  ecolePersonne_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EcolePersonneSequence'),
  ecolePersonne_ecole_ID INTEGER REFERENCES Ecole (ecole_ID) NOT NULL,
  ecolePersonne_personne_ID INTEGER REFERENCES Personne (personne_ID) NOT NULL,
  ecolePersonne_secteur_ID INTEGER REFERENCES Secteur (secteur_ID) NOT NULL
);

CREATE TABLE PosteActuel (
  posteActuel_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PosteActuelSequence'),
  posteActuel_entreprisePersonne_ID INTEGER REFERENCES EntreprisePersonne (entreprisePersonne_ID),
  posteActuel_ecolePersonne_ID INTEGER REFERENCES EcolePersonne (ecolePersonne_ID),
  CHECK (posteActuel_ecolePersonne_ID IS NOT NULL OR posteActuel_ecolePersonne_ID IS NOT NULL),
  CHECK (posteActuel_ecolePersonne_ID IS NULL OR posteActuel_ecolePersonne_ID IS NULL)
);

INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2003);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2004);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2005);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2006);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2007);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2008);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2009);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2010);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2011);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2012);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2013);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2014);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2015);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2016);--14

INSERT INTO Personne (personne_ID, personne_nom, personne_prenom, personne_anneePromotion_ID) VALUES (nextval('PersonneSequence'), 'Dupont', 'Dupond', 9);
INSERT INTO Personne (personne_ID, personne_nom, personne_prenom, personne_anneePromotion_ID) VALUES (nextval('PersonneSequence'), 'Robert', 'Dupond', 10);
INSERT INTO Personne (personne_ID, personne_nom, personne_prenom, personne_anneePromotion_ID) VALUES (nextval('PersonneSequence'), 'Albert', 'Dupond', 10);
INSERT INTO Personne (personne_ID, personne_nom, personne_prenom, personne_anneePromotion_ID) VALUES (nextval('PersonneSequence'), 'Francis', 'Dupond', 11);
INSERT INTO Personne (personne_ID, personne_nom, personne_prenom, personne_anneePromotion_ID) VALUES (nextval('PersonneSequence'), 'Benjamin', 'Dupond', 8);
INSERT INTO Personne (personne_ID, personne_nom, personne_prenom, personne_anneePromotion_ID) VALUES (nextval('PersonneSequence'), 'Anthony', 'Dupond', 12);
INSERT INTO Personne (personne_ID, personne_nom, personne_prenom, personne_anneePromotion_ID) VALUES (nextval('PersonneSequence'), 'Indianna', 'Jones', 1);--7

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
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Madrid', 2);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Berlin', 3);--10

INSERT INTO Entreprise (entreprise_ID, entreprise_nom) VALUES (nextval('EntrepriseSequence'), 'Sopra');
INSERT INTO Entreprise (entreprise_ID, entreprise_nom) VALUES (nextval('EntrepriseSequence'), 'Capgemini');
INSERT INTO Entreprise (entreprise_ID, entreprise_nom) VALUES (nextval('EntrepriseSequence'), 'Atos');
INSERT INTO Entreprise (entreprise_ID, entreprise_nom) VALUES (nextval('EntrepriseSequence'), 'Solucom');
INSERT INTO Entreprise (entreprise_ID, entreprise_nom) VALUES (nextval('EntrepriseSequence'), 'Centrale Nantes Lab');
INSERT INTO Entreprise (entreprise_ID, entreprise_nom) VALUES (nextval('EntrepriseSequence'), 'Café du coin');--6

INSERT INTO Secteur (secteur_ID, secteur_nom) VALUES (nextval('SecteurSequence'), 'Système d''information');
INSERT INTO Secteur (secteur_ID, secteur_nom) VALUES (nextval('SecteurSequence'), 'Consulting');
INSERT INTO Secteur (secteur_ID, secteur_nom) VALUES (nextval('SecteurSequence'), 'Recherche');
INSERT INTO Secteur (secteur_ID, secteur_nom) VALUES (nextval('SecteurSequence'), 'Percolation');

INSERT INTO EntreprisePersonne (entreprisePersonne_ID, entreprisePersonne_personne_ID, entreprisePersonne_entreprise_ID, entreprisePersonne_secteur_ID, entreprisePersonne_ville_ID) VALUES (nextval('entreprisePersonneSequence'), 1, 1, 1, 1);
INSERT INTO EntreprisePersonne (entreprisePersonne_ID, entreprisePersonne_personne_ID, entreprisePersonne_entreprise_ID, entreprisePersonne_secteur_ID, entreprisePersonne_ville_ID) VALUES (nextval('entreprisePersonneSequence'), 2, 2, 1, 3);
INSERT INTO EntreprisePersonne (entreprisePersonne_ID, entreprisePersonne_personne_ID, entreprisePersonne_entreprise_ID, entreprisePersonne_secteur_ID, entreprisePersonne_ville_ID) VALUES (nextval('entreprisePersonneSequence'), 3, 2, 1, 2);
INSERT INTO EntreprisePersonne (entreprisePersonne_ID, entreprisePersonne_personne_ID, entreprisePersonne_entreprise_ID, entreprisePersonne_secteur_ID, entreprisePersonne_ville_ID) VALUES (nextval('entreprisePersonneSequence'), 4, 3, 1, 2);
INSERT INTO EntreprisePersonne (entreprisePersonne_ID, entreprisePersonne_personne_ID, entreprisePersonne_entreprise_ID, entreprisePersonne_secteur_ID, entreprisePersonne_ville_ID) VALUES (nextval('entreprisePersonneSequence'), 5, 3, 1, 2);
INSERT INTO EntreprisePersonne (entreprisePersonne_ID, entreprisePersonne_personne_ID, entreprisePersonne_entreprise_ID, entreprisePersonne_secteur_ID, entreprisePersonne_ville_ID) VALUES (nextval('entreprisePersonneSequence'), 6, 4, 1, 1);
INSERT INTO EntreprisePersonne (entreprisePersonne_ID, entreprisePersonne_personne_ID, entreprisePersonne_entreprise_ID, entreprisePersonne_secteur_ID, entreprisePersonne_ville_ID) VALUES (nextval('entreprisePersonneSequence'), 7, 6, 4, 1);

INSERT INTO EntrepriseSecteur (entrepriseSecteur_ID, entrepriseSecteur_entreprise_ID, entrepriseSecteur_secteur_ID) VALUES (nextval('EntrepriseSecteurSequence'), 1, 1);
INSERT INTO EntrepriseSecteur (entrepriseSecteur_ID, entrepriseSecteur_entreprise_ID, entrepriseSecteur_secteur_ID) VALUES (nextval('EntrepriseSecteurSequence'), 1, 2);
INSERT INTO EntrepriseSecteur (entrepriseSecteur_ID, entrepriseSecteur_entreprise_ID, entrepriseSecteur_secteur_ID) VALUES (nextval('EntrepriseSecteurSequence'), 2, 1);
INSERT INTO EntrepriseSecteur (entrepriseSecteur_ID, entrepriseSecteur_entreprise_ID, entrepriseSecteur_secteur_ID) VALUES (nextval('EntrepriseSecteurSequence'), 2, 2);
INSERT INTO EntrepriseSecteur (entrepriseSecteur_ID, entrepriseSecteur_entreprise_ID, entrepriseSecteur_secteur_ID) VALUES (nextval('EntrepriseSecteurSequence'), 3, 1);
INSERT INTO EntrepriseSecteur (entrepriseSecteur_ID, entrepriseSecteur_entreprise_ID, entrepriseSecteur_secteur_ID) VALUES (nextval('EntrepriseSecteurSequence'), 3, 2);
INSERT INTO EntrepriseSecteur (entrepriseSecteur_ID, entrepriseSecteur_entreprise_ID, entrepriseSecteur_secteur_ID) VALUES (nextval('EntrepriseSecteurSequence'), 4, 1);
INSERT INTO EntrepriseSecteur (entrepriseSecteur_ID, entrepriseSecteur_entreprise_ID, entrepriseSecteur_secteur_ID) VALUES (nextval('EntrepriseSecteurSequence'), 4, 2);
INSERT INTO EntrepriseSecteur (entrepriseSecteur_ID, entrepriseSecteur_entreprise_ID, entrepriseSecteur_secteur_ID) VALUES (nextval('EntrepriseSecteurSequence'), 5, 3);
INSERT INTO EntrepriseSecteur (entrepriseSecteur_ID, entrepriseSecteur_entreprise_ID, entrepriseSecteur_secteur_ID) VALUES (nextval('EntrepriseSecteurSequence'), 6, 4);

INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 1, 1);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 1, 2);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 1, 3);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 1, 4);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 1, 5);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 1, 6);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 1, 7);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 1, 8);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 2, 1);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 2, 2);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 2, 3);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 2, 4);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 2, 5);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 2, 7);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 2, 8);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 3, 1);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 3, 2);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 3, 3);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 3, 4);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 3, 5);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 3, 6);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 4, 2);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 5, 1);
INSERT INTO EntrepriseVille (entrepriseVille_ID, entrepriseVille_entreprise_ID, entrepriseVille_ville_ID) VALUES (nextval('EntrepriseVilleSequence'), 6, 1);

INSERT INTO Ecole (ecole_ID, ecole_nom, ecole_ville_ID) VALUES (nextval('EcoleSequence'), 'Centrale Nantes', 1);
INSERT INTO Ecole (ecole_ID, ecole_nom, ecole_ville_ID) VALUES (nextval('EcoleSequence'), 'Audencia', 1);
INSERT INTO Ecole (ecole_ID, ecole_nom, ecole_ville_ID) VALUES (nextval('EcoleSequence'), 'Oniris', 1);
INSERT INTO Ecole (ecole_ID, ecole_nom, ecole_ville_ID) VALUES (nextval('EcoleSequence'), 'Lycée Faidherbe', 3);
INSERT INTO Ecole (ecole_ID, ecole_nom, ecole_ville_ID) VALUES (nextval('EcoleSequence'), 'ENA', 2);

# --- !Downs

DROP SEQUENCE IF EXISTS AnneePromotionSequence CASCADE;
DROP SEQUENCE IF EXISTS PersonneSequence CASCADE;
DROP SEQUENCE IF EXISTS EntrepriseSequence CASCADE;
DROP SEQUENCE IF EXISTS EntreprisePersonneSequence CASCADE;
DROP SEQUENCE IF EXISTS SecteurSequence CASCADE;
DROP SEQUENCE IF EXISTS EntrepriseSecteurSequence CASCADE;
DROP SEQUENCE IF EXISTS PaysSequence CASCADE;
DROP SEQUENCE IF EXISTS VilleSequence CASCADE;
DROP SEQUENCE IF EXISTS EntrepriseVilleSequence CASCADE;
DROP SEQUENCE IF EXISTS EcoleSequence CASCADE;
DROP SEQUENCE IF EXISTS EcoleSecteurSequence CASCADE;
DROP SEQUENCE IF EXISTS EcolePersonneSequence CASCADE;
DROP SEQUENCE IF EXISTS PosteActuelSequence CASCADE;

DROP TABLE IF EXISTS AnneePromotion CASCADE;
DROP TABLE IF EXISTS Personne CASCADE;
DROP TABLE IF EXISTS Entreprise CASCADE;
DROP TABLE IF EXISTS EntreprisePersonne CASCADE;
DROP TABLE IF EXISTS Secteur CASCADE;
DROP TABLE IF EXISTS EntrepriseSecteur CASCADE;
DROP TABLE IF EXISTS Pays CASCADE;
DROP TABLE IF EXISTS Ville CASCADE;
DROP TABLE IF EXISTS EntrepriseVille CASCADE;
DROP TABLE IF EXISTS Ecole CASCADE; 
DROP TABLE IF EXISTS EcoleSecteur CASCADE; 
DROP TABLE IF EXISTS EcolePersonne CASCADE; 
DROP TABLE IF EXISTS PosteActuel CASCADE; 
