package sn.isi.diplotrack.mscompetences.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import sn.isi.diplotrack.mscompetences.dto.CompetenceDTO;
import sn.isi.diplotrack.mscompetences.service.CompetenceService;

import java.util.List;

/**
 * Compétences : le diplômé soumet et suit les siennes (F04), le COIP examine (F11).
 */
@RestController
@RequestMapping("/api/v1/competences")
@RequiredArgsConstructor
public class CompetenceController {

    private final CompetenceService competenceService;

    @PostMapping
    public ResponseEntity<CompetenceDTO> soumettre(@RequestBody CompetenceDTO dto, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(competenceService.soumettre(dto, jwt.getSubject()));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<CompetenceDTO>> findByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(competenceService.findByStatut(statut));
    }

    @GetMapping("/mes-competences")
    public ResponseEntity<List<CompetenceDTO>> mesCompetences(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(competenceService.findByProfilId(jwt.getSubject()));
    }

    @PutMapping("/{id}/approuver")
    public ResponseEntity<CompetenceDTO> approuver(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(competenceService.approuver(id, jwt.getSubject()));
    }

    @PutMapping("/{id}/refuser")
    public ResponseEntity<CompetenceDTO> refuser(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(competenceService.refuser(id, jwt.getSubject()));
    }
}