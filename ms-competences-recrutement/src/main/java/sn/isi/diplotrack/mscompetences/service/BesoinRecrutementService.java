package sn.isi.diplotrack.mscompetences.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.mscompetences.dto.BesoinRecrutementDTO;
import sn.isi.diplotrack.mscompetences.entity.BesoinRecrutement;
import sn.isi.diplotrack.mscompetences.repository.BesoinRecrutementRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BesoinRecrutementService {

    private final BesoinRecrutementRepository besoinRepository;

    public BesoinRecrutementDTO soumettre(BesoinRecrutementDTO dto) {
        BesoinRecrutement b = new BesoinRecrutement();
        b.setPoste(dto.getPoste());
        b.setDescription(dto.getDescription());
        b.setCompetencesRequises(dto.getCompetencesRequises());
        b.setEntreprisePartenaireId(dto.getEntreprisePartenaireId());
        b.setStatut("EN_ATTENTE");
        return toDTO(besoinRepository.save(b));
    }

    public List<BesoinRecrutementDTO> findAll() {
        return besoinRepository.findAll().stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public List<BesoinRecrutementDTO> findByEntreprise(Long entrepriseId) {
        return besoinRepository.findByEntreprisePartenaireId(entrepriseId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public BesoinRecrutementDTO prendreEnCharge(Long id, Long agentCoipId) {
        BesoinRecrutement b = besoinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Besoin non trouvé"));
        b.setAgentCoipId(agentCoipId);
        b.setStatut("EN_COURS");
        return toDTO(besoinRepository.save(b));
    }

    private BesoinRecrutementDTO toDTO(BesoinRecrutement b) {
        BesoinRecrutementDTO dto = new BesoinRecrutementDTO();
        dto.setId(b.getId());
        dto.setPoste(b.getPoste());
        dto.setDescription(b.getDescription());
        dto.setCompetencesRequises(b.getCompetencesRequises());
        dto.setDateSoumission(b.getDateSoumission());
        dto.setStatut(b.getStatut());
        dto.setEntreprisePartenaireId(b.getEntreprisePartenaireId());
        dto.setAgentCoipId(b.getAgentCoipId());
        return dto;
    }
}