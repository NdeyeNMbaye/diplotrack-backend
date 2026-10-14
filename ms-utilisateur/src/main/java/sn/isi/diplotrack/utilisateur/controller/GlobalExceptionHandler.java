package sn.isi.diplotrack.utilisateur.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sn.isi.diplotrack.utilisateur.service.CompteExistantException;
import sn.isi.diplotrack.utilisateur.service.KeycloakException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/** Traduit les situations métier en réponses HTTP lisibles. */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Utilisateur inconnu, ou compte Keycloak sans fiche DiploTrack : 404. */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> nonTrouve(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erreur", e.getMessage()));
    }

    /** Email déjà utilisé : 409, rien n'a été créé. */
    @ExceptionHandler(CompteExistantException.class)
    public ResponseEntity<Map<String, String>> dejaExistant(CompteExistantException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erreur", e.getMessage()));
    }

    /** Keycloak indisponible ou opération refusée : 502, la création est annulée. */
    @ExceptionHandler(KeycloakException.class)
    public ResponseEntity<Map<String, String>> keycloak(KeycloakException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Map.of("erreur", e.getMessage()));
    }

    /** Champ propre au rôle manquant (matricule, nom d'entreprise...) : 400. */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> champManquant(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("erreur", e.getMessage()));
    }

    /** Champs invalides (email mal formé, mot de passe trop court...) : 400 avec le détail par champ. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> invalide(MethodArgumentNotValidException e) {
        Map<String, String> erreurs = new HashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(f -> erreurs.put(f.getField(), f.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurs);
    }
}