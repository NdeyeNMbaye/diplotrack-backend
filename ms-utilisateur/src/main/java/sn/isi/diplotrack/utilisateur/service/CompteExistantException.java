package sn.isi.diplotrack.utilisateur.service;

/** Un compte existe déjà avec cet email : jamais deux identités pour une même adresse. */
public class CompteExistantException extends RuntimeException {

    public CompteExistantException(String message) {
        super(message);
    }
}