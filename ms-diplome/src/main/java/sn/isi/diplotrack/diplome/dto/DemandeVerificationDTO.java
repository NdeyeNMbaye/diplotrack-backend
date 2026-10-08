package sn.isi.diplotrack.diplome.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DemandeVerificationDTO {
    private Long id;
    private Long diplomeId;
    private String nomDiplome;
    private String prenomDiplome;
    private String numeroDiplome;
    private String motif;
    private String identiteDemandeur;
    private String contactDemandeur;
    private LocalDateTime dateDemande;
    private LocalDateTime dateTraitement;
    private String statut;
    private String resultat;
}