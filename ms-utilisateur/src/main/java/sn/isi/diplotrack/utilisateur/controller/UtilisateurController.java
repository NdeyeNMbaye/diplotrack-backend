package sn.isi.diplotrack.utilisateur.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.isi.diplotrack.utilisateur.dto.UtilisateurDTO;
import sn.isi.diplotrack.utilisateur.service.UtilisateurService;

import java.util.List;

/** Consultation des comptes (administrateur, agents) et fiche de l'utilisateur connecté. */
@RestController
@RequestMapping("/api/v1/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @GetMapping
    public ResponseEntity<List<UtilisateurDTO>> findAll() {
        return ResponseEntity.ok(utilisateurService.findAll());
    }

    @GetMapping("/moi")
    public ResponseEntity<UtilisateurDTO> moi(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(utilisateurService.findByKeycloakId(jwt.getSubject()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.findById(id));
    }

    @PatchMapping("/{id}/actif")
    public ResponseEntity<Void> updateActif(@PathVariable Long id, @RequestParam Boolean actif) {
        utilisateurService.updateActif(id, actif);
        return ResponseEntity.ok().build();
    }
}