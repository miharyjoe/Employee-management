CREATE TABLE IF NOT EXISTS employee (
                          id SERIAL PRIMARY KEY,
                          firstname VARCHAR NOT NULL,
                          lastname VARCHAR NOT NULL,
                          birthdate DATE,
                          matricule VARCHAR NOT NULL UNIQUE,
                          image TEXT,
                          sexe VARCHAR NOT NULL,
                          telephones TEXT[],
                          address VARCHAR NOT NULL,
                          email_perso VARCHAR,
                          email_pro VARCHAR,
                          cin_number BIGINT NOT NULL UNIQUE,
                          cin_date DATE NOT NULL,
                          place_cin VARCHAR NOT NULL,
                          fonction VARCHAR NOT NULL,
                          children_charge INTEGER,
                          hire_date DATE NOT NULL,
                          departure_date DATE,
                          csp VARCHAR,
                          num_cnaps VARCHAR
);

CREATE SEQUENCE hibernate_sequence START 1;