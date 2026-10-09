package sn.isi.diplotrack.mscompetences.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.mscompetences.dto.CompetenceDTO;
import sn.isi.diplotrack.mscompetences.entity.Competence;
import sn.isi.diplotrack.mscompetences.repository.CompetenceRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompetenceService {

    private final CompetenceRepository competenceRepository;

    public CompetenceDTO soumettre(CompetenceDTO dto) {
        Competence c = new Competence();
        c.setIntitule(dto.getIntitule());
        c.setCategorie(dto.getCategorie());
        c.setNiveau(dto.getNiveau());
        c.setDescription(dto.getDescription());
        c.setLienRessource(dto.getLienRessource());
        c.setProfilId(dto.getProfilId());
        c.setStatut("EN_ATTENTE");
        return toDTO(competenceRepository.save(c));
    }

    public List<CompetenceDTO> findByStatut(String statut) {
        return competenceRepository.findByStatut(statut).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public List<CompetenceDTO> findByProfilId(Long profilId) {
        return competenceRepository.findByProfilId(profilId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public CompetenceDTO approuver(Long id, Long agentCoipId) {
        return decider(id, agentCoipId, "APPROUVEE");
    }

    public CompetenceDTO refuser(Long id, Long agentCoipId) {
        return decider(id, agentCoipId, "REFUSEE");
    }

    private CompetenceDTO decider(Long id, Long agentCoipId, String statut) {
        Competence c = competenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compétence non trouvée"));
        c.setStatut(statut);
        c.setAgentCoipId(agentCoipId);
        c.setDateApprobation(LocalDateTime.now());
        return toDTO(competenceRepository.save(c));
    }

    private CompetenceDTO toDTO(Competence c) {
        CompetenceDTO dto = new CompetenceDTO();
        dto.setId(c.getId());
        dto.setIntitule(c.getIntitule());
        dto.setCategorie(c.getCategorie());
        dto.setNiveau(c.getNiveau());
        dto.setDescription(c.getDescription());
        dto.setLienRessource(c.getLienRessource());
        dto.setDateSoumission(c.getDateSoumission());
        dto.setDateApprobation(c.getDateApprobation());
        dto.setStatut(c.getStatut());
        dto.setProfilId(c.getProfilId());
        dto.setAgentCoipId(c.getAgentCoipId());
        return dto;
    }
}