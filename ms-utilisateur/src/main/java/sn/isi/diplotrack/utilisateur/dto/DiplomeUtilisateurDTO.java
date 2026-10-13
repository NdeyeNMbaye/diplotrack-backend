package sn.isi.diplotrack.utilisateur.dto;

import lombok.Data;

@Data
public class DiplomeUtilisateurDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String matricule;
    private String promotion;
    private String filiere;
    private Boolean actif;
}