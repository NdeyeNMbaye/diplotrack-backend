package sn.isi.diplotrack.msoffre.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.msoffre.dto.CandidatureDTO;
import sn.isi.diplotrack.msoffre.entity.Candidature;
import sn.isi.diplotrack.msoffre.entity.Offre;
import sn.isi.diplotrack.msoffre.repository.CandidatureRepository;
import sn.isi.diplotrack.msoffre.repository.OffreRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidatureService {

    private final CandidatureRepository candidatureRepository;
    private final OffreRepository offreRepository;

    public CandidatureDTO save(CandidatureDTO dto) {
        Offre offre = offreRepository.findById(dto.getOffreId())
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));
        Candidature candidature = new Candidature();
        candidature.setOffre(offre);
        candidature.setDiplomeUtilisateurId(dto.getDiplomeUtilisateurId());
        candidature.setStatut("EN_ATTENTE");
        return toDTO(candidatureRepository.save(candidature));
    }

    public List<CandidatureDTO> findByDiplomeUtilisateurId(Long diplomeUtilisateurId) {
        return candidatureRepository.findByDiplomeUtilisateurId(diplomeUtilisateurId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CandidatureDTO updateStatut(Long id, String statut) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidature non trouvée"));
        candidature.setStatut(statut);
        return toDTO(candidatureRepository.save(candidature));
    }

    private CandidatureDTO toDTO(Candidature c) {
        CandidatureDTO dto = new CandidatureDTO();
        dto.setId(c.getId());
        dto.setOffreId(c.getOffre().getId());
        dto.setDateSoumission(c.getDateSoumission());
        dto.setStatut(c.getStatut());
        dto.setDiplomeUtilisateurId(c.getDiplomeUtilisateurId());
        return dto;
    }
}