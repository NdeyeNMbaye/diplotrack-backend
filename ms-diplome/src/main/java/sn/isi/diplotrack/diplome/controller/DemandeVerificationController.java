package sn.isi.diplotrack.diplome.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.isi.diplotrack.diplome.dto.DemandeVerificationDTO;
import sn.isi.diplotrack.diplome.service.DemandeVerificationService;

import java.util.List;

/**
 * API des demandes de vérification de diplôme.
 * Le dépôt (POST) est public (F16) ; la consultation et le traitement sont réservés
 * à l'agent de scolarité (F08), conformément au SecurityConfig.
 */
@RestController
@RequestMapping("/api/v1/demandes-verification")
@RequiredArgsConstructor
public class DemandeVerificationController {

    private final DemandeVerificationService demandeVerificationService;

    /** Dépose une demande. @Valid renvoie un 400 si un champ est invalide (email compris). */
    @PostMapping
    public ResponseEntity<DemandeVerificationDTO> save(@Valid @RequestBody DemandeVerificationDTO dto) {
        return ResponseEntity.ok(demandeVerificationService.save(dto));
    }

    /** Liste toutes les demandes. */
    @GetMapping
    public ResponseEntity<List<DemandeVerificationDTO>> findAll() {
        return ResponseEntity.ok(demandeVerificationService.findAll());
    }

    /** Liste les demandes d'un statut donné. */
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<DemandeVerificationDTO>> findByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(demandeVerificationService.findByStatut(statut));
    }

    /** Traite une demande et déclenche l'email de réponse au demandeur. */
    @PutMapping("/{id}/traiter")
    public ResponseEntity<DemandeVerificationDTO> traiter(
            @PathVariable Long id,
            @RequestParam String resultat) {
        return ResponseEntity.ok(demandeVerificationService.traiter(id, resultat));
    }
}