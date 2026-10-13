package sn.isi.diplotrack.utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.isi.diplotrack.utilisateur.entity.EntreprisePartenaire;

public interface EntreprisePartenaireRepository extends JpaRepository<EntreprisePartenaire, Long> {
}