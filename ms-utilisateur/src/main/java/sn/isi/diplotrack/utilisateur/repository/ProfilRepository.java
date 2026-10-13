package sn.isi.diplotrack.utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.isi.diplotrack.utilisateur.entity.Profil;
import java.util.Optional;

public interface ProfilRepository extends JpaRepository<Profil, Long> {
    Optional<Profil> findByDiplomeUtilisateurId(Long diplomeUtilisateurId);
}