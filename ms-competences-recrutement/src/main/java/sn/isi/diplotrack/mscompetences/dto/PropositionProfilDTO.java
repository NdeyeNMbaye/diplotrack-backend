package sn.isi.diplotrack.mscompetences.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/** profilId est choisi par l'agent COIP ; agentCoipId est déduit du jeton. */
@Getter
@Setter
public class PropositionProfilDTO {
    private Long id;
    private Long besoinRecrutementId;
    private LocalDateTime dateProposition;
    private String statut;
    private String profilId;
    private String agentCoipId;
}