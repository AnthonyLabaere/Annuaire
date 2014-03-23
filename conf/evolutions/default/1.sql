---------------------------------------------------------------------------

-- Copyright 2014 Anthony Labaere
-- 
-- Contributeurs : 
-- François Neber francois.neber@centraliens-nantes.net
-- Malik Olivier Boussejra malik.boussejra@centraliens-nantes.net
-- Anthony Labaere anthony.labaere@centraliens-nantes.net
-- 
-- Ce logiciel est un programme informatique ayant pour but de faciliter 
-- les contacts entre étudiants et diplômés de l'École Centrale Nantes 
-- à l'étranger comme en France.
-- 
-- Ce logiciel est régi par la licence CeCILL soumise au droit français et
-- respectant les principes de diffusion des logiciels libres. Vous pouvez
-- utiliser, modifier et/ou redistribuer ce programme sous les conditions
-- de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA 
-- sur le site "http://www.cecill.info".
-- 
-- En contrepartie de l'accessibilité au code source et des droits de copie,
-- de modification et de redistribution accordés par cette licence, il n'est
-- offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
-- seule une responsabilité restreinte pèse sur l'auteur du programme,  le
-- titulaire des droits patrimoniaux et les concédants successifs.
-- 
-- A cet égard  l'attention de l'utilisateur est attirée sur les risques
-- associés au chargement,  à l'utilisation,  à la modification et/ou au
-- développement et à la reproduction du logiciel par l'utilisateur étant 
-- donné sa spécificité de logiciel libre, qui peut le rendre complexe à 
-- manipuler et qui le réserve donc à des développeurs et des professionnels
-- avertis possédant  des  connaissances  informatiques approfondies.  Les
-- utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
-- logiciel à leurs besoins dans des conditions permettant d'assurer la
-- sécurité de leurs systèmes et ou de leurs données et, plus généralement, 
-- à l'utiliser et l'exploiter dans les mêmes conditions de sécurité. 
-- 
-- Le fait que vous puissiez accéder à cet en-tête signifie que vous avez 
-- pris connaissance de la licence CeCILL et que vous en avez accepté les
-- termes.

---------------------------------------------------------------------------

------------------------------------------------------------------------------------------------------------------------------
-- Ce script cree les sequences suivantes :
------------------------------------------------------------------------------------------------------------------------------
-- AnneePromotionSequence
-- CentralienSequence
-- EntrepriseSequence
-- PosteSequence
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
-- Ce script insere des donnees de test dans les tables suivantes :
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
CREATE SEQUENCE PosteSequence;
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
  centralien_telephone INTEGER,
  centralien_mail VARCHAR(50),
  centralien_anneePromotion_ID INTEGER REFERENCES AnneePromotion (anneePromotion_ID) NOT NULL
);

