CREATE TABLE notification (
                              id              BIGSERIAL PRIMARY KEY,
                              destinataire_id VARCHAR(100) NOT NULL,
                              type            VARCHAR(50)  NOT NULL,
                              message         VARCHAR(500) NOT NULL,
                              lue             BOOLEAN      NOT NULL DEFAULT FALSE,
                              date_creation   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_notification_destinataire ON notification (destinataire_id);