------------------------------------------------------------------------------------------------------------------------------
-- Ce script cree les sequences suivantes :
------------------------------------------------------------------------------------------------------------------------------
-- AnneePromotionSequence
-- CentralienSequence
-- EntrepriseSequence
-- EntrepriseVilleSecteurCentralienSequence
-- SecteurSequence
-- PaysSequence
-- VilleSequence
-- EntrepriseVilleSecteurSequence
-- EcoleSequence
-- EcoleSecteurSequence
-- EcoleSecteurCentralienSequence
------------------------------------------------------------------------------------------------------------------------------
-- Ce script cree les tables suivantes :
------------------------------------------------------------------------------------------------------------------------------
-- AnneePromotion
-- Centralien
-- Entreprise
-- EntrepriseVilleSecteurCentralien
-- Secteur
-- Pays
-- Ville
-- EntrepriseVilleSecteur
-- Ecole
-- EcoleSecteur
-- EcoleSecteurCentralien
------------------------------------------------------------------------------------------------------------------------------
-- Ce script cree les fonctions suivantes :
------------------------------------------------------------------------------------------------------------------------------
-- getCoordonneesSelonPays
------------------------------------------------------------------------------------------------------------------------------
-- Ce script cree insere des donnees de test dans les tables suivantes :
------------------------------------------------------------------------------------------------------------------------------
-- AnneePromotion
-- Centralien
-- Entreprise
-- EntrepriseVilleSecteurCentralien
-- Secteur
-- Pays
-- Ville
-- EntrepriseVilleSecteur
-- Ecole
------------------------------------------------------------------------------------------------------------------------------

# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

------------------------------------------------------------------------------------------------------------------------------
-- Creation des sequences
------------------------------------------------------------------------------------------------------------------------------
CREATE SEQUENCE AnneePromotionSequence;
CREATE SEQUENCE CentralienSequence;
CREATE SEQUENCE EntrepriseSequence;
CREATE SEQUENCE EntrepriseVilleSecteurCentralienSequence;
CREATE SEQUENCE SecteurSequence;
CREATE SEQUENCE PaysSequence;
CREATE SEQUENCE VilleSequence;
CREATE SEQUENCE EntrepriseVilleSecteurSequence;
CREATE SEQUENCE EcoleSequence;
CREATE SEQUENCE EcoleSecteurSequence;
CREATE SEQUENCE EcoleSecteurCentralienSequence;
CREATE SEQUENCE PosteActuelSequence;

------------------------------------------------------------------------------------------------------------------------------
-- Creation des tables
------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE AnneePromotion (
  anneePromotion_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('AnneePromotionSequence'),
  anneePromotion_libelle INTEGER NOT NULL 
);

CREATE TABLE Centralien (
  centralien_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('CentralienSequence'),
  centralien_nom VARCHAR(50) NOT NULL,
  centralien_prenom VARCHAR(50) NOT NULL,
  centralien_anneePromotion_ID INTEGER REFERENCES AnneePromotion (anneePromotion_ID) NOT NULL
);

CREATE TABLE Pays (
  pays_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PaysSequence'),
  pays_nom VARCHAR(50) NOT NULL,
  pays_latitude NUMERIC,
  pays_longitude NUMERIC,
  pays_zoom INTEGER 
);

CREATE TABLE Ville (
  ville_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('VilleSequence'),
  ville_nom VARCHAR(50) NOT NULL,
  ville_pays_ID INTEGER REFERENCES Pays (pays_ID) NOT NULL,
  ville_latitude NUMERIC,
  ville_longitude NUMERIC  
);

CREATE TABLE Entreprise (
  entreprise_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EntrepriseSequence'),
  entreprise_nom VARCHAR(50) NOT NULL
);

CREATE TABLE Secteur (
  secteur_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('SecteurSequence'),
  secteur_nom VARCHAR(50) NOT NULL
);

CREATE TABLE EntrepriseVilleSecteur (
  entrepriseVilleSecteur_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EntrepriseVilleSecteurSequence'),
  entrepriseVilleSecteur_entreprise_ID INTEGER REFERENCES Entreprise (entreprise_ID) NOT NULL,
  entrepriseVilleSecteur_ville_ID INTEGER REFERENCES Ville (ville_ID) NOT NULL,
  entrepriseVilleSecteur_secteur_ID INTEGER REFERENCES Secteur (secteur_ID) NOT NULL
);

