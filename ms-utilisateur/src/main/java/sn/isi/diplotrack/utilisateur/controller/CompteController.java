package sn.isi.diplotrack.utilisateur.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.isi.diplotrack.utilisateur.dto.CreationCompteDTO;
import sn.isi.diplotrack.utilisateur.dto.UtilisateurDTO;
import sn.isi.diplotrack.utilisateur.service.CompteService;

/** Ouverture de comptes, réservée à l'administrateur (F17). */
@RestController
@RequestMapping("/api/v1/utilisateurs")
@RequiredArgsConstructor
public class CompteController {

    private final CompteService compteService;

    @PostMapping
    public ResponseEntity<UtilisateurDTO> creer(@Valid @RequestBody CreationCompteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compteService.creer(dto));
    }
}