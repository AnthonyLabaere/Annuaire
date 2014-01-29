------------------------------------------------------------------------------------------------------------------------------
-- Ce script insere des donnees de test les tables suivantes :
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

INSERT INTO Pays (pays_ID, pays_Nom) VALUES (nextval('PaysSequence'), 'France');
INSERT INTO Pays (pays_ID, pays_Nom) VALUES (nextval('PaysSequence'), 'Espagne');
INSERT INTO Pays (pays_ID, pays_Nom) VALUES (nextval('PaysSequence'), 'Allemagne');

INSERT INTO Ville (ville_ID, ville_Nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Nantes', 1);
INSERT INTO Ville (ville_ID, ville_Nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Paris', 1);
INSERT INTO Ville (ville_ID, ville_Nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Lille', 1);
INSERT INTO Ville (ville_ID, ville_Nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Lyon', 1);
INSERT INTO Ville (ville_ID, ville_Nom, ville_pays_ID) VALUES (nextval('VilleSequence'), 'Marseille', 1);

# --- !Downs

DELETE FROM Ville CASCADE;
DELETE FROM Pays CASCADE;

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
