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

-- TODO : Latitude et longitude doivent etre insere par un webservice ou une API, le zoom est a definir a la main...
-- INSERT INTO Pays (pays_ID, pays_nom, pays_nomMinuscule, pays_nomMajuscule/*, pays_latitude, pays_longitude*/, pays_code, pays_zoom) VALUES 
-- nextval('PaysSequence'), 'France', 'FRANCE', 'france', 'fr', 6),
-- nextval('PaysSequence'), 'Afghanistan', 'AFGHANISTAN', 'afghanistan', 'af', 6),
-- nextval('PaysSequence'), 'Afrique du sud', 'AFRIQUE DU SUD', 'afrique-du-sud', 'za', 6),
-- nextval('PaysSequence'), 'Albanie', 'ALBANIE', 'albanie', 'al', 6),
-- nextval('PaysSequence'), 'Algérie', 'ALGÉRIE', 'algerie', 'dz', 6),
-- nextval('PaysSequence'), 'Allemagne', 'ALLEMAGNE', 'allemagne', 'de', 6),
-- nextval('PaysSequence'), 'Arabie saoudite', 'ARABIE SAOUDITE', 'arabie-saoudite', 'sa', 6),
-- nextval('PaysSequence'), 'Argentine', 'ARGENTINE', 'argentine', 'ar', 6),
-- nextval('PaysSequence'), 'Australie', 'AUSTRALIE', 'australie', 'au', 6),
-- nextval('PaysSequence'), 'Autriche', 'AUTRICHE', 'autriche', 'at', 6),
-- nextval('PaysSequence'), 'Belgique', 'BELGIQUE', 'belgique', 'be', 6),
-- nextval('PaysSequence'), 'Brésil', 'BRÉSIL', 'bresil', 'br', 6),
-- nextval('PaysSequence'), 'Bulgarie', 'BULGARIE', 'bulgarie', 'bg', 6),
-- nextval('PaysSequence'), 'Canada', 'CANADA', 'canada', 'ca', 6),
-- nextval('PaysSequence'), 'Chili', 'CHILI', 'chili', 'cl', 6),
-- nextval('PaysSequence'), 'Chine (Rép. pop.)', 'CHINE (RÉP. POP.)', 'chine-rep-pop', 'cn', 6),
-- nextval('PaysSequence'), 'Colombie', 'COLOMBIE', 'colombie', 'co', 6),
-- nextval('PaysSequence'), 'Corée, Sud', 'CORÉE, SUD', 'coree-sud', 'kr', 6),
-- nextval('PaysSequence'), 'Costa Rica', 'COSTA RICA', 'costa-rica', 'cr', 6),
-- nextval('PaysSequence'), 'Croatie', 'CROATIE', 'croatie', 'hr', 6),
-- nextval('PaysSequence'), 'Danemark', 'DANEMARK', 'danemark', 'dk', 6),
-- nextval('PaysSequence'), 'Égypte', 'ÉGYPTE', 'egypte', 'eg', 6),
-- nextval('PaysSequence'), 'Émirats arabes unis', 'ÉMIRATS ARABES UNIS', 'emirats-arabes-unis', 'ae', 6),
-- nextval('PaysSequence'), 'Équateur', 'ÉQUATEUR', 'equateur', 'ec', 6),
-- nextval('PaysSequence'), 'États-Unis', 'ÉTATS-UNIS', 'etats-unis', 'us', 6),
-- nextval('PaysSequence'), 'El Salvador', 'EL SALVADOR', 'el-salvador', 'sv', 6),
-- nextval('PaysSequence'), 'Espagne', 'ESPAGNE', 'espagne', 'es', 6),
-- nextval('PaysSequence'), 'Finlande', 'FINLANDE', 'finlande', 'fi', 6),
-- nextval('PaysSequence'), 'Grèce', 'GRÈCE', 'grece', 'gr', 6),
-- nextval('PaysSequence'), 'Hong Kong', 'HONG KONG', 'hong-kong', 'hk', 6),
-- nextval('PaysSequence'), 'Hongrie', 'HONGRIE', 'hongrie', 'hu', 6),
-- nextval('PaysSequence'), 'Inde', 'INDE', 'inde', 'in', 6),
-- nextval('PaysSequence'), 'Indonésie', 'INDONÉSIE', 'indonesie', 'id', 6),
-- nextval('PaysSequence'), 'Irlande', 'IRLANDE', 'irlande', 'ie', 6),
-- nextval('PaysSequence'), 'Israël', 'ISRAËL', 'israel', 'il', 6),
-- nextval('PaysSequence'), 'Italie', 'ITALIE', 'italie', 'it', 6),
-- nextval('PaysSequence'), 'Japon', 'JAPON', 'japon', 'jp', 6),
-- nextval('PaysSequence'), 'Jordanie', 'JORDANIE', 'jordanie', 'jo', 6),
-- nextval('PaysSequence'), 'Liban', 'LIBAN', 'liban', 'lb', 6),
-- nextval('PaysSequence'), 'Malaisie', 'MALAISIE', 'malaisie', 'my', 6),
-- nextval('PaysSequence'), 'Maroc', 'MAROC', 'maroc', 'ma', 6),
-- nextval('PaysSequence'), 'Mexique', 'MEXIQUE', 'mexique', 'mx', 6),
-- nextval('PaysSequence'), 'Norvège', 'NORVÈGE', 'norvege', 'no', 6),
-- nextval('PaysSequence'), 'Nouvelle-Zélande', 'NOUVELLE-ZÉLANDE', 'nouvelle-zelande', 'nz', 6),
-- nextval('PaysSequence'), 'Pérou', 'PÉROU', 'perou', 'pe', 6),
-- nextval('PaysSequence'), 'Pakistan', 'PAKISTAN', 'pakistan', 'pk', 6),
-- nextval('PaysSequence'), 'Pays-Bas', 'PAYS-BAS', 'pays-bas', 'nl', 6),
-- nextval('PaysSequence'), 'Philippines', 'PHILIPPINES', 'philippines', 'ph', 6),
-- nextval('PaysSequence'), 'Pologne', 'POLOGNE', 'pologne', 'pl', 6),
-- nextval('PaysSequence'), 'Porto Rico', 'PORTO RICO', 'porto-rico', 'pr', 6),
-- nextval('PaysSequence'), 'Portugal', 'PORTUGAL', 'portugal', 'pt', 6),
-- nextval('PaysSequence'), 'République tchèque', 'RÉPUBLIQUE TCHÈQUE', 'republique-tcheque', 'cz', 6),
-- nextval('PaysSequence'), 'Roumanie', 'ROUMANIE', 'roumanie', 'ro', 6),
-- nextval('PaysSequence'), 'Royaume-Uni', 'ROYAUME-UNI', 'royaume-uni', 'uk', 6),
-- nextval('PaysSequence'), 'Russie', 'RUSSIE', 'russie', 'ru', 6),
-- nextval('PaysSequence'), 'Singapour', 'SINGAPOUR', 'singapour', 'sg', 6),
-- nextval('PaysSequence'), 'Suède', 'SUÈDE', 'suede', 'se', 6),
-- nextval('PaysSequence'), 'Suisse', 'SUISSE', 'suisse', 'ch', 6),
-- nextval('PaysSequence'), 'Taiwan', 'TAIWAN', 'taiwan', 'tw', 6),
-- nextval('PaysSequence'), 'Thailande', 'THAILANDE', 'thailande', 'th', 6),
-- nextval('PaysSequence'), 'Turquie', 'TURQUIE', 'turquie', 'tr', 6),
-- nextval('PaysSequence'), 'Ukraine', 'UKRAINE', 'ukraine', 'ua', 6),
-- nextval('PaysSequence'), 'Venezuela', 'VENEZUELA', 'venezuela', 've', 6),
-- nextval('PaysSequence'), 'Yougoslavie', 'YOUGOSLAVIE', 'yougoslavie', 'yu', 6),
-- nextval('PaysSequence'), 'Samoa', 'SAMOA', 'samoa', 'as', 6),
-- nextval('PaysSequence'), 'Andorre', 'ANDORRE', 'andorre', 'ad', 6),
-- nextval('PaysSequence'), 'Angola', 'ANGOLA', 'angola', 'ao', 6),
-- nextval('PaysSequence'), 'Anguilla', 'ANGUILLA', 'anguilla', 'ai', 6),
-- nextval('PaysSequence'), 'Antarctique', 'ANTARCTIQUE', 'antarctique', 'aq', 6),
-- nextval('PaysSequence'), 'Antigua et Barbuda', 'ANTIGUA ET BARBUDA', 'antigua-et-barbuda', 'ag', 6),
-- nextval('PaysSequence'), 'Arménie', 'ARMÉNIE', 'armenie', 'am', 6),
-- nextval('PaysSequence'), 'Aruba', 'ARUBA', 'aruba', 'aw', 6),
-- nextval('PaysSequence'), 'Azerbaïdjan', 'AZERBAÏDJAN', 'azerbaidjan', 'az', 6),
-- nextval('PaysSequence'), 'Bahamas', 'BAHAMAS', 'bahamas', 'bs', 6),
-- nextval('PaysSequence'), 'Bahrain', 'BAHRAIN', 'bahrain', 'bh', 6),
-- nextval('PaysSequence'), 'Bangladesh', 'BANGLADESH', 'bangladesh', 'bd', 6),
-- nextval('PaysSequence'), 'Biélorussie', 'BIÉLORUSSIE', 'bielorussie', 'by', 6),
-- nextval('PaysSequence'), 'Belize', 'BELIZE', 'belize', 'bz', 6),
-- nextval('PaysSequence'), 'Benin', 'BENIN', 'benin', 'bj', 6),
-- nextval('PaysSequence'), 'Bermudes (Les)', 'BERMUDES (LES)', 'bermudes-les', 'bm', 6),
-- nextval('PaysSequence'), 'Bhoutan', 'BHOUTAN', 'bhoutan', 'bt', 6),
-- nextval('PaysSequence'), 'Bolivie', 'BOLIVIE', 'bolivie', 'bo', 6),
-- nextval('PaysSequence'), 'Bosnie-Herzégovine', 'BOSNIE-HERZÉGOVINE', 'bosnie-herzegovine', 'ba', 6),
-- nextval('PaysSequence'), 'Botswana', 'BOTSWANA', 'botswana', 'bw', 6),
-- nextval('PaysSequence'), 'Bouvet (Îles)', 'BOUVET (ÎLES)', 'bouvet-iles', 'bv', 6),
-- nextval('PaysSequence'), 'Territoire britannique de l''océan Indien', 'TERRITOIRE BRITANNIQUE DE L''OCÉAN INDIEN', 'territoire-britannique-de-locean-indien', 'io', 6),
-- nextval('PaysSequence'), 'Vierges britanniques (Îles)', 'VIERGES BRITANNIQUES (ÎLES)', 'vierges-britanniques-iles', 'vg', 6),
-- nextval('PaysSequence'), 'Brunei', 'BRUNEI', 'brunei', 'bn', 6),
-- nextval('PaysSequence'), 'Burkina Faso', 'BURKINA FASO', 'burkina-faso', 'bf', 6),
-- nextval('PaysSequence'), 'Burundi', 'BURUNDI', 'burundi', 'bi', 6),
-- nextval('PaysSequence'), 'Cambodge', 'CAMBODGE', 'cambodge', 'kh', 6),
-- nextval('PaysSequence'), 'Cameroun', 'CAMEROUN', 'cameroun', 'cm', 6),
-- nextval('PaysSequence'), 'Cap Vert', 'CAP VERT', 'cap-vert', 'cv', 6),
-- nextval('PaysSequence'), 'Cayman (Îles)', 'CAYMAN (ÎLES)', 'cayman-iles', 'ky', 6),
-- nextval('PaysSequence'), 'République centrafricaine', 'RÉPUBLIQUE CENTRAFRICAINE', 'republique-centrafricaine', 'cf', 6),
-- nextval('PaysSequence'), 'Tchad', 'TCHAD', 'tchad', 'td', 6),
-- nextval('PaysSequence'), 'Christmas (Île)', 'CHRISTMAS (ÎLE)', 'christmas-ile', 'cx', 6),
-- nextval('PaysSequence'), 'Cocos (Îles)', 'COCOS (ÎLES)', 'cocos-iles', 'cc', 6),
-- nextval('PaysSequence'), 'Comores', 'COMORES', 'comores', 'km', 6),
-- nextval('PaysSequence'), 'Rép. Dém. du Congo', 'RÉP. DÉM. DU CONGO', 'rep-dem-du-congo', 'cg', 6),
-- nextval('PaysSequence'), 'Cook (Îles)', 'COOK (ÎLES)', 'cook-iles', 'ck', 6),
-- nextval('PaysSequence'), 'Cuba', 'CUBA', 'cuba', 'cu', 6),
-- nextval('PaysSequence'), 'Chypre', 'CHYPRE', 'chypre', 'cy', 6),
-- nextval('PaysSequence'), 'Djibouti', 'DJIBOUTI', 'djibouti', 'dj', 6),
-- nextval('PaysSequence'), 'Dominique', 'DOMINIQUE', 'dominique', 'dm', 6),
-- nextval('PaysSequence'), 'République Dominicaine', 'RÉPUBLIQUE DOMINICAINE', 'republique-dominicaine', 'do', 6),
-- nextval('PaysSequence'), 'Timor', 'TIMOR', 'timor', 'tp', 6),
-- nextval('PaysSequence'), 'Guinée Equatoriale', 'GUINÉE EQUATORIALE', 'guinee-equatoriale', 'gq', 6),
-- nextval('PaysSequence'), 'Érythrée', 'ÉRYTHRÉE', 'erythree', 'er', 6),
-- nextval('PaysSequence'), 'Estonie', 'ESTONIE', 'estonie', 'ee', 6),
-- nextval('PaysSequence'), 'Ethiopie', 'ETHIOPIE', 'ethiopie', 'et', 6),
-- nextval('PaysSequence'), 'Falkland (Île)', 'FALKLAND (ÎLE)', 'falkland-ile', 'fk', 6),
-- nextval('PaysSequence'), 'Féroé (Îles)', 'FÉROÉ (ÎLES)', 'feroe-iles', 'fo', 6),
-- nextval('PaysSequence'), 'Fidji (République des)', 'FIDJI (RÉPUBLIQUE DES)', 'fidji-republique-des', 'fj', 6),
-- nextval('PaysSequence'), 'Guyane française', 'GUYANE FRANÇAISE', 'guyane-francaise', 'gf', 6),
-- nextval('PaysSequence'), 'Polynésie française', 'POLYNÉSIE FRANÇAISE', 'polynesie-francaise', 'pf', 6),
-- nextval('PaysSequence'), 'Territoires français du sud', 'TERRITOIRES FRANÇAIS DU SUD', 'territoires-francais-du-sud', 'tf', 6),
-- nextval('PaysSequence'), 'Gabon', 'GABON', 'gabon', 'ga', 6),
-- nextval('PaysSequence'), 'Gambie', 'GAMBIE', 'gambie', 'gm', 6),
-- nextval('PaysSequence'), 'Géorgie', 'GÉORGIE', 'georgie', 'ge', 6),
-- nextval('PaysSequence'), 'Ghana', 'GHANA', 'ghana', 'gh', 6),
-- nextval('PaysSequence'), 'Gibraltar', 'GIBRALTAR', 'gibraltar', 'gi', 6),
-- nextval('PaysSequence'), 'Groenland', 'GROENLAND', 'groenland', 'gl', 6),
-- nextval('PaysSequence'), 'Grenade', 'GRENADE', 'grenade', 'gd', 6),
-- nextval('PaysSequence'), 'Guadeloupe', 'GUADELOUPE', 'guadeloupe', 'gp', 6),
-- nextval('PaysSequence'), 'Guam', 'GUAM', 'guam', 'gu', 6),
-- nextval('PaysSequence'), 'Guatemala', 'GUATEMALA', 'guatemala', 'gt', 6),
-- nextval('PaysSequence'), 'Guinée', 'GUINÉE', 'guinee', 'gn', 6),
-- nextval('PaysSequence'), 'Guinée-Bissau', 'GUINÉE-BISSAU', 'guinee-bissau', 'gw', 6),
-- nextval('PaysSequence'), 'Guyane', 'GUYANE', 'guyane', 'gy', 6),
-- nextval('PaysSequence'), 'Haïti', 'HAÏTI', 'haiti', 'ht', 6),
-- nextval('PaysSequence'), 'Heard et McDonald (Îles)', 'HEARD ET MCDONALD (ÎLES)', 'heard-et-mcdonald-iles', 'hm', 6),
-- nextval('PaysSequence'), 'Honduras', 'HONDURAS', 'honduras', 'hn', 6),
-- nextval('PaysSequence'), 'Islande', 'ISLANDE', 'islande', 'is', 6),
-- nextval('PaysSequence'), 'Iran', 'IRAN', 'iran', 'ir', 6),
-- nextval('PaysSequence'), 'Irak', 'IRAK', 'irak', 'iq', 6),
-- nextval('PaysSequence'), 'Côte d''Ivoire', 'CÔTE D''IVOIRE', 'cote-divoire', 'ci', 6),
-- nextval('PaysSequence'), 'Jamaïque', 'JAMAÏQUE', 'jamaique', 'jm', 6),
-- nextval('PaysSequence'), 'Kazakhstan', 'KAZAKHSTAN', 'kazakhstan', 'kz', 6),
-- nextval('PaysSequence'), 'Kenya', 'KENYA', 'kenya', 'ke', 6),
-- nextval('PaysSequence'), 'Kiribati', 'KIRIBATI', 'kiribati', 'ki', 6),
-- nextval('PaysSequence'), 'Corée du Nord', 'CORÉE DU NORD', 'coree-du-nord', 'kp', 6),
-- nextval('PaysSequence'), 'Koweit', 'KOWEIT', 'koweit', 'kw', 6),
-- nextval('PaysSequence'), 'Kirghizistan', 'KIRGHIZISTAN', 'kirghizistan', 'kg', 6),
-- nextval('PaysSequence'), 'Laos', 'LAOS', 'laos', 'la', 6),
-- nextval('PaysSequence'), 'Lettonie', 'LETTONIE', 'lettonie', 'lv', 6),
-- nextval('PaysSequence'), 'Lesotho', 'LESOTHO', 'lesotho', 'ls', 6),
-- nextval('PaysSequence'), 'Libéria', 'LIBÉRIA', 'liberia', 'lr', 6),
-- nextval('PaysSequence'), 'Libye', 'LIBYE', 'libye', 'ly', 6),
-- nextval('PaysSequence'), 'Liechtenstein', 'LIECHTENSTEIN', 'liechtenstein', 'li', 6),
-- nextval('PaysSequence'), 'Lithuanie', 'LITHUANIE', 'lithuanie', 'lt', 6),
-- nextval('PaysSequence'), 'Luxembourg', 'LUXEMBOURG', 'luxembourg', 'lu', 6),
-- nextval('PaysSequence'), 'Macau', 'MACAU', 'macau', 'mo', 6),
-- nextval('PaysSequence'), 'Macédoine', 'MACÉDOINE', 'macedoine', 'mk', 6),
-- nextval('PaysSequence'), 'Madagascar', 'MADAGASCAR', 'madagascar', 'mg', 6),
-- nextval('PaysSequence'), 'Malawi', 'MALAWI', 'malawi', 'mw', 6),
-- nextval('PaysSequence'), 'Maldives (Îles)', 'MALDIVES (ÎLES)', 'maldives-iles', 'mv', 6),
-- nextval('PaysSequence'), 'Mali', 'MALI', 'mali', 'ml', 6),
-- nextval('PaysSequence'), 'Malte', 'MALTE', 'malte', 'mt', 6),
-- nextval('PaysSequence'), 'Marshall (Îles)', 'MARSHALL (ÎLES)', 'marshall-iles', 'mh', 6),
-- nextval('PaysSequence'), 'Martinique', 'MARTINIQUE', 'martinique', 'mq', 6),
-- nextval('PaysSequence'), 'Mauritanie', 'MAURITANIE', 'mauritanie', 'mr', 6),
-- nextval('PaysSequence'), 'Maurice', 'MAURICE', 'maurice', 'mu', 6),
-- nextval('PaysSequence'), 'Mayotte', 'MAYOTTE', 'mayotte', 'yt', 6),
-- nextval('PaysSequence'), 'Micronésie (États fédérés de)', 'MICRONÉSIE (ÉTATS FÉDÉRÉS DE)', 'micronesie-etats-federes-de', 'fm', 6),
-- nextval('PaysSequence'), 'Moldavie', 'MOLDAVIE', 'moldavie', 'md', 6),
-- nextval('PaysSequence'), 'Monaco', 'MONACO', 'monaco', 'mc', 6),
-- nextval('PaysSequence'), 'Mongolie', 'MONGOLIE', 'mongolie', 'mn', 6),
-- nextval('PaysSequence'), 'Montserrat', 'MONTSERRAT', 'montserrat', 'ms', 6),
-- nextval('PaysSequence'), 'Mozambique', 'MOZAMBIQUE', 'mozambique', 'mz', 6),
-- nextval('PaysSequence'), 'Myanmar', 'MYANMAR', 'myanmar', 'mm', 6),
-- nextval('PaysSequence'), 'Namibie', 'NAMIBIE', 'namibie', 'na', 6),
-- nextval('PaysSequence'), 'Nauru', 'NAURU', 'nauru', 'nr', 6),
-- nextval('PaysSequence'), 'Nepal', 'NEPAL', 'nepal', 'np', 6),
-- nextval('PaysSequence'), 'Antilles néerlandaises', 'ANTILLES NÉERLANDAISES', 'antilles-neerlandaises', 'an', 6),
-- nextval('PaysSequence'), 'Nouvelle Calédonie', 'NOUVELLE CALÉDONIE', 'nouvelle-caledonie', 'nc', 6),
-- nextval('PaysSequence'), 'Nicaragua', 'NICARAGUA', 'nicaragua', 'ni', 6),
-- nextval('PaysSequence'), 'Niger', 'NIGER', 'niger', 'ne', 6),
-- nextval('PaysSequence'), 'Nigeria', 'NIGERIA', 'nigeria', 'ng', 6),
-- nextval('PaysSequence'), 'Niue', 'NIUE', 'niue', 'nu', 6),
-- nextval('PaysSequence'), 'Norfolk (Îles)', 'NORFOLK (ÎLES)', 'norfolk-iles', 'nf', 6),
-- nextval('PaysSequence'), 'Mariannes du Nord (Îles)', 'MARIANNES DU NORD (ÎLES)', 'mariannes-du-nord-iles', 'mp', 6),
-- nextval('PaysSequence'), 'Oman', 'OMAN', 'oman', 'om', 6),
-- nextval('PaysSequence'), 'Palau', 'PALAU', 'palau', 'pw', 6),
-- nextval('PaysSequence'), 'Panama', 'PANAMA', 'panama', 'pa', 6),
-- nextval('PaysSequence'), 'Papouasie-Nouvelle-Guinée', 'PAPOUASIE-NOUVELLE-GUINÉE', 'papouasie-nouvelle-guinee', 'pg', 6),
-- nextval('PaysSequence'), 'Paraguay', 'PARAGUAY', 'paraguay', 'py', 6),
-- nextval('PaysSequence'), 'Pitcairn (Îles)', 'PITCAIRN (ÎLES)', 'pitcairn-iles', 'pn', 6),
-- nextval('PaysSequence'), 'Qatar', 'QATAR', 'qatar', 'qa', 6),
-- nextval('PaysSequence'), 'Réunion (La)', 'RÉUNION (LA)', 'reunion-la', 're', 6),
-- nextval('PaysSequence'), 'Rwanda', 'RWANDA', 'rwanda', 'rw', 6),
-- nextval('PaysSequence'), 'Géorgie du Sud et Sandwich du Sud (Îles)', 'GÉORGIE DU SUD ET SANDWICH DU SUD (ÎLES)', 'georgie-du-sud-et-sandwich-du-sud-iles', 'gs', 6),
-- nextval('PaysSequence'), 'Saint-Kitts et Nevis', 'SAINT-KITTS ET NEVIS', 'saint-kitts-et-nevis', 'kn', 6),
-- nextval('PaysSequence'), 'Sainte Lucie', 'SAINTE LUCIE', 'sainte-lucie', 'lc', 6),
-- nextval('PaysSequence'), 'Saint Vincent et les Grenadines', 'SAINT VINCENT ET LES GRENADINES', 'saint-vincent-et-les-grenadines', 'vc', 6),
-- nextval('PaysSequence'), 'Samoa', 'SAMOA', 'samoa', 'ws', 6),
-- nextval('PaysSequence'), 'Saint-Marin (Rép. de)', 'SAINT-MARIN (RÉP. DE)', 'saint-marin-rep-de', 'sm', 6),
-- nextval('PaysSequence'), 'São Tomé et Príncipe (Rép.)', 'SÃO TOMÉ ET PRÍNCIPE (RÉP.)', 'sao-tome-et-principe-rep', 'st', 6),
-- nextval('PaysSequence'), 'Sénégal', 'SÉNÉGAL', 'senegal', 'sn', 6),
-- nextval('PaysSequence'), 'Seychelles', 'SEYCHELLES', 'seychelles', 'sc', 6),
-- nextval('PaysSequence'), 'Sierra Leone', 'SIERRA LEONE', 'sierra-leone', 'sl', 6),
-- nextval('PaysSequence'), 'Slovaquie', 'SLOVAQUIE', 'slovaquie', 'sk', 6),
-- nextval('PaysSequence'), 'Slovénie', 'SLOVÉNIE', 'slovenie', 'si', 6),
-- nextval('PaysSequence'), 'Somalie', 'SOMALIE', 'somalie', 'so', 6),
-- nextval('PaysSequence'), 'Sri Lanka', 'SRI LANKA', 'sri-lanka', 'lk', 6),
-- nextval('PaysSequence'), 'Sainte Hélène', 'SAINTE HÉLÈNE', 'sainte-helene', 'sh', 6),
-- nextval('PaysSequence'), 'Saint Pierre et Miquelon', 'SAINT PIERRE ET MIQUELON', 'saint-pierre-et-miquelon', 'pm', 6),
-- nextval('PaysSequence'), 'Soudan', 'SOUDAN', 'soudan', 'sd', 6),
-- nextval('PaysSequence'), 'Suriname', 'SURINAME', 'suriname', 'sr', 6),
-- nextval('PaysSequence'), 'Svalbard et Jan Mayen (Îles)', 'SVALBARD ET JAN MAYEN (ÎLES)', 'svalbard-et-jan-mayen-iles', 'sj', 6),
-- nextval('PaysSequence'), 'Swaziland', 'SWAZILAND', 'swaziland', 'sz', 6),
-- nextval('PaysSequence'), 'Syrie', 'SYRIE', 'syrie', 'sy', 6),
-- nextval('PaysSequence'), 'Tadjikistan', 'TADJIKISTAN', 'tadjikistan', 'tj', 6),
-- nextval('PaysSequence'), 'Tanzanie', 'TANZANIE', 'tanzanie', 'tz', 6),
-- nextval('PaysSequence'), 'Togo', 'TOGO', 'togo', 'tg', 6),
-- nextval('PaysSequence'), 'Tokelau', 'TOKELAU', 'tokelau', 'tk', 6),
-- nextval('PaysSequence'), 'Tonga', 'TONGA', 'tonga', 'to', 6),
-- nextval('PaysSequence'), 'Trinité et Tobago', 'TRINITÉ ET TOBAGO', 'trinite-et-tobago', 'tt', 6),
-- nextval('PaysSequence'), 'Tunisie', 'TUNISIE', 'tunisie', 'tn', 6),
-- nextval('PaysSequence'), 'Turkménistan', 'TURKMÉNISTAN', 'turkmenistan', 'tm', 6),
-- nextval('PaysSequence'), 'Turks et Caïques (Îles)', 'TURKS ET CAÏQUES (ÎLES)', 'turks-et-caiques-iles', 'tc', 6),
-- nextval('PaysSequence'), 'Tuvalu', 'TUVALU', 'tuvalu', 'tv', 6),
-- nextval('PaysSequence'), 'Îles Mineures Éloignées des États-Unis', 'ÎLES MINEURES ÉLOIGNÉES DES ÉTATS-UNIS', 'iles-mineures-eloignees-des-etats-unis', 'um', 6),
-- nextval('PaysSequence'), 'Ouganda', 'OUGANDA', 'ouganda', 'ug', 6),
-- nextval('PaysSequence'), 'Uruguay', 'URUGUAY', 'uruguay', 'uy', 6),
-- nextval('PaysSequence'), 'Ouzbékistan', 'OUZBÉKISTAN', 'ouzbekistan', 'uz', 6),
-- nextval('PaysSequence'), 'Vanuatu', 'VANUATU', 'vanuatu', 'vu', 6),
-- nextval('PaysSequence'), 'Vatican (Etat du)', 'VATICAN (ETAT DU)', 'vatican-etat-du', 'va', 6),
-- nextval('PaysSequence'), 'Vietnam', 'VIETNAM', 'vietnam', 'vn', 6),
-- nextval('PaysSequence'), 'Vierges (Îles)', 'VIERGES (ÎLES)', 'vierges-iles', 'vi', 6),
-- nextval('PaysSequence'), 'Wallis et Futuna (Îles)', 'WALLIS ET FUTUNA (ÎLES)', 'wallis-et-futuna-iles', 'wf', 6),
-- nextval('PaysSequence'), 'Sahara Occidental', 'SAHARA OCCIDENTAL', 'sahara-occidental', 'eh', 6),
-- nextval('PaysSequence'), 'Yemen', 'YEMEN', 'yemen', 'ye', 6),
-- nextval('PaysSequence'), 'Zaïre', 'ZAÏRE', 'zaire', 'zr', 6),
-- nextval('PaysSequence'), 'Zambie', 'ZAMBIE', 'zambie', 'zm', 6),
-- nextval('PaysSequence'), 'Zimbabwe', 'ZIMBABWE', 'zimbabwe', 'zw', 6),
-- nextval('PaysSequence'), 'La Barbad', 'LA BARBAD', 'la-barbad', 'bb', 6);

