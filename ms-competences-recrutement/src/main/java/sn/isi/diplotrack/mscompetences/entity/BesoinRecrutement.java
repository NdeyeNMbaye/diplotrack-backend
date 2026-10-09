package sn.isi.diplotrack.mscompetences.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

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

    @Column(name = "entreprise_partenaire_id", nullable = false)
    private Long entreprisePartenaireId;

    @Column(name = "agent_coip_id")
    private Long agentCoipId;
}