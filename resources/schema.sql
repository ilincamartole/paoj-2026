DROP TABLE IF EXISTS oferta;
DROP TABLE IF EXISTS licitatie;
DROP TABLE IF EXISTS produs;
DROP TABLE IF EXISTS utilizator_premiumbuyer;
DROP TABLE IF EXISTS utilizator_buyer;
DROP TABLE IF EXISTS utilizator_seller;
DROP TABLE IF EXISTS utilizator;


CREATE TABLE utilizator (
                            id_user INT AUTO_INCREMENT,
                            nume VARCHAR(100) NOT NULL,
                            cnp VARCHAR(13) NOT NULL UNIQUE,
                            PRIMARY KEY (id_user)
);

CREATE TABLE utilizator_seller (
                                   id_seller INT,
                                   rating DOUBLE DEFAULT 0.0,
                                   PRIMARY KEY (id_seller),
                                   FOREIGN KEY (id_seller) REFERENCES utilizator(id_user) ON DELETE CASCADE
);

CREATE TABLE utilizator_buyer (
                                  id_buyer INT,
                                  categorie_preferata VARCHAR(50),
                                  PRIMARY KEY (id_buyer),
                                  FOREIGN KEY (id_buyer) REFERENCES utilizator(id_user) ON DELETE CASCADE,
                                  CONSTRAINT chk_categorie_buyer CHECK (categorie_preferata IN ('MOBILA', 'ARTA', 'HAINE', 'AUTOMOBILE', 'BIJUTERII'))
);

CREATE TABLE utilizator_premiumbuyer (
                                         id_pBuyer INT,
                                         discount_rate FLOAT DEFAULT 0.0,
                                         PRIMARY KEY (id_pBuyer),
                                         FOREIGN KEY (id_pBuyer) REFERENCES utilizator_buyer(id_buyer) ON DELETE CASCADE
);

CREATE TABLE produs (
                        id_produs INT AUTO_INCREMENT,
                        nume VARCHAR(150) NOT NULL,
                        categorie VARCHAR(50) NOT NULL,
                        colectie VARCHAR(100),
                        id_seller INT NOT NULL,
                        PRIMARY KEY (id_produs),
                        FOREIGN KEY (id_seller) REFERENCES utilizator_seller(id_seller) ON DELETE CASCADE,
                        CONSTRAINT chk_categorie_produs CHECK (categorie IN ('MOBILA', 'ARTA', 'HAINE', 'AUTOMOBILE', 'BIJUTERII'))
);

CREATE TABLE licitatie (
                           id_licitatie INT AUTO_INCREMENT,
                           id_produs INT NOT NULL UNIQUE,
                           min_value BIGINT NOT NULL,
                           PRIMARY KEY (id_licitatie),
                           FOREIGN KEY (id_produs) REFERENCES produs(id_produs) ON DELETE CASCADE
);

CREATE TABLE oferta (
                        id_oferta INT AUTO_INCREMENT,
                        valoare INT NOT NULL,
                        id_buyer INT NOT NULL,
                        id_licitatie INT NOT NULL,
                        PRIMARY KEY (id_oferta),
                        FOREIGN KEY (id_buyer) REFERENCES utilizator_buyer(id_buyer) ON DELETE CASCADE,
                        FOREIGN KEY (id_licitatie) REFERENCES licitatie(id_licitatie) ON DELETE CASCADE
);

-- ==========================================
-- DATE DE TEST
-- ==========================================

-- Selleri
INSERT INTO utilizator (nume, cnp) VALUES ('Gigel', '200');
INSERT INTO utilizator (nume, cnp) VALUES ('Vlad', '201');
INSERT INTO utilizator_seller (id_seller, rating) VALUES (1, 4.5);
INSERT INTO utilizator_seller (id_seller, rating) VALUES (2, 3.8);

-- Buyeri
INSERT INTO utilizator (nume, cnp) VALUES ('Ion', '123');
INSERT INTO utilizator (nume, cnp) VALUES ('Maria', '124');
INSERT INTO utilizator (nume, cnp) VALUES ('Alex', '125');
INSERT INTO utilizator (nume, cnp) VALUES ('Elena', '126');
INSERT INTO utilizator_buyer (id_buyer, categorie_preferata) VALUES (3, 'ARTA');
INSERT INTO utilizator_buyer (id_buyer, categorie_preferata) VALUES (4, 'MOBILA');
INSERT INTO utilizator_buyer (id_buyer, categorie_preferata) VALUES (5, 'BIJUTERII');
INSERT INTO utilizator_buyer (id_buyer, categorie_preferata) VALUES (6, 'ARTA');

-- Produse
INSERT INTO produs (nume, categorie, colectie, id_seller) VALUES ('Tablou Van Gogh', 'ARTA', 'Colectia 1', 1);
INSERT INTO produs (nume, categorie, colectie, id_seller) VALUES ('Canapea IKEA', 'MOBILA', 'Living', 2);
INSERT INTO produs (nume, categorie, colectie, id_seller) VALUES ('Inel aur', 'BIJUTERII', 'Luxury', 1);
INSERT INTO produs (nume, categorie, colectie, id_seller) VALUES ('Sculptura moderna', 'ARTA', 'Colectia 2', 2);

-- Licitatii
INSERT INTO licitatie (id_produs, min_value) VALUES (1, 1000);
INSERT INTO licitatie (id_produs, min_value) VALUES (2, 500);
INSERT INTO licitatie (id_produs, min_value) VALUES (3, 2000);
INSERT INTO licitatie (id_produs, min_value) VALUES (4, 1500);

-- Oferte
INSERT INTO oferta (valoare, id_buyer, id_licitatie) VALUES (1200, 3, 1);
INSERT INTO oferta (valoare, id_buyer, id_licitatie) VALUES (1300, 4, 1);
INSERT INTO oferta (valoare, id_buyer, id_licitatie) VALUES (1400, 5, 1);
INSERT INTO oferta (valoare, id_buyer, id_licitatie) VALUES (600, 4, 2);
INSERT INTO oferta (valoare, id_buyer, id_licitatie) VALUES (700, 3, 2);
INSERT INTO oferta (valoare, id_buyer, id_licitatie) VALUES (2100, 5, 3);
INSERT INTO oferta (valoare, id_buyer, id_licitatie) VALUES (2200, 6, 3);
INSERT INTO oferta (valoare, id_buyer, id_licitatie) VALUES (2300, 3, 3);
INSERT INTO oferta (valoare, id_buyer, id_licitatie) VALUES (1600, 6, 4);
INSERT INTO oferta (valoare, id_buyer, id_licitatie) VALUES (1700, 4, 4);