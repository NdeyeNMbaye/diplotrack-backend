package sn.isi.diplotrack.msoffre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.isi.diplotrack.msoffre.entity.Candidature;
import java.util.List;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
    List<Candidature> findByDiplomeUtilisateurId(Long diplomeUtilisateurId);
    List<Candidature> findByOffreId(Long offreId);
}