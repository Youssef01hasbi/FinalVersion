package org.sid.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class  Stagiaire extends User implements Serializable {
    @Column(length = 75)
    private String nom;
    @Column(length = 75)
    private String prenom;
    @Column(unique = true,length = 8)
    private String cin;
    private String photo;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    private String pathCv;
    private String telephone;
    private String NiveauEtude;
    @ManyToOne
    @JoinColumn(name = "formation_id")
    @JsonBackReference // Add this annotation
    private Formation formation;

    @OneToMany(mappedBy = "stagiaire",cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Collection<Evaluation> evaluation;

    @OneToMany(mappedBy = "stagiaire")
    private Collection<Postulez> postulez;

    @ManyToMany(mappedBy = "stagiaires")
    @JsonIgnoreProperties("stagiaires") // pour éviter les boucles infinies dans le JSON
    private Collection<Stage> stages;



}