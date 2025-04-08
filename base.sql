drop database form_session;
CREATE database form_session;
use form_session;

CREATE TABLE client (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(100) NOT NULL
);


CREATE TABLE credit (
    id_credit INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(100) NOT NULL UNIQUE,
    solde DOUBLE DEFAULT 0
);


CREATE TABLE depense (
    id_depense INT PRIMARY KEY AUTO_INCREMENT,
    credit_id INT,
    montant DOUBLE DEFAULT 0,
    FOREIGN KEY (credit_id) REFERENCES credit(id_credit) ON DELETE CASCADE
);

INSERT INTO client (email, mot_de_passe)
VALUES ('test@email.com', '1234');

