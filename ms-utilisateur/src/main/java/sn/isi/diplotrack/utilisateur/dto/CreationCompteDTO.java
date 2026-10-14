package sn.isi.diplotrack.utilisateur.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Saisie de l'administrateur pour ouvrir un compte (F17).
 * L'email institutionnel sert d'identifiant de connexion. Le mot de passe initial est
 * communiqué à l'utilisateur par l'administrateur ; Keycloak impose son changement
 * dès la première connexion.
 */
@Data
public class CreationCompteDTO {

    @NotBlank
    @Pattern(regexp = "DIPLOME|AGENT_SCOLARITE|AGENT_COIP|ENTREPRISE_PARTENAIRE|ADMINISTRATEUR",
            message = "rôle inconnu")
    private String role;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "8 caractères minimum")
    private String motDePasseInitial;

    // Diplômé, agents et administrateur
    private String matricule;

    // Diplômé uniquement
    private String promotion;
    private String filiere;

    // Entreprise partenaire uniquement
    private String nomEntreprise;
    private String secteur;
    private String contact;
}