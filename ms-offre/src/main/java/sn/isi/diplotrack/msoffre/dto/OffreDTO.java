package sn.isi.diplotrack.msoffre.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Offre échangée avec l'API. agentCoipId est renvoyé en lecture seule :
 * à la publication, l'auteur est déduit du jeton et non de la requête.
 */
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
    private String agentCoipId;
}