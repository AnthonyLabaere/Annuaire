# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table cv (
  id                        integer not null,
  filename                  varchar(255),
  constraint pk_cv primary key (id))
;

create table city (
  id                        integer not null,
  name                      varchar(255),
  country_id                varchar(255),
  constraint pk_city primary key (id))
;

create table contact (
  id                        integer not null,
  person1_uid               varchar(255),
  person2_uid               varchar(255),
  type_c_id                 integer,
  constraint pk_contact primary key (id))
;

create table contact_type (
  id                        integer not null,
  type_c                    varchar(255),
  constraint pk_contact_type primary key (id))
;

create table country (
  id                        varchar(255) not null,
  name                      varchar(255),
  nationality               varchar(255),
  constraint pk_country primary key (id))
;

create table options (
  id                        integer not null,
  show_mail_id              integer,
  constraint pk_options primary key (id))
;

create table person (
  uid                       varchar(255) not null,
  name                      varchar(255),
  surname                   varchar(255),
  birthday                  timestamp,
  mail                      varchar(255),
  phone                     varchar(255),
  skype                     varchar(255),
  school_of_origin_id       integer,
  nationality_id            varchar(255),
  photo_id                  integer,
  cv_id                     integer,
  options_id                integer,
  constraint pk_person primary key (uid))
;

create table photo (
  id                        integer not null,
  filename                  varchar(255),
  constraint pk_photo primary key (id))
;

create table plan (
  id                        integer not null,
  person_uid                varchar(255),
  city_id                   integer,
  for_what                  TEXT,
  but_when                  TEXT,
  constraint pk_plan primary key (id))
;

create table school (
  id                        integer not null,
  name                      varchar(255),
  city_id                   integer,
  constraint pk_school primary key (id))
;

create table travelled (
  id                        integer not null,
  person_uid                varchar(255),
  city_id                   integer,
  for_what                  TEXT,
  but_when                  TEXT,
  constraint pk_travelled primary key (id))
;

create table whereabouts (
  id                        integer not null,
  person_uid                varchar(255),
  city_id                   integer,
  constraint pk_whereabouts primary key (id))
;

create sequence cv_seq;

create sequence city_seq;

create sequence contact_seq;

create sequence contact_type_seq;

create sequence country_seq;

create sequence options_seq;

create sequence person_seq;

create sequence photo_seq;

create sequence plan_seq;

create sequence school_seq;

create sequence travelled_seq;

create sequence whereabouts_seq;

alter table city add constraint fk_city_country_1 foreign key (country_id) references country (id);
create index ix_city_country_1 on city (country_id);
alter table contact add constraint fk_contact_person1_2 foreign key (person1_uid) references person (uid);
create index ix_contact_person1_2 on contact (person1_uid);
alter table contact add constraint fk_contact_person2_3 foreign key (person2_uid) references person (uid);
create index ix_contact_person2_3 on contact (person2_uid);
alter table contact add constraint fk_contact_typeC_4 foreign key (type_c_id) references contact_type (id);
create index ix_contact_typeC_4 on contact (type_c_id);
alter table options add constraint fk_options_showMail_5 foreign key (show_mail_id) references contact_type (id);
create index ix_options_showMail_5 on options (show_mail_id);
alter table person add constraint fk_person_schoolOfOrigin_6 foreign key (school_of_origin_id) references school (id);
create index ix_person_schoolOfOrigin_6 on person (school_of_origin_id);
alter table person add constraint fk_person_nationality_7 foreign key (nationality_id) references country (id);
create index ix_person_nationality_7 on person (nationality_id);
alter table person add constraint fk_person_photo_8 foreign key (photo_id) references photo (id);
create index ix_person_photo_8 on person (photo_id);
alter table person add constraint fk_person_cv_9 foreign key (cv_id) references cv (id);
create index ix_person_cv_9 on person (cv_id);
alter table person add constraint fk_person_options_10 foreign key (options_id) references options (id);
create index ix_person_options_10 on person (options_id);
alter table plan add constraint fk_plan_person_11 foreign key (person_uid) references person (uid);
create index ix_plan_person_11 on plan (person_uid);
alter table plan add constraint fk_plan_city_12 foreign key (city_id) references city (id);
create index ix_plan_city_12 on plan (city_id);
alter table school add constraint fk_school_city_13 foreign key (city_id) references city (id);
create index ix_school_city_13 on school (city_id);
alter table travelled add constraint fk_travelled_person_14 foreign key (person_uid) references person (uid);
create index ix_travelled_person_14 on travelled (person_uid);
alter table travelled add constraint fk_travelled_city_15 foreign key (city_id) references city (id);
create index ix_travelled_city_15 on travelled (city_id);
alter table whereabouts add constraint fk_whereabouts_person_16 foreign key (person_uid) references person (uid);
create index ix_whereabouts_person_16 on whereabouts (person_uid);
alter table whereabouts add constraint fk_whereabouts_city_17 foreign key (city_id) references city (id);
create index ix_whereabouts_city_17 on whereabouts (city_id);



# --- !Downs

drop table if exists cv cascade;

drop table if exists city cascade;

drop table if exists contact cascade;

drop table if exists contact_type cascade;

drop table if exists country cascade;

drop table if exists options cascade;

drop table if exists person cascade;

drop table if exists photo cascade;

drop table if exists plan cascade;

drop table if exists school cascade;

drop table if exists travelled cascade;

drop table if exists whereabouts cascade;

drop sequence if exists cv_seq;

drop sequence if exists city_seq;

drop sequence if exists contact_seq;

drop sequence if exists contact_type_seq;

drop sequence if exists country_seq;

drop sequence if exists options_seq;

drop sequence if exists person_seq;

drop sequence if exists photo_seq;

drop sequence if exists plan_seq;

drop sequence if exists school_seq;

drop sequence if exists travelled_seq;

drop sequence if exists whereabouts_seq;

