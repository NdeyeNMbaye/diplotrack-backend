package sn.isi.diplotrack.mscompetences.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/** entreprisePartenaireId et agentCoipId sont en lecture seule : déduits du jeton. */
@Getter
@Setter
public class BesoinRecrutementDTO {
    private Long id;
    private String poste;
    private String description;
    private String competencesRequises;
    private LocalDateTime dateSoumission;
    private String statut;
    private String entreprisePartenaireId;
    private String agentCoipId;
}