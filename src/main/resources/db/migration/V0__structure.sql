-- Cleanup (not anymore)
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS wot_user;

-- Create
CREATE TABLE client
(
    id        SERIAL PRIMARY KEY,
    telephone VARCHAR(10),
    address   VARCHAR(50)
);

create table wot_user
(
    email    varchar(20),
    firstname VARCHAR(50) NOT NULL,
    lastname  VARCHAR(50) NOT NULL,
    primary key (email),
    role     varchar(10), -- "ADMIN" ou "CLIENT"
    password varchar(500) not null
);


alter table client
    add column email varchar(20) unique,
    add foreign key (email) references wot_user (email);