package sn.isi.diplotrack.mscompetences.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.isi.diplotrack.mscompetences.entity.BesoinRecrutement;
import java.util.List;

public interface BesoinRecrutementRepository extends JpaRepository<BesoinRecrutement, Long> {
    List<BesoinRecrutement> findByEntreprisePartenaireId(Long entreprisePartenaireId);
    List<BesoinRecrutement> findByStatut(String statut);
    long countByStatut(String statut);
}