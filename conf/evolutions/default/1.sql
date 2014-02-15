------------------------------------------------------------------------------------------------------------------------------
-- Ce script crée les séquences suivantes :
------------------------------------------------------------------------------------------------------------------------------
-- AnneePromotionSequence
-- PersonneSequence
-- EntrepriseSequence
-- EntrepriseVilleSecteurPersonneSequence
-- SecteurSequence
-- PaysSequence
-- VilleSequence
-- EntrepriseVilleSecteurSequence
-- EcoleSequence
-- EcoleSecteurSequence
-- EcoleSecteurPersonneSequence
------------------------------------------------------------------------------------------------------------------------------
-- et les tables suivantes :
------------------------------------------------------------------------------------------------------------------------------
-- AnneePromotion
-- Personne
-- Entreprise
-- EntrepriseVilleSecteurPersonne
-- Secteur
-- Pays
-- Ville
-- EntrepriseVilleSecteur
-- Ecole
-- EcoleSecteur
-- EcoleSecteurPersonne
------------------------------------------------------------------------------------------------------------------------------
-- et insere des donnees de test dans les tables suivantes :
------------------------------------------------------------------------------------------------------------------------------
-- AnneePromotion
-- Personne
-- Entreprise
-- EntrepriseVilleSecteurPersonne
-- Secteur
-- Pays
-- Ville
-- EntrepriseVilleSecteur
-- Ecole
------------------------------------------------------------------------------------------------------------------------------

# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

CREATE SEQUENCE AnneePromotionSequence;
CREATE SEQUENCE PersonneSequence;
CREATE SEQUENCE EntrepriseSequence;
CREATE SEQUENCE EntrepriseVilleSecteurPersonneSequence;
CREATE SEQUENCE SecteurSequence;
CREATE SEQUENCE PaysSequence;
CREATE SEQUENCE VilleSequence;
CREATE SEQUENCE EntrepriseVilleSecteurSequence;
CREATE SEQUENCE EcoleSequence;
CREATE SEQUENCE EcoleSecteurSequence;
CREATE SEQUENCE EcoleSecteurPersonneSequence;
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

CREATE TABLE EntrepriseVilleSecteur (
  entrepriseVilleSecteur_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EntrepriseVilleSecteurSequence'),
  entrepriseVilleSecteur_entreprise_ID INTEGER REFERENCES Entreprise (entreprise_ID) NOT NULL,
  entrepriseVilleSecteur_ville_ID INTEGER REFERENCES Ville (ville_ID) NOT NULL,
  entrepriseVilleSecteur_secteur_ID INTEGER REFERENCES Secteur (secteur_ID) NOT NULL
);

CREATE TABLE EntrepriseVilleSecteurPersonne (
  entrepriseVilleSecteurPersonne_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EntrepriseVilleSecteurPersonneSequence'),
  entrepriseVilleSecteurPersonne_personne_ID INTEGER REFERENCES Personne (personne_ID) NOT NULL,
  entrepriseVilleSecteurPersonne_entrepriseVilleSecteur_ID INTEGER REFERENCES EntrepriseVilleSecteur (entrepriseVilleSecteur_ID) NOT NULL
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

CREATE TABLE EcoleSecteurPersonne (
  ecoleSecteurPersonne_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EcoleSecteurPersonneSequence'),
  ecoleSecteurPersonne_ecoleSecteur_ID INTEGER REFERENCES EcoleSecteur (ecoleSecteur_ID) NOT NULL,
  ecoleSecteurPersonne_personne_ID INTEGER REFERENCES Personne (personne_ID) NOT NULL
);

-- CREATE TABLE PosteActuel (
--  posteActuel_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PosteActuelSequence'),
--   posteActuel_entreprisePersonne_ID INTEGER REFERENCES EntreprisePersonne (entreprisePersonne_ID),
--   posteActuel_ecolePersonne_ID INTEGER REFERENCES EcolePersonne (ecolePersonne_ID),
--   CHECK (posteActuel_ecolePersonne_ID IS NOT NULL OR posteActuel_ecolePersonne_ID IS NOT NULL),
--  CHECK (posteActuel_ecolePersonne_ID IS NULL OR posteActuel_ecolePersonne_ID IS NULL)
-- );

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
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Marseille', 1);--5
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Bordeaux', 1);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Toulouse', 1);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Rennes', 1);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Madrid', 2);
INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Berlin', 3);--10

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

