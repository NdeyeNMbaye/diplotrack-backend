package sn.isi.diplotrack.mscompetences.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.isi.diplotrack.mscompetences.entity.Competence;
import java.util.List;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    List<Competence> findByStatut(String statut);
    List<Competence> findByProfilId(Long profilId);
    long countByStatut(String statut);
}