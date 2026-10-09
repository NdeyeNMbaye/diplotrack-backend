package sn.isi.diplotrack.mscompetences.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.isi.diplotrack.mscompetences.dto.BesoinRecrutementDTO;
import sn.isi.diplotrack.mscompetences.service.BesoinRecrutementService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/besoins-recrutement")
@RequiredArgsConstructor
public class BesoinRecrutementController {

    private final BesoinRecrutementService besoinService;

    @PostMapping
    public ResponseEntity<BesoinRecrutementDTO> soumettre(@RequestBody BesoinRecrutementDTO dto) {
        return ResponseEntity.ok(besoinService.soumettre(dto));
    }

    @GetMapping
    public ResponseEntity<List<BesoinRecrutementDTO>> findAll() {
        return ResponseEntity.ok(besoinService.findAll());
    }

    @GetMapping("/entreprise/{entrepriseId}")
    public ResponseEntity<List<BesoinRecrutementDTO>> findByEntreprise(@PathVariable Long entrepriseId) {
        return ResponseEntity.ok(besoinService.findByEntreprise(entrepriseId));
    }

    @PutMapping("/{id}/prendre-en-charge")
    public ResponseEntity<BesoinRecrutementDTO> prendreEnCharge(@PathVariable Long id,
                                                                @RequestParam Long agentCoipId) {
        return ResponseEntity.ok(besoinService.prendreEnCharge(id, agentCoipId));
    }
}