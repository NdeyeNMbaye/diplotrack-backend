package sn.isi.diplotrack.msoffre.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "candidature")
public class Candidature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "offre_id")
    private Offre offre;

    @Column(name = "date_soumission")
    private LocalDateTime dateSoumission = LocalDateTime.now();

    @Column(nullable = false)
    private String statut = "EN_ATTENTE";

    @Column(name = "diplome_utilisateur_id", nullable = false)
    private Long diplomeUtilisateurId;
}