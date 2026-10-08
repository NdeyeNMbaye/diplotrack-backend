package sn.isi.diplotrack.msoffre.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.isi.diplotrack.msoffre.dto.OffreDTO;
import sn.isi.diplotrack.msoffre.service.OffreService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/offres")
@RequiredArgsConstructor
public class OffreController {

    private final OffreService offreService;

    @PostMapping
    public ResponseEntity<OffreDTO> save(@RequestBody OffreDTO dto) {
        return ResponseEntity.ok(offreService.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<OffreDTO>> findAll() {
        return ResponseEntity.ok(offreService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OffreDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(offreService.findById(id));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<OffreDTO>> findByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(offreService.findByStatut(statut));
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<OffreDTO> updateStatut(
            @PathVariable Long id,
            @RequestParam String statut) {
        return ResponseEntity.ok(offreService.updateStatut(id, statut));
    }
}