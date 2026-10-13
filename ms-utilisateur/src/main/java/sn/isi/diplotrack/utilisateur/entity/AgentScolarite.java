package sn.isi.diplotrack.utilisateur.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "agent_scolarite")
public class AgentScolarite extends Utilisateur {

    @Column(nullable = false)
    private String matricule;
}