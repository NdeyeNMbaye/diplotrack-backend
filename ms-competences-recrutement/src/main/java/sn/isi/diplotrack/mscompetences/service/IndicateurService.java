package sn.isi.diplotrack.mscompetences.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.mscompetences.dto.IndicateursInsertionDTO;
import sn.isi.diplotrack.mscompetences.repository.BesoinRecrutementRepository;
import sn.isi.diplotrack.mscompetences.repository.CompetenceRepository;
import sn.isi.diplotrack.mscompetences.repository.PropositionProfilRepository;

@Service
@RequiredArgsConstructor
public class IndicateurService {

    private final CompetenceRepository competenceRepository;
    private final BesoinRecrutementRepository besoinRepository;
    private final PropositionProfilRepository propositionRepository;

    public IndicateursInsertionDTO calculer() {
        IndicateursInsertionDTO dto = new IndicateursInsertionDTO();
        dto.setCompetencesEnAttente(competenceRepository.countByStatut("EN_ATTENTE"));
        dto.setCompetencesApprouvees(competenceRepository.countByStatut("APPROUVEE"));
        dto.setCompetencesRefusees(competenceRepository.countByStatut("REFUSEE"));
        dto.setBesoinsRecus(besoinRepository.count());
        dto.setBesoinsEnCours(besoinRepository.countByStatut("EN_COURS"));
        dto.setPropositionsEnvoyees(propositionRepository.count());
        return dto;
    }
}