CREATE TABLE EntrepriseVilleSecteurCentralien (
  entrepriseVilleSecteurCentralien_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EntrepriseVilleSecteurCentralienSequence'),
  entrepriseVilleSecteurCentralien_centralien_ID INTEGER REFERENCES Centralien (centralien_ID) NOT NULL,
  entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID INTEGER REFERENCES EntrepriseVilleSecteur (entrepriseVilleSecteur_ID) NOT NULL
  );

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

CREATE TABLE EcoleSecteurCentralien (
  ecoleSecteurCentralien_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EcoleSecteurCentralienSequence'),
  ecoleSecteurCentralien_ecoleSecteur_ID INTEGER REFERENCES EcoleSecteur (ecoleSecteur_ID) NOT NULL,
  ecoleSecteurCentralien_centralien_ID INTEGER REFERENCES Centralien (centralien_ID) NOT NULL
);

-- CREATE TABLE PosteActuel (
--  posteActuel_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PosteActuelSequence'),
--   posteActuel_entrepriseCentralien_ID INTEGER REFERENCES EntrepriseCentralien (entrepriseCentralien_ID),
--   posteActuel_ecoleCentralien_ID INTEGER REFERENCES EcoleCentralien (ecoleCentralien_ID),
--   CHECK (posteActuel_ecoleCentralien_ID IS NOT NULL OR posteActuel_ecoleCentralien_ID IS NOT NULL),
--  CHECK (posteActuel_ecoleCentralien_ID IS NULL OR posteActuel_ecoleCentralien_ID IS NULL)
-- );

------------------------------------------------------------------------------------------------------------------------------
-- Creation des fonctions
------------------------------------------------------------------------------------------------------------------------------
-- CREATE OR REPLACE FUNCTION getCoordonneesSelonPays ()
-- AS 'GeocoderUtil.test()'
-- LANGUAGE java;

------------------------------------------------------------------------------------------------------------------------------
-- Insertion des donnees
------------------------------------------------------------------------------------------------------------------------------
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2003);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2004);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2005);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2006);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2007);--5
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2008);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2009);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2010);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2011);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2012);--10
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2013);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2014);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2015);
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES (nextval('AnneePromotionSequence'), 2016);--14

INSERT INTO Centralien (centralien_ID, centralien_prenom, centralien_nom,  centralien_anneePromotion_ID) VALUES (nextval('CentralienSequence'), 'Dupont', 'Dupond', 9);
INSERT INTO Centralien (centralien_ID, centralien_prenom, centralien_nom, centralien_anneePromotion_ID) VALUES (nextval('CentralienSequence'), 'Robert', 'Dupond', 10);
INSERT INTO Centralien (centralien_ID, centralien_prenom, centralien_nom, centralien_anneePromotion_ID) VALUES (nextval('CentralienSequence'), 'Albert', 'Dupond', 10);
INSERT INTO Centralien (centralien_ID, centralien_prenom, centralien_nom, centralien_anneePromotion_ID) VALUES (nextval('CentralienSequence'), 'Francis', 'Dupond', 11);
INSERT INTO Centralien (centralien_ID, centralien_prenom, centralien_nom, centralien_anneePromotion_ID) VALUES (nextval('CentralienSequence'), 'Benjamin', 'Dupond', 8);--5
INSERT INTO Centralien (centralien_ID, centralien_prenom, centralien_nom, centralien_anneePromotion_ID) VALUES (nextval('CentralienSequence'), 'Anthony', 'Dupond', 12);
INSERT INTO Centralien (centralien_ID, centralien_prenom, centralien_nom, centralien_anneePromotion_ID) VALUES (nextval('CentralienSequence'), 'Indianna', 'Jones', 1);
INSERT INTO Centralien (centralien_ID, centralien_prenom, centralien_nom, centralien_anneePromotion_ID) VALUES (nextval('CentralienSequence'), 'Black', 'MrCafe', 1);--8

-- TODO : Latitude et longitude doivent etre insere par un webservice ou une API, le zoom est a definir a la main...
INSERT INTO Pays (pays_ID, pays_nom, pays_latitude, pays_longitude, pays_zoom) VALUES (nextval('PaysSequence'), 'France', 46.0558887, 1.8142930, 6);
INSERT INTO Pays (pays_ID, pays_nom, pays_latitude, pays_longitude, pays_zoom) VALUES (nextval('PaysSequence'), 'Espagne', 40.4636670, -3.7492200, 6);
INSERT INTO Pays (pays_ID, pays_nom, pays_latitude, pays_longitude, pays_zoom) VALUES (nextval('PaysSequence'), 'Allemagne', 51.1656910, 10.4515260, 6);