CREATE TABLE Pays (
  pays_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PaysSequence'),
  pays_nom VARCHAR(50) NOT NULL,
  pays_nomMajuscule VARCHAR(50) NOT NULL,
  pays_nomMinuscule VARCHAR(50) NOT NULL,
  pays_code VARCHAR(2) NOT NULL,
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

CREATE TABLE Poste (
	poste_id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PosteSequence'),
	poste_nom VARCHAR(50) NOT NULL
);

CREATE TABLE EntrepriseVilleSecteurCentralien (
  entrepriseVilleSecteurCentralien_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('EntrepriseVilleSecteurCentralienSequence'),
  entrepriseVilleSecteurCentralien_centralien_ID INTEGER REFERENCES Centralien (centralien_ID) NOT NULL,
  entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID INTEGER REFERENCES EntrepriseVilleSecteur (entrepriseVilleSecteur_ID) NOT NULL,
  entrepriseVilleSecteurCentralien_poste_ID INTEGER REFERENCES Poste (poste_ID) NOT NULL
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

CREATE TABLE PosteActuel (
  posteActuel_ID INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('PosteActuelSequence'),
  posteActuel_entrepriseVilleSecteurCentralien_ID INTEGER REFERENCES EntrepriseVilleSecteurCentralien (entrepriseVilleSecteurCentralien_ID),
  posteActuel_ecoleSecteurCentralien_ID INTEGER REFERENCES EcoleSecteurCentralien (ecoleSecteurCentralien_ID),
  CHECK (posteActuel_entrepriseVilleSecteurCentralien_ID IS NOT NULL OR posteActuel_ecoleSecteurCentralien_ID IS NOT NULL),
  CHECK (posteActuel_entrepriseVilleSecteurCentralien_ID IS NULL OR posteActuel_ecoleSecteurCentralien_ID IS NULL)
);


------------------------------------------------------------------------------------------------------------------------------
-- Insertion des donnees
------------------------------------------------------------------------------------------------------------------------------
INSERT INTO AnneePromotion (anneePromotion_ID, anneePromotion_libelle) VALUES 
(nextval('AnneePromotionSequence'), 2003),
(nextval('AnneePromotionSequence'), 2004),
(nextval('AnneePromotionSequence'), 2005),
(nextval('AnneePromotionSequence'), 2006),
(nextval('AnneePromotionSequence'), 2007),--5
(nextval('AnneePromotionSequence'), 2008),
(nextval('AnneePromotionSequence'), 2009),
(nextval('AnneePromotionSequence'), 2010),
(nextval('AnneePromotionSequence'), 2011),
(nextval('AnneePromotionSequence'), 2012),--10
(nextval('AnneePromotionSequence'), 2013),
(nextval('AnneePromotionSequence'), 2014),
(nextval('AnneePromotionSequence'), 2015),
(nextval('AnneePromotionSequence'), 2016);--14

INSERT INTO Centralien (centralien_ID, centralien_prenom, centralien_nom,  centralien_anneePromotion_ID) VALUES
(nextval('CentralienSequence'), 'Dupont', 'Dupond', 9),
(nextval('CentralienSequence'), 'Robert', 'Dupond', 10),
(nextval('CentralienSequence'), 'Albert', 'Dupond', 10),
(nextval('CentralienSequence'), 'Francis', 'Dupond', 11),
(nextval('CentralienSequence'), 'Benjamin', 'Dupond', 8),--5
(nextval('CentralienSequence'), 'Anthony', 'Dupond', 12),
(nextval('CentralienSequence'), 'Indianna', 'Jones', 1),
(nextval('CentralienSequence'), 'Black', 'MrCafe', 1);--8

INSERT INTO pays (pays_id, pays_nom, pays_nomMajuscule, pays_nomMinuscule, pays_code, pays_latitude, pays_longitude, pays_zoom) VALUES
(nextval('PaysSequence'), 'France', 'france', 'FRANCE', 'fr', 46.227638, 2.213749, 6),
(nextval('PaysSequence'), 'Espagne', 'espagne', 'ESPAGNE', 'es', 40.463667, -3.74922, 6),
(nextval('PaysSequence'), 'Allemagne', 'allemagne', 'ALLEMAGNE', 'de', 51.165691, 10.451526, 6),
(nextval('PaysSequence'), 'Afrique du sud', 'afrique-du-sud', 'AFRIQUE DU SUD', 'za', -30.559482, 22.937506, 6),
(nextval('PaysSequence'), 'Albanie', 'albanie', 'ALBANIE', 'al', 41.153332, 20.168331, 6),
(nextval('PaysSequence'), 'Algérie', 'algerie', 'ALGÉRIE', 'dz', 28.033886, 1.659626, 6),
(nextval('PaysSequence'), 'Arabie saoudite', 'arabie-saoudite', 'ARABIE SAOUDITE', 'sa', 23.885942, 45.079162, 6),
(nextval('PaysSequence'), 'Argentine', 'argentine', 'ARGENTINE', 'ar', -38.416097, -63.616672, 6),
(nextval('PaysSequence'), 'Australie', 'australie', 'AUSTRALIE', 'au', -25.274398, 133.775136, 6),
(nextval('PaysSequence'), 'Autriche', 'autriche', 'AUTRICHE', 'at', 47.516231, 14.550072, 6),
(nextval('PaysSequence'), 'Brésil', 'bresil', 'BRÉSIL', 'br', -14.235004, -51.92528, 6),
(nextval('PaysSequence'), 'Bulgarie', 'bulgarie', 'BULGARIE', 'bg', 42.733883, 25.48583, 6),
(nextval('PaysSequence'), 'Canada', 'canada', 'CANADA', 'ca', 56.130366, -106.346771, 6),
(nextval('PaysSequence'), 'Colombie', 'colombie', 'COLOMBIE', 'co', 4.570868, -74.297333, 6),
(nextval('PaysSequence'), 'Costa Rica', 'costa-rica', 'COSTA RICA', 'cr', 9.748917, -83.753428, 6),
(nextval('PaysSequence'), 'Croatie', 'croatie', 'CROATIE', 'hr', 45.1, 15.2, 6),
(nextval('PaysSequence'), 'Danemark', 'danemark', 'DANEMARK', 'dk', 56.26392, 9.501785, 6),
(nextval('PaysSequence'), 'Égypte', 'egypte', 'ÉGYPTE', 'eg', 26.820553, 30.802498, 6),
(nextval('PaysSequence'), 'Équateur', 'equateur', 'ÉQUATEUR', 'ec', -1.831239, -78.183406, 6),
(nextval('PaysSequence'), 'États-Unis', 'etats-unis', 'ÉTATS-UNIS', 'us', 37.09024, -95.712891, 6),
(nextval('PaysSequence'), 'Grèce', 'grece', 'GRÈCE', 'gr', 39.074208, 21.824312, 6),
(nextval('PaysSequence'), 'Hong Kong', 'hong-kong', 'HONG KONG', 'hk', 22.396428, 114.109497, 6),
(nextval('PaysSequence'), 'Hongrie', 'hongrie', 'HONGRIE', 'hu', 47.162494, 19.503304, 6),
(nextval('PaysSequence'), 'Irlande', 'irlande', 'IRLANDE', 'ie', 53.41291, -8.24389, 6),
(nextval('PaysSequence'), 'Israël', 'israel', 'ISRAËL', 'il', 31.046051, 34.851612, 6),
(nextval('PaysSequence'), 'Italie', 'italie', 'ITALIE', 'it', 41.87194, 12.56738, 6),
(nextval('PaysSequence'), 'Japon', 'japon', 'JAPON', 'jp', 36.204824, 138.252924, 6),
(nextval('PaysSequence'), 'Jordanie', 'jordanie', 'JORDANIE', 'jo', 30.585164, 36.238414, 6),
(nextval('PaysSequence'), 'Porto Rico', 'porto-rico', 'PORTO RICO', 'pr', 18.220833, -66.590149, 6),
(nextval('PaysSequence'), 'Liban', 'liban', 'LIBAN', 'lb', 33.854721, 35.862285, 6),
(nextval('PaysSequence'), 'Maroc', 'maroc', 'MAROC', 'ma', 31.791702, -7.09262, 6),
(nextval('PaysSequence'), 'Mexique', 'mexique', 'MEXIQUE', 'mx', 23.634501, -102.552784, 6),
(nextval('PaysSequence'), 'Norvège', 'norvege', 'NORVÈGE', 'no', 60.472024, 8.468946, 6),
(nextval('PaysSequence'), 'Pérou', 'perou', 'PÉROU', 'pe', -9.189967, -75.015152, 6),
(nextval('PaysSequence'), 'Pakistan', 'pakistan', 'PAKISTAN', 'pk', 30.375321, 69.345116, 6),
(nextval('PaysSequence'), 'Pays-Bas', 'pays-bas', 'PAYS-BAS', 'nl', 52.132633, 5.291266, 6),
(nextval('PaysSequence'), 'Portugal', 'portugal', 'PORTUGAL', 'pt', 39.399872, -8.224454, 6),
(nextval('PaysSequence'), 'République tchèque', 'republique-tcheque', 'RÉPUBLIQUE TCHÈQUE', 'cz', 49.817492, 15.472962, 6),
(nextval('PaysSequence'), 'Roumanie', 'roumanie', 'ROUMANIE', 'ro', 45.943161, 24.96676, 6),
(nextval('PaysSequence'), 'Royaume-Uni', 'royaume-uni', 'ROYAUME-UNI', 'uk', 55.378051, -3.435973, 6),
(nextval('PaysSequence'), 'Singapour', 'singapour', 'SINGAPOUR', 'sg', 1.352083, 103.819836, 6),
(nextval('PaysSequence'), 'Suède', 'suede', 'SUÈDE', 'se', 60.128161, 18.643501, 6),
(nextval('PaysSequence'), 'Suisse', 'suisse', 'SUISSE', 'ch', 46.818188, 8.227512, 6),
(nextval('PaysSequence'), 'Thailande', 'thailande', 'THAILANDE', 'th', 15.870032, 100.992541, 6),
(nextval('PaysSequence'), 'Turquie', 'turquie', 'TURQUIE', 'tr', 38.963745, 35.243322, 6),
(nextval('PaysSequence'), 'Venezuela', 'venezuela', 'VENEZUELA', 've', 6.42375, -66.58973, 6);

INSERT INTO Ville (ville_ID, ville_nom, ville_pays_ID, ville_latitude, ville_longitude) VALUES
(nextval('VilleSequence'), 'Nantes', 1, 47.2183710, -1.5536210),
(nextval('VilleSequence'), 'Paris', 1, 48.8566140, 2.3522219),
(nextval('VilleSequence'), 'Lille', 1, 50.6292500, 3.0572560),
(nextval('VilleSequence'), 'Lyon', 1, 45.7640430, 4.8356590),
(nextval('VilleSequence'), 'Marseille', 1, 43.2964820, 5.3697800),--5
(nextval('VilleSequence'), 'Bordeaux', 1, 44.8377890, -0.5791800),
(nextval('VilleSequence'), 'Toulouse', 1, 43.6046520, 1.4442090),
(nextval('VilleSequence'), 'Rennes', 1, 48.1134750, -1.6757080),
(nextval('VilleSequence'), 'Madrid', 2, 40.4167754, -0.1198244),
(nextval('VilleSequence'), 'Berlin', 3, 52.5200066, 13.4049540);--10

INSERT INTO Entreprise (entreprise_ID, entreprise_nom) VALUES
(nextval('EntrepriseSequence'), 'Sopra'),
(nextval('EntrepriseSequence'), 'Capgemini'),
(nextval('EntrepriseSequence'), 'Atos'),
(nextval('EntrepriseSequence'), 'Solucom'),
(nextval('EntrepriseSequence'), 'Centrale Nantes Lab'),--5
(nextval('EntrepriseSequence'), 'Café du coin');

INSERT INTO Secteur (secteur_ID, secteur_nom) VALUES
(nextval('SecteurSequence'), 'Système d''information'),
(nextval('SecteurSequence'), 'Consulting'),
(nextval('SecteurSequence'), 'Recherche'),
(nextval('SecteurSequence'), 'Percolation');

INSERT INTO EntrepriseVilleSecteur (entrepriseVilleSecteur_ID, entrepriseVilleSecteur_entreprise_ID, entrepriseVilleSecteur_ville_ID, entrepriseVilleSecteur_secteur_ID) VALUES 
(nextval('EntrepriseVilleSecteurSequence'), 1, 1, 1),
(nextval('EntrepriseVilleSecteurSequence'), 1, 1, 2),
(nextval('EntrepriseVilleSecteurSequence'), 1, 2, 1),
(nextval('EntrepriseVilleSecteurSequence'), 1, 2, 2),
(nextval('EntrepriseVilleSecteurSequence'), 1, 3, 1),--5
(nextval('EntrepriseVilleSecteurSequence'), 1, 3, 2),
(nextval('EntrepriseVilleSecteurSequence'), 1, 4, 1),
(nextval('EntrepriseVilleSecteurSequence'), 1, 4, 2),
(nextval('EntrepriseVilleSecteurSequence'), 1, 5, 1),
(nextval('EntrepriseVilleSecteurSequence'), 1, 5, 2),--10
(nextval('EntrepriseVilleSecteurSequence'), 1, 6, 1),
(nextval('EntrepriseVilleSecteurSequence'), 1, 6, 2),
(nextval('EntrepriseVilleSecteurSequence'), 1, 7, 1),
(nextval('EntrepriseVilleSecteurSequence'), 1, 7, 2),
(nextval('EntrepriseVilleSecteurSequence'), 1, 8, 1),--15
(nextval('EntrepriseVilleSecteurSequence'), 1, 8, 2),
(nextval('EntrepriseVilleSecteurSequence'), 2, 1, 1),
(nextval('EntrepriseVilleSecteurSequence'), 2, 2, 2),
(nextval('EntrepriseVilleSecteurSequence'), 2, 3, 1),
(nextval('EntrepriseVilleSecteurSequence'), 2, 4, 1),--20
(nextval('EntrepriseVilleSecteurSequence'), 2, 5, 2),
(nextval('EntrepriseVilleSecteurSequence'), 2, 7, 1),
(nextval('EntrepriseVilleSecteurSequence'), 2, 8, 1),
(nextval('EntrepriseVilleSecteurSequence'), 3, 1, 1),
(nextval('EntrepriseVilleSecteurSequence'), 3, 1, 2),--25
(nextval('EntrepriseVilleSecteurSequence'), 3, 2, 2),
(nextval('EntrepriseVilleSecteurSequence'), 3, 3, 1),
(nextval('EntrepriseVilleSecteurSequence'), 3, 3, 2),
(nextval('EntrepriseVilleSecteurSequence'), 3, 4, 2),
(nextval('EntrepriseVilleSecteurSequence'), 3, 5, 1),--30
(nextval('EntrepriseVilleSecteurSequence'), 3, 6, 1),
(nextval('EntrepriseVilleSecteurSequence'), 3, 6, 2),
(nextval('EntrepriseVilleSecteurSequence'), 4, 2, 1),
(nextval('EntrepriseVilleSecteurSequence'), 4, 2, 2),
(nextval('EntrepriseVilleSecteurSequence'), 5, 1, 3),--35
(nextval('EntrepriseVilleSecteurSequence'), 6, 1, 4);

INSERT INTO Poste (poste_id, poste_nom) VALUES
(nextval('PosteSequence'), 'Ingénieur Développeur');

INSERT INTO EntrepriseVilleSecteurCentralien (entrepriseVilleSecteurCentralien_ID, entrepriseVilleSecteurCentralien_centralien_ID, entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID, entrepriseVilleSecteurCentralien_poste_ID) VALUES
(nextval('EntrepriseVilleSecteurCentralienSequence'), 1, 1, 1),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 2, 19, 1),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 3, 18, 1),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 4, 26, 1),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 5, 27, 1),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 6, 33, 1),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 7, 26, 1),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 8, 36, 1),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 1, 2, 1),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 2, 20, 1),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 3, 19, 1),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 4, 27, 1);

