CREATE TABLE offre (
                       id BIGSERIAL PRIMARY KEY,
                       titre VARCHAR(200) NOT NULL,
                       description TEXT,
                       domaine VARCHAR(100),
                       localisation VARCHAR(100),
                       date_publication TIMESTAMP NOT NULL DEFAULT NOW(),
                       date_expiration TIMESTAMP,
                       type VARCHAR(50) NOT NULL,
                       statut VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
                       agent_coip_id BIGINT NOT NULL
);

CREATE TABLE candidature (
                             id BIGSERIAL PRIMARY KEY,
                             offre_id BIGINT REFERENCES offre(id),
                             date_soumission TIMESTAMP NOT NULL DEFAULT NOW(),
                             statut VARCHAR(50) NOT NULL DEFAULT 'EN_ATTENTE',
                             diplome_utilisateur_id BIGINT NOT NULL
);