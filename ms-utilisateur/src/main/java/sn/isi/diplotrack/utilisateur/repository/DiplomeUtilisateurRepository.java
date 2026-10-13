package sn.isi.diplotrack.utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.isi.diplotrack.utilisateur.entity.DiplomeUtilisateur;
import java.util.Optional;

public interface DiplomeUtilisateurRepository extends JpaRepository<DiplomeUtilisateur, Long> {
    Optional<DiplomeUtilisateur> findByMatricule(String matricule);
}