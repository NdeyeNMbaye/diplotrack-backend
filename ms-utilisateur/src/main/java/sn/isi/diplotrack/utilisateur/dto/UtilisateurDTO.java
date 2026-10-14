package sn.isi.diplotrack.utilisateur.dto;

import lombok.Data;

import java.time.LocalDateTime;

/** Fiche d'un utilisateur DiploTrack. Aucun mot de passe : Keycloak en est l'unique détenteur. */
@Data
public class UtilisateurDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String keycloakId;
    private Boolean actif;
    private LocalDateTime dateCreation;
}