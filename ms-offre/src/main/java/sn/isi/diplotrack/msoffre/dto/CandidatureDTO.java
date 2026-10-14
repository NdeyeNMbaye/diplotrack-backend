package sn.isi.diplotrack.msoffre.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Candidature échangée avec l'API. Le client n'indique que l'offre visée (offreId) ;
 * le candidat est le diplômé connecté.
 */
@Getter
@Setter
public class CandidatureDTO {
    private Long id;
    private Long offreId;
    private LocalDateTime dateSoumission;
    private String statut;
    private String diplomeUtilisateurId;
}