package sn.isi.diplotrack.msoffre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.isi.diplotrack.msoffre.entity.Offre;
import java.util.List;

public interface OffreRepository extends JpaRepository<Offre, Long> {
    List<Offre> findByStatut(String statut);
    List<Offre> findByType(String type);
}