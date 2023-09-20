package org.sid.Repository;

import org.sid.models.Stagiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;


@RepositoryRestResource
@CrossOrigin("*")
public interface StagiaireRepository extends JpaRepository<Stagiaire,Long> {

    public Stagiaire findStagiaireByUsername(String username);
}
