package sn.isi.diplotrack.diplome.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "diplome")
public class Diplome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String intitule;

    @Column(nullable = false)
    private String niveau;

    @Column(name = "annee_obtention", nullable = false)
    private Integer anneeObtention;

    @Column(name = "numero_identification", nullable = false, unique = true)
    private String numeroIdentification;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "date_enregistrement")
    private LocalDateTime dateEnregistrement = LocalDateTime.now();

    @Column(nullable = false)
    private String statut = "ENREGISTRE";

    @Column(name = "utilisateur_id", nullable = false)
    private Long utilisateurId;
}