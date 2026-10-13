CREATE TABLE utilisateur (
                             id BIGSERIAL PRIMARY KEY,
                             nom VARCHAR(100) NOT NULL,
                             prenom VARCHAR(100) NOT NULL,
                             email VARCHAR(150) NOT NULL UNIQUE,
                             mot_de_passe VARCHAR(255) NOT NULL,
                             actif BOOLEAN NOT NULL DEFAULT TRUE,
                             date_creation TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE diplome_utilisateur (
                                     id BIGINT PRIMARY KEY REFERENCES utilisateur(id),
                                     matricule VARCHAR(50) NOT NULL,
                                     promotion VARCHAR(50),
                                     filiere VARCHAR(100)
);

CREATE TABLE agent_scolarite (
                                 id BIGINT PRIMARY KEY REFERENCES utilisateur(id),
                                 matricule VARCHAR(50) NOT NULL
);

CREATE TABLE agent_coip (
                            id BIGINT PRIMARY KEY REFERENCES utilisateur(id),
                            matricule VARCHAR(50) NOT NULL
);

CREATE TABLE entreprise_partenaire (
                                       id BIGINT PRIMARY KEY REFERENCES utilisateur(id),
                                       nom_entreprise VARCHAR(150) NOT NULL,
                                       secteur VARCHAR(100),
                                       contact VARCHAR(100)
);

CREATE TABLE administrateur (
                                id BIGINT PRIMARY KEY REFERENCES utilisateur(id),
                                matricule VARCHAR(50) NOT NULL
);

CREATE TABLE profil (
                        id BIGSERIAL PRIMARY KEY,
                        utilisateur_id BIGINT REFERENCES utilisateur(id),
                        bio TEXT,
                        lien_externe VARCHAR(255),
                        disponibilite BOOLEAN,
                        date_creation TIMESTAMP NOT NULL DEFAULT NOW(),
                        date_modification TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE notification (
                              id BIGSERIAL PRIMARY KEY,
                              utilisateur_id BIGINT REFERENCES utilisateur(id),
                              message TEXT NOT NULL,
                              date_envoi TIMESTAMP NOT NULL DEFAULT NOW(),
                              lu BOOLEAN NOT NULL DEFAULT FALSE,
                              type VARCHAR(50)
);