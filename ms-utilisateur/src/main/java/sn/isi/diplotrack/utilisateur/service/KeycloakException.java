package sn.isi.diplotrack.utilisateur.service;

/** Keycloak est injoignable ou a refusé l'opération : la création du compte est annulée. */
public class KeycloakException extends RuntimeException {

    public KeycloakException(String message) {
        super(message);
    }

    public KeycloakException(String message, Throwable cause) {
        super(message, cause);
    }
}