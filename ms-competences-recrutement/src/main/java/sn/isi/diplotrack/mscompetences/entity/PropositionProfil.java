package sn.isi.diplotrack.mscompetences.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

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

    @Column(name = "profil_id", nullable = false)
    private Long profilId;

    @Column(name = "agent_coip_id", nullable = false)
    private Long agentCoipId;
}