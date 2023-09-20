package org.sid.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.io.Serializable;
import java.util.Collection;


@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Tuteur extends User implements Serializable {
    private String nom;
    private String tele;
    @OneToMany(mappedBy = "tuteur")
    private Collection<Stage> stages;
}