INSERT INTO Ecole (ecole_ID, ecole_nom, ecole_ville_ID) VALUES
(nextval('EcoleSequence'), 'Centrale Nantes', 1),
(nextval('EcoleSequence'), 'Audencia', 1),
(nextval('EcoleSequence'), 'Oniris', 1),
(nextval('EcoleSequence'), 'Lycée Faidherbe', 3),
(nextval('EcoleSequence'), 'ENA', 2),
(nextval('EcoleSequence'), 'ESB', 1);--6

INSERT INTO EcoleSecteur (ecoleSecteur_ID, ecoleSecteur_ecole_ID, ecoleSecteur_secteur_ID) VALUES
(nextval('EcoleSecteurSequence'),1,1),
(nextval('EcoleSecteurSequence'),1,3),
(nextval('EcoleSecteurSequence'),2,3),
(nextval('EcoleSecteurSequence'),3,3),
(nextval('EcoleSecteurSequence'),4,1),--5
(nextval('EcoleSecteurSequence'),4,3),
(nextval('EcoleSecteurSequence'),5,3),
(nextval('EcoleSecteurSequence'),6,3);

INSERT INTO EcoleSecteurCentralien (ecoleSecteurCentralien_ID, ecoleSecteurCentralien_centralien_ID, ecoleSecteurCentralien_ecoleSecteur_ID) VALUES
(nextval('EcoleSecteurCentralienSequence'),1,1),
(nextval('EcoleSecteurCentralienSequence'),1,5),
(nextval('EcoleSecteurCentralienSequence'),4,2),
(nextval('EcoleSecteurCentralienSequence'),5,3),
(nextval('EcoleSecteurCentralienSequence'),6,4);