-- TODO : Latitude et longitude doivent etre insere par un webservice ou une API
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID, ville_latitude, ville_longitude) VALUES (nextval('VilleSequence'), 'Nantes', 1, 47.2183710, -1.5536210);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID, ville_latitude, ville_longitude) VALUES (nextval('VilleSequence'), 'Paris', 1, 48.8566140, 2.3522219);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID, ville_latitude, ville_longitude) VALUES (nextval('VilleSequence'), 'Lille', 1, 50.6292500, 3.0572560);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID, ville_latitude, ville_longitude) VALUES (nextval('VilleSequence'), 'Lyon', 1, 45.7640430, 4.8356590);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID, ville_latitude, ville_longitude) VALUES (nextval('VilleSequence'), 'Marseille', 1, 43.2964820, 5.3697800);--5
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID, ville_latitude, ville_longitude) VALUES (nextval('VilleSequence'), 'Bordeaux', 1, 44.8377890, -0.5791800);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID, ville_latitude, ville_longitude) VALUES (nextval('VilleSequence'), 'Toulouse', 1, 43.6046520, 1.4442090);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID, ville_latitude, ville_longitude) VALUES (nextval('VilleSequence'), 'Rennes', 1, 48.1134750, -1.6757080);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID, ville_latitude, ville_longitude) VALUES (nextval('VilleSequence'), 'Madrid', 2, 40.4167754, -0.1198244);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID, ville_latitude, ville_longitude) VALUES (nextval('VilleSequence'), 'Berlin', 3, 52.5200066, 13.4049540);--10

INSERT INTO Entreprise (entreprise_ID, entreprise_nom) VALUES (nextval('EntrepriseSequence'), 'Sopra');
INSERT INTO Entreprise (entreprise_ID, entreprise_nom) VALUES (nextval('EntrepriseSequence'), 'Capgemini');
INSERT INTO Entreprise (entreprise_ID, entreprise_nom) VALUES (nextval('EntrepriseSequence'), 'Atos');
INSERT INTO Entreprise (entreprise_ID, entreprise_nom) VALUES (nextval('EntrepriseSequence'), 'Solucom');
INSERT INTO Entreprise (entreprise_ID, entreprise_nom) VALUES (nextval('EntrepriseSequence'), 'Centrale Nantes Lab');--5
INSERT INTO Entreprise (entreprise_ID, entreprise_nom) VALUES (nextval('EntrepriseSequence'), 'Café du coin');

INSERT INTO Secteur (secteur_ID, secteur_nom) VALUES (nextval('SecteurSequence'), 'Système d''information');
INSERT INTO Secteur (secteur_ID, secteur_nom) VALUES (nextval('SecteurSequence'), 'Consulting');
INSERT INTO Secteur (secteur_ID, secteur_nom) VALUES (nextval('SecteurSequence'), 'Recherche');
INSERT INTO Secteur (secteur_ID, secteur_nom) VALUES (nextval('SecteurSequence'), 'Percolation');

INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 1, 1);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 1, 2);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 2, 1);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 2, 2);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 3, 1);--5
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 3, 2);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 4, 1);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 4, 2);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 5, 1);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 5, 2);--10
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 6, 1);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 6, 2);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 7, 1);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 7, 2);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 8, 1);--15
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 1, 8, 2);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 2, 1, 1);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 2, 2, 2);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 2, 3, 1);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 2, 4, 1);--20
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 2, 5, 2);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 2, 7, 1);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 2, 8, 1);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 3, 1, 1);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 3, 1, 2);--25
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 3, 2, 2);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 3, 3, 1);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 3, 3, 2);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 3, 4, 2);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 3, 5, 1);--30
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 3, 6, 1);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 3, 6, 2);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 4, 2, 1);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 4, 2, 2);
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 5, 1, 3);--35
INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES (nextval('EntrepriseVilleSecteurSequence'), 6, 1, 4);

