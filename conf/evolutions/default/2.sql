# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

INSERT INTO country(id,name,nationality) VALUES
(1,'United States','American'),
(2,'France','French');

INSERT INTO city(id,name,country_id) VALUES
(1,'Nantes',2);

INSERT INTO school(id,name,city_id) VALUES
(1,'Ecole Centrale de Nantes',1);


SET FOREIGN_KEY_CHECKS=0;

DELETE FROM country;
DELETE FROM city;
DELETE FROM school;

SET FOREIGN_KEY_CHECKS=1;
