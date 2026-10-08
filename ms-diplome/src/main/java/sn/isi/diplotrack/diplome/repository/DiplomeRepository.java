package sn.isi.diplotrack.diplome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.isi.diplotrack.diplome.entity.Diplome;
import java.util.List;
import java.util.Optional;

public interface DiplomeRepository extends JpaRepository<Diplome, Long> {
    Optional<Diplome> findByNumeroIdentification(String numeroIdentification);
    List<Diplome> findByUtilisateurId(Long utilisateurId);
}