INSERT INTO Pays (pays_ID, pays_nom, pays_nomMinuscule, pays_nomMajuscule/*, pays_latitude, pays_longitude*/, pays_code, pays_zoom) VALUES 
(nextval('PaysSequence'), 'France', 'FRANCE', 'france', 'fr', 6),
(nextval('PaysSequence'), 'Espagne', 'ESPAGNE', 'espagne', 'es', 6),
(nextval('PaysSequence'), 'Allemagne', 'ALLEMAGNE', 'allemagne', 'de', 6);

-- TODO : Latitude et longitude doivent etre insere par un webservice ou une API
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

INSERT INTO EntrepriseVilleSecteurCentralien (entrepriseVilleSecteurCentralien_ID, entrepriseVilleSecteurCentralien_centralien_ID, entrepriseVilleSecteurCentralien_entrepriseVilleSecteur_ID) VALUES
(nextval('EntrepriseVilleSecteurCentralienSequence'), 1, 1),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 2, 19),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 3, 18),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 4, 26),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 5, 27),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 6, 33),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 7, 26),
(nextval('EntrepriseVilleSecteurCentralienSequence'), 8, 36);

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

INSERT INTO EcoleSecteurCentralien (ecoleSecteurCentralien_ID, ecoleSecteurCentralien_ecoleSecteur_ID, ecoleSecteurCentralien_centralien_ID) VALUES
(nextval('EcoleSecteurCentralienSequence'),1,1),
(nextval('EcoleSecteurCentralienSequence'),5,1),
(nextval('EcoleSecteurCentralienSequence'),2,4),
(nextval('EcoleSecteurCentralienSequence'),3,5),
(nextval('EcoleSecteurCentralienSequence'),4,6);

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
