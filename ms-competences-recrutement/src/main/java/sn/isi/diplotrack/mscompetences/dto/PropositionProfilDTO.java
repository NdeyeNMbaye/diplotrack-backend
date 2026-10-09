package sn.isi.diplotrack.mscompetences.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class PropositionProfilDTO {
    private Long id;
    private Long besoinRecrutementId;
    private LocalDateTime dateProposition;
    private String statut;
    private Long profilId;
    private Long agentCoipId;
}