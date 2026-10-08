package sn.isi.diplotrack.diplome.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.diplome.dto.DemandeVerificationDTO;
import sn.isi.diplotrack.diplome.entity.DemandeVerification;
import sn.isi.diplotrack.diplome.entity.Diplome;
import sn.isi.diplotrack.diplome.repository.DemandeVerificationRepository;
import sn.isi.diplotrack.diplome.repository.DiplomeRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DemandeVerificationService {

    private final DemandeVerificationRepository demandeVerificationRepository;
    private final DiplomeRepository diplomeRepository;

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

    public List<DemandeVerificationDTO> findAll() {
        return demandeVerificationRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<DemandeVerificationDTO> findByStatut(String statut) {
        return demandeVerificationRepository.findByStatut(statut)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DemandeVerificationDTO traiter(Long id, String resultat) {
        DemandeVerification demande = demandeVerificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));
        demande.setResultat(resultat);
        demande.setStatut("TRAITE");
        demande.setDateTraitement(LocalDateTime.now());
        return toDTO(demandeVerificationRepository.save(demande));
    }

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