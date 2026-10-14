package sn.isi.diplotrack.msoffre.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.msoffre.dto.OffreDTO;
import sn.isi.diplotrack.msoffre.entity.Offre;
import sn.isi.diplotrack.msoffre.repository.OffreRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Publication et consultation des offres de stage et d'emploi (F10, F05).
 */
@Service
@RequiredArgsConstructor
public class OffreService {

    private final OffreRepository offreRepository;

    /**
     * Publie une offre (F10). L'auteur est l'agent COIP connecté : on ne lit jamais
     * son identité dans le body, pour qu'aucun agent ne publie au nom d'un autre.
     */
    public OffreDTO save(OffreDTO dto, String agentCoipId) {
        Offre offre = new Offre();
        offre.setTitre(dto.getTitre());
        offre.setDescription(dto.getDescription());
        offre.setDomaine(dto.getDomaine());
        offre.setLocalisation(dto.getLocalisation());
        offre.setDateExpiration(dto.getDateExpiration());
        offre.setType(dto.getType());
        offre.setStatut("ACTIVE");
        offre.setAgentCoipId(agentCoipId);
        return toDTO(offreRepository.save(offre));
    }

    public List<OffreDTO> findAll() {
        return offreRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<OffreDTO> findByStatut(String statut) {
        return offreRepository.findByStatut(statut)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public OffreDTO findById(Long id) {
        return toDTO(trouver(id));
    }

    /** Actualise l'état d'une offre (F10) : par exemple la clôturer une fois pourvue. */
    public OffreDTO updateStatut(Long id, String statut) {
        Offre offre = trouver(id);
        offre.setStatut(statut);
        return toDTO(offreRepository.save(offre));
    }

    private Offre trouver(Long id) {
        return offreRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Offre non trouvée"));
    }

    private OffreDTO toDTO(Offre o) {
        OffreDTO dto = new OffreDTO();
        dto.setId(o.getId());
        dto.setTitre(o.getTitre());
        dto.setDescription(o.getDescription());
        dto.setDomaine(o.getDomaine());
        dto.setLocalisation(o.getLocalisation());
        dto.setDatePublication(o.getDatePublication());
        dto.setDateExpiration(o.getDateExpiration());
        dto.setType(o.getType());
        dto.setStatut(o.getStatut());
        dto.setAgentCoipId(o.getAgentCoipId());
        return dto;
    }
}