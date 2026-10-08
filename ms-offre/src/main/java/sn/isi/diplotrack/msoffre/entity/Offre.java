package sn.isi.diplotrack.msoffre.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

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

    @Column(name = "agent_coip_id", nullable = false)
    private Long agentCoipId;
}