package sn.isi.diplotrack.utilisateur.dto;

import lombok.Data;

@Data
public class EntreprisePartenaireDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String nomEntreprise;
    private String secteur;
    private String contact;
    private Boolean actif;
}