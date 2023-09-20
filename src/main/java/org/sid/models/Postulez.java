package org.sid.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Postulez implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatutCandidature statut;

    @ManyToOne
    @JoinColumn(name = "stagiaire_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "postulez"})
    private Stagiaire stagiaire;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "postulez"})
    private Stage stage;

}




