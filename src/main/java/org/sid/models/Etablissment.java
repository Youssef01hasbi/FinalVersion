package org.sid.models;


import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Etablissment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @OneToMany(mappedBy = "etablissment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Formation> formations = new HashSet<>();
}
