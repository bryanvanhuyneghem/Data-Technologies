create table personen (
    id int NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT PERSONEN_PK PRIMARY KEY,
    naam varchar(100) NOT NULL,
    geboortedatum date NOT NULL,
    geslacht char NOT NULL
);



