package sn.isi.diplotrack.diplome.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.diplome.dto.DiplomeDTO;
import sn.isi.diplotrack.diplome.entity.Diplome;
import sn.isi.diplotrack.diplome.repository.DiplomeRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiplomeService {

    private final DiplomeRepository diplomeRepository;

    public DiplomeDTO save(DiplomeDTO dto) {
        Diplome diplome = new Diplome();
        diplome.setIntitule(dto.getIntitule());
        diplome.setNiveau(dto.getNiveau());
        diplome.setAnneeObtention(dto.getAnneeObtention());
        diplome.setNumeroIdentification(dto.getNumeroIdentification());
        diplome.setQrCode(dto.getQrCode());
        diplome.setStatut("ENREGISTRE");
        diplome.setUtilisateurId(dto.getUtilisateurId());
        return toDTO(diplomeRepository.save(diplome));
    }

    public List<DiplomeDTO> findByUtilisateurId(Long utilisateurId) {
        return diplomeRepository.findByUtilisateurId(utilisateurId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DiplomeDTO findById(Long id) {
        Diplome diplome = diplomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diplôme non trouvé"));
        return toDTO(diplome);
    }

    public DiplomeDTO findByNumeroIdentification(String numeroIdentification) {
        Diplome diplome = diplomeRepository.findByNumeroIdentification(numeroIdentification)
                .orElseThrow(() -> new RuntimeException("Diplôme non trouvé"));
        return toDTO(diplome);
    }

    private DiplomeDTO toDTO(Diplome d) {
        DiplomeDTO dto = new DiplomeDTO();
        dto.setId(d.getId());
        dto.setIntitule(d.getIntitule());
        dto.setNiveau(d.getNiveau());
        dto.setAnneeObtention(d.getAnneeObtention());
        dto.setNumeroIdentification(d.getNumeroIdentification());
        dto.setQrCode(d.getQrCode());
        dto.setDateEnregistrement(d.getDateEnregistrement());
        dto.setStatut(d.getStatut());
        dto.setUtilisateurId(d.getUtilisateurId());
        return dto;
    }
}