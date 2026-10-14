package sn.isi.diplotrack.diplome.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.diplome.dto.DemandeVerificationDTO;
import sn.isi.diplotrack.diplome.entity.DemandeVerification;
import sn.isi.diplotrack.diplome.repository.DemandeVerificationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Logique métier des demandes de vérification de diplôme (F08, F16).
 */
@Service
@RequiredArgsConstructor
public class DemandeVerificationService {

    private final DemandeVerificationRepository demandeVerificationRepository;
    private final EmailService emailService;

    /**
     * Enregistre une nouvelle demande sur la plateforme (statut EN_ATTENTE).
     * Aucun email n'est envoyé : la demande attend son traitement par la scolarité.
     */
    public DemandeVerificationDTO save(DemandeVerificationDTO dto) {
        DemandeVerification demande = new DemandeVerification();
        demande.setNomDiplome(dto.getNomDiplome());
        demande.setPrenomDiplome(dto.getPrenomDiplome());
        demande.setNumeroDiplome(dto.getNumeroDiplome());
        demande.setMotif(dto.getMotif());
        demande.setIdentiteDemandeur(dto.getIdentiteDemandeur());
        demande.setContactDemandeur(dto.getContactDemandeur());
        demande.setStatut("EN_ATTENTE");
        return toDTO(demandeVerificationRepository.save(demande));
    }

    /** Liste toutes les demandes (file de traitement de la scolarité). */
    public List<DemandeVerificationDTO> findAll() {
        return demandeVerificationRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /** Liste les demandes d'un statut donné (par exemple EN_ATTENTE). */
    public List<DemandeVerificationDTO> findByStatut(String statut) {
        return demandeVerificationRepository.findByStatut(statut)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Traite une demande : enregistre le résultat puis envoie la réponse par email au demandeur.
     *
     * @throws NoSuchElementException si la demande n'existe pas (renvoyé en 404)
     */
    public DemandeVerificationDTO traiter(Long id, String resultat) {
        DemandeVerification demande = demandeVerificationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Demande non trouvée"));

        demande.setResultat(resultat);
        demande.setStatut("TRAITE");
        demande.setDateTraitement(LocalDateTime.now());
        DemandeVerification traitee = demandeVerificationRepository.save(demande);

        // Seul déclencheur d'email du processus
        emailService.envoyerReponse(traitee);
        return toDTO(traitee);
    }

    /** Convertit l'entité en DTO pour la réponse de l'API. */
    private DemandeVerificationDTO toDTO(DemandeVerification d) {
        DemandeVerificationDTO dto = new DemandeVerificationDTO();
        dto.setId(d.getId());
        dto.setNomDiplome(d.getNomDiplome());
        dto.setPrenomDiplome(d.getPrenomDiplome());
        dto.setNumeroDiplome(d.getNumeroDiplome());
        dto.setMotif(d.getMotif());
        dto.setIdentiteDemandeur(d.getIdentiteDemandeur());
        dto.setContactDemandeur(d.getContactDemandeur());
        dto.setDateDemande(d.getDateDemande());
        dto.setDateTraitement(d.getDateTraitement());
        dto.setStatut(d.getStatut());
        dto.setResultat(d.getResultat());
        if (d.getDiplome() != null) {
            dto.setDiplomeId(d.getDiplome().getId());
        }
        return dto;
    }
}