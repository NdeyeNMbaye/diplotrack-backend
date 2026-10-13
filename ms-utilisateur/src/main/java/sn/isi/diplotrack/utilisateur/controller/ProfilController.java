package sn.isi.diplotrack.utilisateur.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.isi.diplotrack.utilisateur.dto.ProfilDTO;
import sn.isi.diplotrack.utilisateur.service.ProfilService;

@RestController
@RequestMapping("/api/v1/profils")
@RequiredArgsConstructor
public class ProfilController {

    private final ProfilService profilService;

    @GetMapping("/{diplomeId}")
    public ResponseEntity<ProfilDTO> findByDiplomeId(@PathVariable Long diplomeId) {
        return ResponseEntity.ok(profilService.findByDiplomeId(diplomeId));
    }

    @PutMapping("/{diplomeId}")
    public ResponseEntity<ProfilDTO> update(
            @PathVariable Long diplomeId,
            @RequestBody ProfilDTO dto) {
        return ResponseEntity.ok(profilService.update(diplomeId, dto));
    }
}