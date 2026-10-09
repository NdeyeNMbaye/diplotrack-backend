package sn.isi.diplotrack.mscompetences.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.isi.diplotrack.mscompetences.dto.IndicateursInsertionDTO;
import sn.isi.diplotrack.mscompetences.service.IndicateurService;

@RestController
@RequestMapping("/api/v1/indicateurs")
@RequiredArgsConstructor
public class IndicateurController {

    private final IndicateurService indicateurService;

    @GetMapping("/insertion")
    public ResponseEntity<IndicateursInsertionDTO> insertion() {
        return ResponseEntity.ok(indicateurService.calculer());
    }
}