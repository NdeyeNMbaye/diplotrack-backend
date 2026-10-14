package sn.isi.diplotrack.msoffre.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import sn.isi.diplotrack.msoffre.dto.CandidatureDTO;
import sn.isi.diplotrack.msoffre.service.CandidatureService;

import java.util.List;

/**
 * Candidatures. Le diplômé postule et suit les siennes (F06) ;
 * le COIP en actualise l'état.
 */
@RestController
@RequestMapping("/api/v1/candidatures")
@RequiredArgsConstructor
public class CandidatureController {

    private final CandidatureService candidatureService;

    @PostMapping
    public ResponseEntity<CandidatureDTO> save(@RequestBody CandidatureDTO dto, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(candidatureService.save(dto, jwt.getSubject()));
    }

    @GetMapping("/mes-candidatures")
    public ResponseEntity<List<CandidatureDTO>> mesCandidatures(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(candidatureService.findByDiplomeUtilisateurId(jwt.getSubject()));
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<CandidatureDTO> updateStatut(@PathVariable Long id, @RequestParam String statut) {
        return ResponseEntity.ok(candidatureService.updateStatut(id, statut));
    }
}