package sn.isi.diplotrack.utilisateur.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProfilDTO {
    private Long id;
    private Long diplomeUtilisateurId;
    private String bio;
    private String lienExterne;
    private Boolean disponibilite;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
}