INSERT INTO EntrepriseVilleSecteurPersonne (entrepriseVilleSecteurPersonne_ID, entrepriseVilleSecteurPersonne_personne_ID, entrepriseVilleSecteurPersonne_entrepriseVilleSecteur_ID) VALUES (nextval('EntrepriseVilleSecteurPersonneSequence'), 1, 1);
INSERT INTO EntrepriseVilleSecteurPersonne (entrepriseVilleSecteurPersonne_ID, entrepriseVilleSecteurPersonne_personne_ID, entrepriseVilleSecteurPersonne_entrepriseVilleSecteur_ID) VALUES (nextval('EntrepriseVilleSecteurPersonneSequence'), 2, 19);
INSERT INTO EntrepriseVilleSecteurPersonne (entrepriseVilleSecteurPersonne_ID, entrepriseVilleSecteurPersonne_personne_ID, entrepriseVilleSecteurPersonne_entrepriseVilleSecteur_ID) VALUES (nextval('EntrepriseVilleSecteurPersonneSequence'), 3, 18);
INSERT INTO EntrepriseVilleSecteurPersonne (entrepriseVilleSecteurPersonne_ID, entrepriseVilleSecteurPersonne_personne_ID, entrepriseVilleSecteurPersonne_entrepriseVilleSecteur_ID) VALUES (nextval('EntrepriseVilleSecteurPersonneSequence'), 4, 26);
INSERT INTO EntrepriseVilleSecteurPersonne (entrepriseVilleSecteurPersonne_ID, entrepriseVilleSecteurPersonne_personne_ID, entrepriseVilleSecteurPersonne_entrepriseVilleSecteur_ID) VALUES (nextval('EntrepriseVilleSecteurPersonneSequence'), 5, 27);
INSERT INTO EntrepriseVilleSecteurPersonne (entrepriseVilleSecteurPersonne_ID, entrepriseVilleSecteurPersonne_personne_ID, entrepriseVilleSecteurPersonne_entrepriseVilleSecteur_ID) VALUES (nextval('EntrepriseVilleSecteurPersonneSequence'), 6, 33);
INSERT INTO EntrepriseVilleSecteurPersonne (entrepriseVilleSecteurPersonne_ID, entrepriseVilleSecteurPersonne_personne_ID, entrepriseVilleSecteurPersonne_entrepriseVilleSecteur_ID) VALUES (nextval('EntrepriseVilleSecteurPersonneSequence'), 7, 26);

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

INSERT INTO EcoleSecteurPersonne (ecoleSecteurPersonne_ID, ecoleSecteurPersonne_ecoleSecteur_ID, ecoleSecteurPersonne_personne_ID) VALUES (nextval('EcoleSecteurPersonneSequence'),1,1);
INSERT INTO EcoleSecteurPersonne (ecoleSecteurPersonne_ID, ecoleSecteurPersonne_ecoleSecteur_ID, ecoleSecteurPersonne_personne_ID) VALUES (nextval('EcoleSecteurPersonneSequence'),5,1);
INSERT INTO EcoleSecteurPersonne (ecoleSecteurPersonne_ID, ecoleSecteurPersonne_ecoleSecteur_ID, ecoleSecteurPersonne_personne_ID) VALUES (nextval('EcoleSecteurPersonneSequence'),2,4);
INSERT INTO EcoleSecteurPersonne (ecoleSecteurPersonne_ID, ecoleSecteurPersonne_ecoleSecteur_ID, ecoleSecteurPersonne_personne_ID) VALUES (nextval('EcoleSecteurPersonneSequence'),3,5);
INSERT INTO EcoleSecteurPersonne (ecoleSecteurPersonne_ID, ecoleSecteurPersonne_ecoleSecteur_ID, ecoleSecteurPersonne_personne_ID) VALUES (nextval('EcoleSecteurPersonneSequence'),4,6);


# --- !Downs

DROP SEQUENCE IF EXISTS AnneePromotionSequence CASCADE;
DROP SEQUENCE IF EXISTS PersonneSequence CASCADE;
DROP SEQUENCE IF EXISTS EntrepriseSequence CASCADE;
DROP SEQUENCE IF EXISTS EntrepriseVilleSecteurPersonneSequence CASCADE;
DROP SEQUENCE IF EXISTS SecteurSequence CASCADE;
DROP SEQUENCE IF EXISTS PaysSequence CASCADE;
DROP SEQUENCE IF EXISTS VilleSequence CASCADE;
DROP SEQUENCE IF EXISTS EntrepriseVilleSecteurSequence CASCADE;
DROP SEQUENCE IF EXISTS EcoleSequence CASCADE;
DROP SEQUENCE IF EXISTS EcoleSecteurSequence CASCADE;
DROP SEQUENCE IF EXISTS EcoleSecteurPersonneSequence CASCADE;
DROP SEQUENCE IF EXISTS PosteActuelSequence CASCADE;

DROP TABLE IF EXISTS AnneePromotion CASCADE;
DROP TABLE IF EXISTS Personne CASCADE;
DROP TABLE IF EXISTS Entreprise CASCADE;
DROP TABLE IF EXISTS EntrepriseVilleSecteurPersonne CASCADE;
DROP TABLE IF EXISTS Secteur CASCADE;
DROP TABLE IF EXISTS Pays CASCADE;
DROP TABLE IF EXISTS Ville CASCADE;
DROP TABLE IF EXISTS EntrepriseVilleSecteur CASCADE;
DROP TABLE IF EXISTS Ecole CASCADE; 
DROP TABLE IF EXISTS EcoleSecteur CASCADE; 
DROP TABLE IF EXISTS EcoleSecteurPersonne CASCADE; 
DROP TABLE IF EXISTS PosteActuel CASCADE; 
