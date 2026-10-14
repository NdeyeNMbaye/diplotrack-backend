package sn.isi.diplotrack.diplome.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Données échangées pour une demande de vérification de diplôme.
 * L'adresse de contact doit être un email valide : c'est là que part la réponse.
 */
@Data
public class DemandeVerificationDTO {

    private Long id;
    private Long diplomeId;

    @NotBlank
    private String nomDiplome;

    @NotBlank
    private String prenomDiplome;

    @NotBlank
    private String numeroDiplome;

    private String motif;

    @NotBlank
    private String identiteDemandeur;

    @NotBlank
    @Email
    private String contactDemandeur;


    private LocalDateTime dateDemande;
    private LocalDateTime dateTraitement;
    private String statut;
    private String resultat;
}