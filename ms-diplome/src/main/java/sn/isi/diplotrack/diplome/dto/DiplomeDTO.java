package sn.isi.diplotrack.diplome.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class DiplomeDTO {
    private Long id;
    private String intitule;
    private String niveau;
    private Integer anneeObtention;
    private String numeroIdentification;
    private String qrCode;
    private LocalDateTime dateEnregistrement;
    private String statut;
    private Long utilisateurId;
}