package sn.isi.diplotrack.msoffre.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.isi.diplotrack.msoffre.dto.CandidatureDTO;
import sn.isi.diplotrack.msoffre.service.CandidatureService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/candidatures")
@RequiredArgsConstructor
public class CandidatureController {

    private final CandidatureService candidatureService;

    @PostMapping
    public ResponseEntity<CandidatureDTO> save(@RequestBody CandidatureDTO dto) {
        return ResponseEntity.ok(candidatureService.save(dto));
    }

    @GetMapping("/diplome/{diplomeUtilisateurId}")
    public ResponseEntity<List<CandidatureDTO>> findByDiplomeUtilisateurId(
            @PathVariable Long diplomeUtilisateurId) {
        return ResponseEntity.ok(candidatureService.findByDiplomeUtilisateurId(diplomeUtilisateurId));
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<CandidatureDTO> updateStatut(
            @PathVariable Long id,
            @RequestParam String statut) {
        return ResponseEntity.ok(candidatureService.updateStatut(id, statut));
    }
}