-- TODO : Necessite une fonction sql de controle pour verifier : une seule ligne par centralien
INSERT INTO PosteActuel (posteActuel_ID, posteActuel_entrepriseVilleSecteurCentralien_ID, posteActuel_ecoleSecteurCentralien_ID) VALUES
(nextval('PosteActuelSequence'),2,NULL),
(nextval('PosteActuelSequence'),4,NULL),
(nextval('PosteActuelSequence'),5,NULL),
(nextval('PosteActuelSequence'),6,NULL),
(nextval('PosteActuelSequence'),7,NULL),
(nextval('PosteActuelSequence'),8,NULL),
(nextval('PosteActuelSequence'),NULL,1),
(nextval('PosteActuelSequence'),NULL,3);

# --- !Downs

------------------------------------------------------------------------------------------------------------------------------
-- Suppression des sequences
------------------------------------------------------------------------------------------------------------------------------
DROP SEQUENCE IF EXISTS AnneePromotionSequence CASCADE;
DROP SEQUENCE IF EXISTS CentralienSequence CASCADE;
DROP SEQUENCE IF EXISTS EntrepriseSequence CASCADE;
DROP SEQUENCE IF EXISTS PosteSequence CASCADE;
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
DROP TABLE IF EXISTS Poste CASCADE;
