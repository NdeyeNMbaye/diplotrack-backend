-- Les mots de passe sont geres par Keycloak
ALTER TABLE utilisateur DROP COLUMN IF EXISTS mot_de_passe;

-- Lien logique avec l'identite Keycloak (le sub du jeton)
ALTER TABLE utilisateur ADD COLUMN IF NOT EXISTS keycloak_id VARCHAR(100) UNIQUE;

-- Database-per-Service : les notifications vivent dans db_notification
DROP TABLE IF EXISTS notification;