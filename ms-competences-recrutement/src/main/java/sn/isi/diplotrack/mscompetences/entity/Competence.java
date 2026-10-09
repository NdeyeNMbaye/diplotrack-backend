package sn.isi.diplotrack.mscompetences.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

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

    @Column(name = "profil_id", nullable = false)
    private Long profilId;

    @Column(name = "agent_coip_id")
    private Long agentCoipId;
}