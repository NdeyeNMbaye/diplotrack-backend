package sn.isi.diplotrack.utilisateur.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.utilisateur.dto.UtilisateurDTO;
import sn.isi.diplotrack.utilisateur.entity.Utilisateur;
import sn.isi.diplotrack.utilisateur.repository.UtilisateurRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/** Consultation des comptes et activation ou désactivation par l'administrateur (F17). */
@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public List<UtilisateurDTO> findAll() {
        return utilisateurRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UtilisateurDTO findById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur non trouvé"));
        return toDTO(utilisateur);
    }

    /** Retrouve la fiche de l'utilisateur connecté à partir du sub de son jeton. */
    public UtilisateurDTO findByKeycloakId(String keycloakId) {
        Utilisateur utilisateur = utilisateurRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Aucun utilisateur DiploTrack lié à ce compte Keycloak"));
        return toDTO(utilisateur);
    }

    public void updateActif(Long id, Boolean actif) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur non trouvé"));
        utilisateur.setActif(actif);
        utilisateurRepository.save(utilisateur);
    }

    private UtilisateurDTO toDTO(Utilisateur u) {
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