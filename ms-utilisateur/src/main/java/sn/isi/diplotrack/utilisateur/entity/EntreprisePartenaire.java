package sn.isi.diplotrack.utilisateur.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "entreprise_partenaire")
public class EntreprisePartenaire extends Utilisateur {

    @Column(name = "nom_entreprise", nullable = false)
    private String nomEntreprise;

    private String secteur;

    private String contact;
}