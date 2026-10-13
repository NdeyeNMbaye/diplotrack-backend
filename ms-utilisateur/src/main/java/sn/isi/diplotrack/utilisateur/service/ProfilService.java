package sn.isi.diplotrack.utilisateur.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.isi.diplotrack.utilisateur.dto.ProfilDTO;
import sn.isi.diplotrack.utilisateur.entity.DiplomeUtilisateur;
import sn.isi.diplotrack.utilisateur.entity.Profil;
import sn.isi.diplotrack.utilisateur.repository.DiplomeUtilisateurRepository;
import sn.isi.diplotrack.utilisateur.repository.ProfilRepository;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfilService {

    private final ProfilRepository profilRepository;
    private final DiplomeUtilisateurRepository diplomeUtilisateurRepository;

    public ProfilDTO findByDiplomeId(Long diplomeId) {
        Profil profil = profilRepository.findByDiplomeUtilisateurId(diplomeId)
                .orElseThrow(() -> new RuntimeException("Profil non trouvé"));
        return toDTO(profil);
    }

    public ProfilDTO update(Long diplomeId, ProfilDTO dto) {
        Profil profil = profilRepository.findByDiplomeUtilisateurId(diplomeId)
                .orElseGet(() -> {
                    Profil p = new Profil();
                    DiplomeUtilisateur d = diplomeUtilisateurRepository.findById(diplomeId)
                            .orElseThrow(() -> new RuntimeException("Diplômé non trouvé"));
                    p.setDiplomeUtilisateur(d);
                    return p;
                });
        profil.setBio(dto.getBio());
        profil.setLienExterne(dto.getLienExterne());
        profil.setDisponibilite(dto.getDisponibilite());
        profil.setDateModification(LocalDateTime.now());
        return toDTO(profilRepository.save(profil));
    }

    private ProfilDTO toDTO(Profil p) {
        ProfilDTO dto = new ProfilDTO();
        dto.setId(p.getId());
        dto.setDiplomeUtilisateurId(p.getDiplomeUtilisateur().getId());
        dto.setBio(p.getBio());
        dto.setLienExterne(p.getLienExterne());
        dto.setDisponibilite(p.getDisponibilite());
        dto.setDateCreation(p.getDateCreation());
        dto.setDateModification(p.getDateModification());
        return dto;
    }
}