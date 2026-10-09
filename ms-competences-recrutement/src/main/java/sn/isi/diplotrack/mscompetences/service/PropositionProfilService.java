package sn.isi.diplotrack.mscompetences.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.mscompetences.dto.PropositionProfilDTO;
import sn.isi.diplotrack.mscompetences.entity.BesoinRecrutement;
import sn.isi.diplotrack.mscompetences.entity.PropositionProfil;
import sn.isi.diplotrack.mscompetences.repository.BesoinRecrutementRepository;
import sn.isi.diplotrack.mscompetences.repository.PropositionProfilRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropositionProfilService {

    private final PropositionProfilRepository propositionRepository;
    private final BesoinRecrutementRepository besoinRepository;

    public PropositionProfilDTO proposer(PropositionProfilDTO dto) {
        BesoinRecrutement besoin = besoinRepository.findById(dto.getBesoinRecrutementId())
                .orElseThrow(() -> new RuntimeException("Besoin non trouvé"));
        PropositionProfil p = new PropositionProfil();
        p.setBesoinRecrutement(besoin);
        p.setProfilId(dto.getProfilId());
        p.setAgentCoipId(dto.getAgentCoipId());
        p.setStatut("PROPOSEE");
        return toDTO(propositionRepository.save(p));
    }

    public List<PropositionProfilDTO> findByBesoin(Long besoinId) {
        return propositionRepository.findByBesoinRecrutementId(besoinId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    private PropositionProfilDTO toDTO(PropositionProfil p) {
        PropositionProfilDTO dto = new PropositionProfilDTO();
        dto.setId(p.getId());
        dto.setBesoinRecrutementId(p.getBesoinRecrutement().getId());
        dto.setDateProposition(p.getDateProposition());
        dto.setStatut(p.getStatut());
        dto.setProfilId(p.getProfilId());
        dto.setAgentCoipId(p.getAgentCoipId());
        return dto;
    }
}