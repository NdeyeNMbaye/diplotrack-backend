package sn.isi.diplotrack.mscompetences.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.isi.diplotrack.mscompetences.entity.PropositionProfil;
import java.util.List;

public interface PropositionProfilRepository extends JpaRepository<PropositionProfil, Long> {
    List<PropositionProfil> findByBesoinRecrutementId(Long besoinRecrutementId);
    long countByStatut(String statut);
}