package sn.isi.diplotrack.mscompetences.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.mscompetences.dto.CompetenceDTO;
import sn.isi.diplotrack.mscompetences.entity.Competence;
import sn.isi.diplotrack.mscompetences.repository.CompetenceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Compétences soumises par les diplômés (F04) et examinées par le COIP (F11).
 */
@Service
@RequiredArgsConstructor
public class CompetenceService {

    private final CompetenceRepository competenceRepository;

    /**
     * Soumet une compétence à l'approbation du COIP (F04). Le propriétaire est le diplômé
     * connecté : personne ne peut déclarer une compétence sur le profil d'un autre.
     */
    public CompetenceDTO soumettre(CompetenceDTO dto, String profilId) {
        Competence c = new Competence();
        c.setIntitule(dto.getIntitule());
        c.setCategorie(dto.getCategorie());
        c.setNiveau(dto.getNiveau());
        c.setDescription(dto.getDescription());
        c.setLienRessource(dto.getLienRessource());
        c.setProfilId(profilId);
        c.setStatut("EN_ATTENTE");
        return toDTO(competenceRepository.save(c));
    }

    /** File d'examen du COIP, par exemple les compétences EN_ATTENTE (F11). */
    public List<CompetenceDTO> findByStatut(String statut) {
        return competenceRepository.findByStatut(statut).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    /** Le diplômé suit l'état de ses propres compétences (F04). */
    public List<CompetenceDTO> findByProfilId(String profilId) {
        return competenceRepository.findByProfilId(profilId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    /** Le COIP approuve : la compétence devient visible dans le profil certifié (F11). */
    public CompetenceDTO approuver(Long id, String agentCoipId) {
        return decider(id, agentCoipId, "APPROUVEE");
    }

    /** Le COIP refuse la compétence soumise (F11). */
    public CompetenceDTO refuser(Long id, String agentCoipId) {
        return decider(id, agentCoipId, "REFUSEE");
    }

    /** La décision est tracée : l'agent COIP connecté et la date sont enregistrés. */
    private CompetenceDTO decider(Long id, String agentCoipId, String statut) {
        Competence c = competenceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Compétence non trouvée"));
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