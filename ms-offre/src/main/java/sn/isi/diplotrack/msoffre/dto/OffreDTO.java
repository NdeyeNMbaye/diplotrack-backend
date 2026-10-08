package sn.isi.diplotrack.msoffre.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class OffreDTO {
    private Long id;
    private String titre;
    private String description;
    private String domaine;
    private String localisation;
    private LocalDateTime datePublication;
    private LocalDateTime dateExpiration;
    private String type;
    private String statut;
    private Long agentCoipId;
}