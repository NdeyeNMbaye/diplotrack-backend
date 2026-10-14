package sn.isi.diplotrack.msoffre.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.msoffre.dto.CandidatureDTO;
import sn.isi.diplotrack.msoffre.entity.Candidature;
import sn.isi.diplotrack.msoffre.entity.Offre;
import sn.isi.diplotrack.msoffre.repository.CandidatureRepository;
import sn.isi.diplotrack.msoffre.repository.OffreRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Candidatures des diplômés aux offres (F06) et suivi de leur état par le COIP.
 */
@Service
@RequiredArgsConstructor
public class CandidatureService {

    private final CandidatureRepository candidatureRepository;
    private final OffreRepository offreRepository;

    /**
     * Dépose la candidature du diplômé connecté (F06). Le candidat est déduit du jeton :
     * un diplômé ne peut pas postuler au nom d'un autre.
     */
    public CandidatureDTO save(CandidatureDTO dto, String diplomeUtilisateurId) {
        Offre offre = offreRepository.findById(dto.getOffreId())
                .orElseThrow(() -> new NoSuchElementException("Offre non trouvée"));
        Candidature candidature = new Candidature();
        candidature.setOffre(offre);
        candidature.setDiplomeUtilisateurId(diplomeUtilisateurId);
        candidature.setStatut("EN_ATTENTE");
        return toDTO(candidatureRepository.save(candidature));
    }

    /** Le diplômé suit l'état de ses propres candidatures (F06). */
    public List<CandidatureDTO> findByDiplomeUtilisateurId(String diplomeUtilisateurId) {
        return candidatureRepository.findByDiplomeUtilisateurId(diplomeUtilisateurId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /** Le COIP fait évoluer l'état d'une candidature (par exemple retenue ou refusée). */
    public CandidatureDTO updateStatut(Long id, String statut) {
        Candidature candidature = candidatureRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Candidature non trouvée"));
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