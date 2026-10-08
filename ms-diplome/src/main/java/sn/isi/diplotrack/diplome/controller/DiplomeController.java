package sn.isi.diplotrack.diplome.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.isi.diplotrack.diplome.dto.DiplomeDTO;
import sn.isi.diplotrack.diplome.service.DiplomeService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/diplomes")
@RequiredArgsConstructor
public class DiplomeController {

    private final DiplomeService diplomeService;

    @PostMapping
    public ResponseEntity<DiplomeDTO> save(@RequestBody DiplomeDTO dto) {
        return ResponseEntity.ok(diplomeService.save(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiplomeDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(diplomeService.findById(id));
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<DiplomeDTO>> findByUtilisateurId(@PathVariable Long utilisateurId) {
        return ResponseEntity.ok(diplomeService.findByUtilisateurId(utilisateurId));
    }

    @GetMapping("/verifier/{numeroIdentification}")
    public ResponseEntity<DiplomeDTO> verifier(@PathVariable String numeroIdentification) {
        return ResponseEntity.ok(diplomeService.findByNumeroIdentification(numeroIdentification));
    }
}