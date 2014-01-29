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

INSERT INTO Pays (pays_ID, pays_Nom) VALUES (nextval('PaysSequence'), 'France');
INSERT INTO Pays (pays_ID, pays_Nom) VALUES (nextval('PaysSequence'), 'Espagne');
INSERT INTO Pays (pays_ID, pays_Nom) VALUES (nextval('PaysSequence'), 'Allemagne');

# --- !Downs

DELETE FROM Pays CASCADE;

