-- Cleanup (not anymore)
-- DROP DATABASE IF EXISTS postgres;
-- CREATE DATABASE postgres;
-- USE postgres;

-- Create
CREATE TABLE client
(
    id        SERIAL PRIMARY KEY,
    telephone VARCHAR(10),
    address   VARCHAR(200)
);

create table wot_user
(
    email     varchar(100),
    firstname VARCHAR(50)  NOT NULL,
    lastname  VARCHAR(50)  NOT NULL,
    primary key (email),
    role      varchar(10), -- "ADMIN" ou "CLIENT"
    password  varchar(500) not null
);

create table thing_type
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(100) NOT NULL,
    typeAsJson  TEXT
);

create table thing_property
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(50)  NOT NULL,
    value          VARCHAR(100) NOT NULL,
    metadataAsJson TEXT
);

create table thing_in_store
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50)  NOT NULL,
    description VARCHAR(100) NOT NULL,
    prix        DECIMAL      NOT NULL,
    started     BOOLEAN      DEFAULT FALSE
);


alter table client
    add column email varchar(20) unique,
    add foreign key (email) references wot_user (email);

alter table thing_property
    add column thingtypeid SERIAL,
    add foreign key (thingtypeid) references thing_type (id);

alter table thing_in_store
    add column thingtypeid SERIAL NOT NULL,
    add column clientid INT NULL DEFAULT NULL,
    add foreign key (thingtypeid) references thing_type (id),
    add foreign key (clientid) references client (id);