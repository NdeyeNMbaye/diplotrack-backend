package sn.isi.diplotrack.diplome.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "demande_verification")
public class DemandeVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "diplome_id")
    private Diplome diplome;

    @Column(name = "nom_diplome", nullable = false)
    private String nomDiplome;

    @Column(name = "prenom_diplome", nullable = false)
    private String prenomDiplome;

    @Column(name = "numero_diplome", nullable = false)
    private String numeroDiplome;

    private String motif;

    @Column(name = "identite_demandeur", nullable = false)
    private String identiteDemandeur;

    @Column(name = "contact_demandeur", nullable = false)
    private String contactDemandeur;

    @Column(name = "date_demande")
    private LocalDateTime dateDemande = LocalDateTime.now();

    @Column(name = "date_traitement")
    private LocalDateTime dateTraitement;

    @Column(nullable = false)
    private String statut = "EN_ATTENTE";

    private String resultat;
}