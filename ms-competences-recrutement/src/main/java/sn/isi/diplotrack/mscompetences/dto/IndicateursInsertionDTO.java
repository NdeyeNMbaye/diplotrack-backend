package sn.isi.diplotrack.mscompetences.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndicateursInsertionDTO {
    private long competencesEnAttente;
    private long competencesApprouvees;
    private long competencesRefusees;
    private long besoinsRecus;
    private long besoinsEnCours;
    private long propositionsEnvoyees;
}