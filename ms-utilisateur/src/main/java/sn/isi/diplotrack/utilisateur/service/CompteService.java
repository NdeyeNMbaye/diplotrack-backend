package sn.isi.diplotrack.utilisateur.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.utilisateur.dto.CreationCompteDTO;
import sn.isi.diplotrack.utilisateur.dto.UtilisateurDTO;
import sn.isi.diplotrack.utilisateur.entity.Administrateur;
import sn.isi.diplotrack.utilisateur.entity.AgentCoip;
import sn.isi.diplotrack.utilisateur.entity.AgentScolarite;
import sn.isi.diplotrack.utilisateur.entity.DiplomeUtilisateur;
import sn.isi.diplotrack.utilisateur.entity.EntreprisePartenaire;
import sn.isi.diplotrack.utilisateur.entity.Utilisateur;
import sn.isi.diplotrack.utilisateur.repository.UtilisateurRepository;

/**
 * Ouverture des comptes par l'administrateur (F17) : diplômés, agents de scolarité,
 * agents COIP, entreprises partenaires et administrateurs.
 *
 * Il n'y a pas d'inscription libre. L'identité (Keycloak) et les données métier
 * (db_utilisateur) sont créées ensemble et liées par le sub ; si la seconde création
 * échoue, la première est annulée pour ne laisser aucun compte orphelin.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CompteService {

    private final KeycloakAdminService keycloak;
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurDTO creer(CreationCompteDTO dto) {
        if (utilisateurRepository.existsByEmail(dto.getEmail())) {
            throw new CompteExistantException("Un compte existe déjà pour " + dto.getEmail());
        }

        // Les champs propres au type de compte sont contrôlés avant de toucher à Keycloak
        Utilisateur compte = construire(dto);

        String keycloakId = keycloak.creerUtilisateur(
                dto.getEmail(), dto.getPrenom(), dto.getNom(), dto.getRole(), dto.getMotDePasseInitial());

        try {
            compte.setKeycloakId(keycloakId);
            return versDTO(utilisateurRepository.save(compte));
        } catch (RuntimeException e) {
            log.error("Enregistrement en base impossible, annulation du compte Keycloak {}", keycloakId, e);
            keycloak.supprimer(keycloakId);
            throw e;
        }
    }

    /** Crée l'entité correspondant au rôle choisi, avec ses champs obligatoires. */
    private Utilisateur construire(CreationCompteDTO dto) {
        Utilisateur compte = switch (dto.getRole()) {
            case "DIPLOME" -> {
                DiplomeUtilisateur d = new DiplomeUtilisateur();
                d.setMatricule(exiger(dto.getMatricule(), "matricule"));
                d.setPromotion(dto.getPromotion());
                d.setFiliere(dto.getFiliere());
                yield d;
            }
            case "AGENT_SCOLARITE" -> {
                AgentScolarite a = new AgentScolarite();
                a.setMatricule(exiger(dto.getMatricule(), "matricule"));
                yield a;
            }
            case "AGENT_COIP" -> {
                AgentCoip a = new AgentCoip();
                a.setMatricule(exiger(dto.getMatricule(), "matricule"));
                yield a;
            }
            case "ADMINISTRATEUR" -> {
                Administrateur a = new Administrateur();
                a.setMatricule(exiger(dto.getMatricule(), "matricule"));
                yield a;
            }
            default -> {
                EntreprisePartenaire e = new EntreprisePartenaire();
                e.setNomEntreprise(exiger(dto.getNomEntreprise(), "nomEntreprise"));
                e.setSecteur(dto.getSecteur());
                e.setContact(dto.getContact());
                yield e;
            }
        };
        compte.setNom(dto.getNom());
        compte.setPrenom(dto.getPrenom());
        compte.setEmail(dto.getEmail());
        return compte;
    }

    private String exiger(String valeur, String champ) {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException("Le champ " + champ + " est obligatoire pour ce rôle");
        }
        return valeur;
    }

    private UtilisateurDTO versDTO(Utilisateur u) {
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setId(u.getId());
        dto.setNom(u.getNom());
        dto.setPrenom(u.getPrenom());
        dto.setEmail(u.getEmail());
        dto.setKeycloakId(u.getKeycloakId());
        dto.setActif(u.getActif());
        dto.setDateCreation(u.getDateCreation());
        return dto;
    }
}