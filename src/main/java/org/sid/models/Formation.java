package org.sid.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Formation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String specialite;
    private String diplome;
    @ManyToOne
    @JoinColumn(name = "etablissment_id")
    @JsonBackReference // Add this annotation
    private Etablissment etablissment;

    @OneToMany(mappedBy = "formation")
    @EqualsAndHashCode.Exclude
    private List<Stagiaire> stagiaires = new ArrayList<>();
}
