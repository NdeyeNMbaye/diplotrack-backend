package sn.isi.diplotrack.msoffre.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CandidatureDTO {
    private Long id;
    private Long offreId;
    private LocalDateTime dateSoumission;
    private String statut;
    private Long diplomeUtilisateurId;
}