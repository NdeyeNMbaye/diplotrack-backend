package sn.isi.diplotrack.mscompetences.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.mscompetences.dto.BesoinRecrutementDTO;
import sn.isi.diplotrack.mscompetences.entity.BesoinRecrutement;
import sn.isi.diplotrack.mscompetences.repository.BesoinRecrutementRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Besoins en recrutement des entreprises partenaires (F14), pris en charge par le COIP (F13).
 */
@Service
@RequiredArgsConstructor
public class BesoinRecrutementService {

    private final BesoinRecrutementRepository besoinRepository;

    /**
     * Dépose un besoin au nom de l'entreprise connectée (F14) : une entreprise
     * ne peut pas formuler un besoin pour une autre.
     */
    public BesoinRecrutementDTO soumettre(BesoinRecrutementDTO dto, String entrepriseId) {
        BesoinRecrutement b = new BesoinRecrutement();
        b.setPoste(dto.getPoste());
        b.setDescription(dto.getDescription());
        b.setCompetencesRequises(dto.getCompetencesRequises());
        b.setEntreprisePartenaireId(entrepriseId);
        b.setStatut("EN_ATTENTE");
        return toDTO(besoinRepository.save(b));
    }

    /** Le COIP consulte tous les besoins reçus pour sélectionner des profils (F13). */
    public List<BesoinRecrutementDTO> findAll() {
        return besoinRepository.findAll().stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    /** L'entreprise suit uniquement ses propres besoins (F14). */
    public List<BesoinRecrutementDTO> findByEntreprise(String entrepriseId) {
        return besoinRepository.findByEntreprisePartenaireId(entrepriseId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    /** Le COIP s'engage sur un besoin : il passe EN_COURS et l'agent est tracé (F13). */
    public BesoinRecrutementDTO prendreEnCharge(Long id, String agentCoipId) {
        BesoinRecrutement b = besoinRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Besoin non trouvé"));
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