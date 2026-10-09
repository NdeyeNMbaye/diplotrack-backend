package sn.isi.diplotrack.mscompetences.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CompetenceDTO {
    private Long id;
    private String intitule;
    private String categorie;
    private String niveau;
    private String description;
    private String lienRessource;
    private LocalDateTime dateSoumission;
    private LocalDateTime dateApprobation;
    private String statut;
    private Long profilId;
    private Long agentCoipId;
}