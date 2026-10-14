package sn.isi.diplotrack.mscompetences.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Compétence soumise par un diplômé (F04) et examinée par le COIP (F11).
 * Elle n'apparaît dans le profil certifié qu'après approbation.
 */
@Getter
@Setter
@Entity
@Table(name = "competence")
public class Competence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String intitule;

    private String categorie;
    private String niveau;
    private String description;

    @Column(name = "lien_ressource")
    private String lienRessource;

    @Column(name = "date_soumission")
    private LocalDateTime dateSoumission = LocalDateTime.now();

    @Column(name = "date_approbation")
    private LocalDateTime dateApprobation;

    @Column(nullable = false)
    private String statut = "EN_ATTENTE";

    /** Sub Keycloak du diplômé qui soumet (référence logique vers Service Utilisateur). */
    @Column(name = "profil_id", nullable = false, length = 100)
    private String profilId;

    /** Sub Keycloak de l'agent COIP qui a décidé, renseigné à l'approbation ou au refus. */
    @Column(name = "agent_coip_id", length = 100)
    private String agentCoipId;
}