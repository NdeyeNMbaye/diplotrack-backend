package sn.isi.diplotrack.mscompetences.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.isi.diplotrack.mscompetences.dto.PropositionProfilDTO;
import sn.isi.diplotrack.mscompetences.service.PropositionProfilService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/propositions-profils")
@RequiredArgsConstructor
public class PropositionProfilController {

    private final PropositionProfilService propositionService;

    @PostMapping
    public ResponseEntity<PropositionProfilDTO> proposer(@RequestBody PropositionProfilDTO dto) {
        return ResponseEntity.ok(propositionService.proposer(dto));
    }

    @GetMapping("/besoin/{besoinId}")
    public ResponseEntity<List<PropositionProfilDTO>> findByBesoin(@PathVariable Long besoinId) {
        return ResponseEntity.ok(propositionService.findByBesoin(besoinId));
    }
}