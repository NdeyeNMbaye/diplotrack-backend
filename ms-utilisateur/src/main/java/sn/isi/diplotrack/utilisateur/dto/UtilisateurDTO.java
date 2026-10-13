package sn.isi.diplotrack.utilisateur.dto;

import lombok.Data;
import java.time.LocalDateTime;

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