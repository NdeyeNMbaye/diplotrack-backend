package sn.isi.diplotrack.utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.isi.diplotrack.utilisateur.entity.Utilisateur;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
    Boolean existsByEmail(String email);
    Optional<Utilisateur> findByKeycloakId(String keycloakId);
}