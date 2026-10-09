CREATE TABLE competence (
                            id BIGSERIAL PRIMARY KEY,
                            intitule VARCHAR(150) NOT NULL,
                            categorie VARCHAR(100),
                            niveau VARCHAR(50),
                            description TEXT,
                            lien_ressource VARCHAR(255),
                            date_soumission TIMESTAMP NOT NULL DEFAULT NOW(),
                            date_approbation TIMESTAMP,
                            statut VARCHAR(50) NOT NULL DEFAULT 'EN_ATTENTE',
                            profil_id BIGINT NOT NULL,
                            agent_coip_id BIGINT
);

CREATE TABLE besoin_recrutement (
                                    id BIGSERIAL PRIMARY KEY,
                                    poste VARCHAR(150) NOT NULL,
                                    description TEXT,
                                    competences_requises TEXT,
                                    date_soumission TIMESTAMP NOT NULL DEFAULT NOW(),
                                    statut VARCHAR(50) NOT NULL DEFAULT 'EN_ATTENTE',
                                    entreprise_partenaire_id BIGINT NOT NULL,
                                    agent_coip_id BIGINT
);

CREATE TABLE proposition_profil (
                                    id BIGSERIAL PRIMARY KEY,
                                    besoin_recrutement_id BIGINT NOT NULL REFERENCES besoin_recrutement(id),
                                    date_proposition TIMESTAMP NOT NULL DEFAULT NOW(),
                                    statut VARCHAR(50) NOT NULL DEFAULT 'PROPOSEE',
                                    profil_id BIGINT NOT NULL,
                                    agent_coip_id BIGINT NOT NULL
);