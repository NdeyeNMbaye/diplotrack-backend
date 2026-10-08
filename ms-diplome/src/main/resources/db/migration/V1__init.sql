CREATE TABLE diplome (
                         id BIGSERIAL PRIMARY KEY,
                         intitule VARCHAR(200) NOT NULL,
                         niveau VARCHAR(100) NOT NULL,
                         annee_obtention INTEGER NOT NULL,
                         numero_identification VARCHAR(100) NOT NULL UNIQUE,
                         qr_code VARCHAR(500),
                         date_enregistrement TIMESTAMP NOT NULL DEFAULT NOW(),
                         statut VARCHAR(50) NOT NULL DEFAULT 'ENREGISTRE',
                         utilisateur_id BIGINT NOT NULL
);

CREATE TABLE demande_verification (
                                      id BIGSERIAL PRIMARY KEY,
                                      diplome_id BIGINT REFERENCES diplome(id),
                                      nom_diplome VARCHAR(100) NOT NULL,
                                      prenom_diplome VARCHAR(100) NOT NULL,
                                      numero_diplome VARCHAR(100) NOT NULL,
                                      motif TEXT,
                                      identite_demandeur VARCHAR(200) NOT NULL,
                                      contact_demandeur VARCHAR(150) NOT NULL,
                                      date_demande TIMESTAMP NOT NULL DEFAULT NOW(),
                                      date_traitement TIMESTAMP,
                                      statut VARCHAR(50) NOT NULL DEFAULT 'EN_ATTENTE',
                                      resultat VARCHAR(50)
);