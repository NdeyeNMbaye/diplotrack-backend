package sn.isi.diplotrack.mscompetences.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.isi.diplotrack.mscompetences.dto.CompetenceDTO;
import sn.isi.diplotrack.mscompetences.service.CompetenceService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/competences")
@RequiredArgsConstructor
public class CompetenceController {

    private final CompetenceService competenceService;

    @PostMapping
    public ResponseEntity<CompetenceDTO> soumettre(@RequestBody CompetenceDTO dto) {
        return ResponseEntity.ok(competenceService.soumettre(dto));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<CompetenceDTO>> findByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(competenceService.findByStatut(statut));
    }

    @GetMapping("/profil/{profilId}")
    public ResponseEntity<List<CompetenceDTO>> findByProfil(@PathVariable Long profilId) {
        return ResponseEntity.ok(competenceService.findByProfilId(profilId));
    }

    @PutMapping("/{id}/approuver")
    public ResponseEntity<CompetenceDTO> approuver(@PathVariable Long id,
                                                   @RequestParam Long agentCoipId) {
        return ResponseEntity.ok(competenceService.approuver(id, agentCoipId));
    }

    @PutMapping("/{id}/refuser")
    public ResponseEntity<CompetenceDTO> refuser(@PathVariable Long id,
                                                 @RequestParam Long agentCoipId) {
        return ResponseEntity.ok(competenceService.refuser(id, agentCoipId));
    }
}