INSERT INTO EntrepriseVilleSecteurCentralien (entrepriseVilleSecteurCentralien_ID, entrepriseVilleSecteurCentralien_centralien_ID, entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID) VALUES (nextval('EntrepriseVilleSecteurCentralienSequence'), 1, 1);
INSERT INTO EntrepriseVilleSecteurCentralien (entrepriseVilleSecteurCentralien_ID, entrepriseVilleSecteurCentralien_centralien_ID, entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID) VALUES (nextval('EntrepriseVilleSecteurCentralienSequence'), 2, 19);
INSERT INTO EntrepriseVilleSecteurCentralien (entrepriseVilleSecteurCentralien_ID, entrepriseVilleSecteurCentralien_centralien_ID, entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID) VALUES (nextval('EntrepriseVilleSecteurCentralienSequence'), 3, 18);
INSERT INTO EntrepriseVilleSecteurCentralien (entrepriseVilleSecteurCentralien_ID, entrepriseVilleSecteurCentralien_centralien_ID, entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID) VALUES (nextval('EntrepriseVilleSecteurCentralienSequence'), 4, 26);
INSERT INTO EntrepriseVilleSecteurCentralien (entrepriseVilleSecteurCentralien_ID, entrepriseVilleSecteurCentralien_centralien_ID, entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID) VALUES (nextval('EntrepriseVilleSecteurCentralienSequence'), 5, 27);
INSERT INTO EntrepriseVilleSecteurCentralien (entrepriseVilleSecteurCentralien_ID, entrepriseVilleSecteurCentralien_centralien_ID, entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID) VALUES (nextval('EntrepriseVilleSecteurCentralienSequence'), 6, 33);
INSERT INTO EntrepriseVilleSecteurCentralien (entrepriseVilleSecteurCentralien_ID, entrepriseVilleSecteurCentralien_centralien_ID, entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID) VALUES (nextval('EntrepriseVilleSecteurCentralienSequence'), 7, 26);
INSERT INTO EntrepriseVilleSecteurCentralien (entrepriseVilleSecteurCentralien_ID, entrepriseVilleSecteurCentralien_centralien_ID, entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID) VALUES (nextval('EntrepriseVilleSecteurCentralienSequence'), 8, 36);

INSERT INTO Ecole (ecole_ID, ecole_nom, ecole_ville_ID) VALUES (nextval('EcoleSequence'), 'Centrale Nantes', 1);
INSERT INTO Ecole (ecole_ID, ecole_nom, ecole_ville_ID) VALUES (nextval('EcoleSequence'), 'Audencia', 1);
INSERT INTO Ecole (ecole_ID, ecole_nom, ecole_ville_ID) VALUES (nextval('EcoleSequence'), 'Oniris', 1);
INSERT INTO Ecole (ecole_ID, ecole_nom, ecole_ville_ID) VALUES (nextval('EcoleSequence'), 'Lycée Faidherbe', 3);
INSERT INTO Ecole (ecole_ID, ecole_nom, ecole_ville_ID) VALUES (nextval('EcoleSequence'), 'ENA', 2);
INSERT INTO Ecole (ecole_ID, ecole_nom, ecole_ville_ID) VALUES (nextval('EcoleSequence'), 'ESB', 1);--6

INSERT INTO EcoleSecteur (ecoleSecteur_ID, ecoleSecteur_ecole_ID, ecoleSecteur_secteur_ID) VALUES (nextval('EcoleSecteurSequence'),1,1);
INSERT INTO EcoleSecteur (ecoleSecteur_ID, ecoleSecteur_ecole_ID, ecoleSecteur_secteur_ID) VALUES (nextval('EcoleSecteurSequence'),1,3);
INSERT INTO EcoleSecteur (ecoleSecteur_ID, ecoleSecteur_ecole_ID, ecoleSecteur_secteur_ID) VALUES (nextval('EcoleSecteurSequence'),2,3);
INSERT INTO EcoleSecteur (ecoleSecteur_ID, ecoleSecteur_ecole_ID, ecoleSecteur_secteur_ID) VALUES (nextval('EcoleSecteurSequence'),3,3);
INSERT INTO EcoleSecteur (ecoleSecteur_ID, ecoleSecteur_ecole_ID, ecoleSecteur_secteur_ID) VALUES (nextval('EcoleSecteurSequence'),4,1);--5
INSERT INTO EcoleSecteur (ecoleSecteur_ID, ecoleSecteur_ecole_ID, ecoleSecteur_secteur_ID) VALUES (nextval('EcoleSecteurSequence'),4,3);
INSERT INTO EcoleSecteur (ecoleSecteur_ID, ecoleSecteur_ecole_ID, ecoleSecteur_secteur_ID) VALUES (nextval('EcoleSecteurSequence'),5,3);
INSERT INTO EcoleSecteur (ecoleSecteur_ID, ecoleSecteur_ecole_ID, ecoleSecteur_secteur_ID) VALUES (nextval('EcoleSecteurSequence'),6,3);

