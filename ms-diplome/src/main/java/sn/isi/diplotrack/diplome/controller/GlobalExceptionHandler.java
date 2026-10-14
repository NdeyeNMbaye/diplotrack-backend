package sn.isi.diplotrack.diplome.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Traduit les erreurs métier en réponses HTTP claires (404 au lieu de 500).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Ressource introuvable : renvoie un 404 avec un message lisible. */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> nonTrouve(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erreur", e.getMessage()));
    }
}