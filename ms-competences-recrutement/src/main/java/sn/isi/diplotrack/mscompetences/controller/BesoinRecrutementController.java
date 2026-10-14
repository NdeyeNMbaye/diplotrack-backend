package sn.isi.diplotrack.mscompetences.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import sn.isi.diplotrack.mscompetences.dto.BesoinRecrutementDTO;
import sn.isi.diplotrack.mscompetences.service.BesoinRecrutementService;

import java.util.List;

/**
 * Besoins en recrutement : l'entreprise partenaire dépose et suit les siens (F14),
 * le COIP les consulte et les prend en charge (F13).
 */
@RestController
@RequestMapping("/api/v1/besoins-recrutement")
@RequiredArgsConstructor
public class BesoinRecrutementController {

    private final BesoinRecrutementService besoinService;

    @PostMapping
    public ResponseEntity<BesoinRecrutementDTO> soumettre(@RequestBody BesoinRecrutementDTO dto,
                                                          @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(besoinService.soumettre(dto, jwt.getSubject()));
    }

    @GetMapping
    public ResponseEntity<List<BesoinRecrutementDTO>> findAll() {
        return ResponseEntity.ok(besoinService.findAll());
    }

    @GetMapping("/mes-besoins")
    public ResponseEntity<List<BesoinRecrutementDTO>> mesBesoins(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(besoinService.findByEntreprise(jwt.getSubject()));
    }

    @PutMapping("/{id}/prendre-en-charge")
    public ResponseEntity<BesoinRecrutementDTO> prendreEnCharge(@PathVariable Long id,
                                                                @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(besoinService.prendreEnCharge(id, jwt.getSubject()));
    }
}