INSERT INTO EcoleSecteurCentralien (ecoleSecteurCentralien_ID, ecoleSecteurCentralien_ecoleSecteur_ID, ecoleSecteurCentralien_centralien_ID) VALUES (nextval('EcoleSecteurCentralienSequence'),1,1);
INSERT INTO EcoleSecteurCentralien (ecoleSecteurCentralien_ID, ecoleSecteurCentralien_ecoleSecteur_ID, ecoleSecteurCentralien_centralien_ID) VALUES (nextval('EcoleSecteurCentralienSequence'),5,1);
INSERT INTO EcoleSecteurCentralien (ecoleSecteurCentralien_ID, ecoleSecteurCentralien_ecoleSecteur_ID, ecoleSecteurCentralien_centralien_ID) VALUES (nextval('EcoleSecteurCentralienSequence'),2,4);
INSERT INTO EcoleSecteurCentralien (ecoleSecteurCentralien_ID, ecoleSecteurCentralien_ecoleSecteur_ID, ecoleSecteurCentralien_centralien_ID) VALUES (nextval('EcoleSecteurCentralienSequence'),3,5);
INSERT INTO EcoleSecteurCentralien (ecoleSecteurCentralien_ID, ecoleSecteurCentralien_ecoleSecteur_ID, ecoleSecteurCentralien_centralien_ID) VALUES (nextval('EcoleSecteurCentralienSequence'),4,6);


# --- !Downs

------------------------------------------------------------------------------------------------------------------------------
-- Suppression des sequences
------------------------------------------------------------------------------------------------------------------------------
DROP SEQUENCE IF EXISTS AnneePromotionSequence CASCADE;
DROP SEQUENCE IF EXISTS CentralienSequence CASCADE;
DROP SEQUENCE IF EXISTS EntrepriseSequence CASCADE;
DROP SEQUENCE IF EXISTS EntrepriseVilleSecteurCentralienSequence CASCADE;
DROP SEQUENCE IF EXISTS SecteurSequence CASCADE;
DROP SEQUENCE IF EXISTS PaysSequence CASCADE;
DROP SEQUENCE IF EXISTS VilleSequence CASCADE;
DROP SEQUENCE IF EXISTS EntrepriseVilleSecteurSequence CASCADE;
DROP SEQUENCE IF EXISTS EcoleSequence CASCADE;
DROP SEQUENCE IF EXISTS EcoleSecteurSequence CASCADE;
DROP SEQUENCE IF EXISTS EcoleSecteurCentralienSequence CASCADE;
DROP SEQUENCE IF EXISTS PosteActuelSequence CASCADE;

------------------------------------------------------------------------------------------------------------------------------
-- Suppression des fonctions
------------------------------------------------------------------------------------------------------------------------------
DROP FUNCTION IF EXISTS getCoordonneesSelonPays;

------------------------------------------------------------------------------------------------------------------------------
-- Suppression des tables
------------------------------------------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS AnneePromotion CASCADE;
DROP TABLE IF EXISTS Centralien CASCADE;
DROP TABLE IF EXISTS Entreprise CASCADE;
DROP TABLE IF EXISTS EntrepriseVilleSecteurCentralien CASCADE;
DROP TABLE IF EXISTS Secteur CASCADE;
DROP TABLE IF EXISTS Pays CASCADE;
DROP TABLE IF EXISTS Ville CASCADE;
DROP TABLE IF EXISTS EntrepriseVilleSecteur CASCADE;
DROP TABLE IF EXISTS Ecole CASCADE; 
DROP TABLE IF EXISTS EcoleSecteur CASCADE; 
DROP TABLE IF EXISTS EcoleSecteurCentralien CASCADE; 
DROP TABLE IF EXISTS PosteActuel CASCADE; 
