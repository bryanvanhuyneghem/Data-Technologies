-- create table films (
--     id int NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT FILMS_PK PRIMARY KEY,
--     titel varchar(100) NOT NULL,
--     speelduur time NOT NULL,
--     beoordeling varchar(5) NOT NULL
-- );
-- 
-- create table personen (
--     id int NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT PERSONEN_PK PRIMARY KEY,
--     naam varchar(100) NOT NULL,
--     geboortedatum date NOT NULL,
--     geslacht char NOT NULL
-- );
-- 
-- create table filmcast (
--     film int NOT NULL,
--     acteur int NOT NULL,
--     personage varchar(100) NOT NULL,
--     FOREIGN KEY (film) REFERENCES films(id),
--     FOREIGN KEY (acteur) REFERENCES personen(id)
-- );
create table regisseurs (
    film int NOT NULL,
    regisseur int NOT NULL,
    FOREIGN KEY (film) REFERENCES films(id),
    FOREIGN KEY (regisseur) REFERENCES personen(id)
);



