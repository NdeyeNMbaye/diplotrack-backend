package sn.isi.diplotrack.mscompetences.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Profil de diplômé proposé par le COIP en réponse à un besoin (F13),
 * consulté ensuite par l'entreprise (F14).
 */
@Getter
@Setter
@Entity
@Table(name = "proposition_profil")
public class PropositionProfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "besoin_recrutement_id", nullable = false)
    private BesoinRecrutement besoinRecrutement;

    @Column(name = "date_proposition")
    private LocalDateTime dateProposition = LocalDateTime.now();

    @Column(nullable = false)
    private String statut = "PROPOSEE";

    /** Sub Keycloak du diplômé proposé, choisi par l'agent COIP. */
    @Column(name = "profil_id", nullable = false, length = 100)
    private String profilId;

    /** Sub Keycloak de l'agent COIP qui propose. */
    @Column(name = "agent_coip_id", nullable = false, length = 100)
    private String agentCoipId;
}