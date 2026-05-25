
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
    PRIMARY KEY (id_user) -- Corectat din (id) în (id_user)
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
    FOREIGN KEY (id_buyer) REFERENCES utilizator(id_user) ON DELETE CASCADE, -- Corectat id_user -> id_buyer
    CONSTRAINT chk_categorie_buyer CHECK (categorie_preferata IN ('MOBILA', 'ARTA', 'HAINE', 'AUTOMOBILE', 'BIJUTERII'))
);

CREATE TABLE utilizator_premiumbuyer (
    id_pBuyer INT,
     discount_rate FLOAT DEFAULT 0.0,
     PRIMARY KEY (id_pBuyer),
     FOREIGN KEY (id_pBuyer) REFERENCES utilizator_buyer(id_buyer) ON DELETE CASCADE -- Corectat cheile
);


CREATE TABLE produs (
    id_produs INT AUTO_INCREMENT,
    nume VARCHAR(150) NOT NULL,
    categorie VARCHAR(50) NOT NULL,
    colectie VARCHAR(100),
    id_seller INT NOT NULL, -- Schimbat din id_user în id_seller pentru claritate
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