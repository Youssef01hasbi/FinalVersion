package org.sid.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Stage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    private String sujet;
    private String description;
    @Column(length = 50485760) // Spécifiez la taille en octets (10 Mo dans cet exemple)
    private byte[] photo;
    @ManyToOne
    private TypeStage typeStage;
    @ManyToOne  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Tuteur tuteur;

    @ManyToMany
    @JoinTable(
            name = "stage_stagiaire",
            joinColumns = @JoinColumn(name = "stage_id"),
            inverseJoinColumns = @JoinColumn(name = "stagiaire_id"))
    @JsonIgnoreProperties("stages") // pour éviter les boucles infinies dans le JSON
    private Collection<Stagiaire> stagiaires;

    @OneToMany(mappedBy = "stage")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //n'est pas la piene de me donne les rapport de se stage
    private Collection<Rapport> rapports;

    @OneToMany(mappedBy = "stage")
    private Collection<Evaluation> evaluations;

    @OneToMany(mappedBy = "stage")
    private Collection<Postulez> postulez;


}
