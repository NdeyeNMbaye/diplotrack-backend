package sn.isi.diplotrack.diplome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.isi.diplotrack.diplome.entity.DemandeVerification;
import java.util.List;

public interface DemandeVerificationRepository extends JpaRepository<DemandeVerification, Long> {
    List<DemandeVerification> findByStatut(String statut);
}