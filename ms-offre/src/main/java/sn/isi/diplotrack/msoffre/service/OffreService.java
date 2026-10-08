package sn.isi.diplotrack.msoffre.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.msoffre.dto.OffreDTO;
import sn.isi.diplotrack.msoffre.entity.Offre;
import sn.isi.diplotrack.msoffre.repository.OffreRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OffreService {

    private final OffreRepository offreRepository;

    public OffreDTO save(OffreDTO dto) {
        Offre offre = new Offre();
        offre.setTitre(dto.getTitre());
        offre.setDescription(dto.getDescription());
        offre.setDomaine(dto.getDomaine());
        offre.setLocalisation(dto.getLocalisation());
        offre.setDateExpiration(dto.getDateExpiration());
        offre.setType(dto.getType());
        offre.setStatut("ACTIVE");
        offre.setAgentCoipId(dto.getAgentCoipId());
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
        Offre offre = offreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));
        return toDTO(offre);
    }

    public OffreDTO updateStatut(Long id, String statut) {
        Offre offre = offreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));
        offre.setStatut(statut);
        return toDTO(offreRepository.save(offre));
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