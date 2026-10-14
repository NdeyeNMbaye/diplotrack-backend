package sn.isi.diplotrack.msoffre.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Offre de stage ou d'emploi publiée par le COIP (F10), consultée par les diplômés (F05).
 */
@Data
@Entity
@Table(name = "offre")
public class Offre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    private String description;
    private String domaine;
    private String localisation;

    @Column(name = "date_publication")
    private LocalDateTime datePublication = LocalDateTime.now();

    @Column(name = "date_expiration")
    private LocalDateTime dateExpiration;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String statut = "ACTIVE";

    /** Identifiant Keycloak (sub) de l'agent COIP auteur : référence logique vers Service Utilisateur. */
    @Column(name = "agent_coip_id", nullable = false, length = 100)
    private String agentCoipId;
}