package sn.isi.diplotrack.mscompetences.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Besoin en recrutement déposé par une entreprise partenaire (F14),
 * pris en charge par un agent COIP qui lui propose des profils (F13).
 */
@Getter
@Setter
@Entity
@Table(name = "besoin_recrutement")
public class BesoinRecrutement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String poste;

    private String description;

    @Column(name = "competences_requises")
    private String competencesRequises;

    @Column(name = "date_soumission")
    private LocalDateTime dateSoumission = LocalDateTime.now();

    @Column(nullable = false)
    private String statut = "EN_ATTENTE";

    /** Sub Keycloak de l'entreprise partenaire demandeuse. */
    @Column(name = "entreprise_partenaire_id", nullable = false, length = 100)
    private String entreprisePartenaireId;

    /** Sub Keycloak de l'agent COIP qui a pris le besoin en charge. */
    @Column(name = "agent_coip_id", length = 100)
    private String agentCoipId;
}