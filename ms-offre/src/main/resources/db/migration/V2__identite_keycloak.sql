-- Identité des utilisateurs : le sub Keycloak remplace les identifiants numériques
-- saisis par le client (Database-per-Service : aucune clé étrangère vers db_utilisateur).
ALTER TABLE offre
ALTER COLUMN agent_coip_id TYPE VARCHAR(100) USING agent_coip_id::text;

ALTER TABLE candidature
ALTER COLUMN diplome_utilisateur_id TYPE VARCHAR(100) USING diplome_utilisateur_id::text;