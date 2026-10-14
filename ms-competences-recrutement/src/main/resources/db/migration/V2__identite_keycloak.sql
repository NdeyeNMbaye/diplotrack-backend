-- Identité des acteurs : le sub Keycloak remplace les identifiants numériques saisis
-- par le client (Database-per-Service : aucune clé étrangère vers db_utilisateur).

-- F04, F11 : diplômé qui soumet, agent COIP qui décide
ALTER TABLE competence
ALTER COLUMN profil_id     TYPE VARCHAR(100) USING profil_id::text,
    ALTER COLUMN agent_coip_id TYPE VARCHAR(100) USING agent_coip_id::text;

-- F14, F13 : entreprise qui dépose, agent COIP qui prend en charge
ALTER TABLE besoin_recrutement
ALTER COLUMN entreprise_partenaire_id TYPE VARCHAR(100) USING entreprise_partenaire_id::text,
    ALTER COLUMN agent_coip_id            TYPE VARCHAR(100) USING agent_coip_id::text;

-- F13 : profil du diplômé proposé, agent COIP qui propose
ALTER TABLE proposition_profil
ALTER COLUMN profil_id     TYPE VARCHAR(100) USING profil_id::text,
    ALTER COLUMN agent_coip_id TYPE VARCHAR(100) USING agent_coip_id::text;