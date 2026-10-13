package sn.isi.diplotrack.utilisateur.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "diplome_utilisateur")
public class DiplomeUtilisateur extends Utilisateur {

    @Column(nullable = false)
    private String matricule;

    private String promotion;

    private String filiere;
}