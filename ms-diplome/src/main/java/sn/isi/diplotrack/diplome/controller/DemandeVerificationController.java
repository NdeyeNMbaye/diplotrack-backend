package sn.isi.diplotrack.diplome.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.isi.diplotrack.diplome.dto.DemandeVerificationDTO;
import sn.isi.diplotrack.diplome.service.DemandeVerificationService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/demandes-verification")
@RequiredArgsConstructor
public class DemandeVerificationController {

    private final DemandeVerificationService demandeVerificationService;

    @PostMapping
    public ResponseEntity<DemandeVerificationDTO> save(@RequestBody DemandeVerificationDTO dto) {
        return ResponseEntity.ok(demandeVerificationService.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<DemandeVerificationDTO>> findAll() {
        return ResponseEntity.ok(demandeVerificationService.findAll());
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<DemandeVerificationDTO>> findByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(demandeVerificationService.findByStatut(statut));
    }

    @PutMapping("/{id}/traiter")
    public ResponseEntity<DemandeVerificationDTO> traiter(
            @PathVariable Long id,
            @RequestParam String resultat) {
        return ResponseEntity.ok(demandeVerificationService.traiter(id, resultat));
    }
}