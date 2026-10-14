package sn.isi.diplotrack.msoffre.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Candidature d'un diplômé à une offre (F06). Le suivi de son état est assuré par le COIP.
 */
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

    /** Identifiant Keycloak (sub) du diplômé candidat : référence logique vers Service Utilisateur. */
    @Column(name = "diplome_utilisateur_id", nullable = false, length = 100)
    private String diplomeUtilisateurId;
}