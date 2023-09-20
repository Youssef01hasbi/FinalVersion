package org.sid.Repository;


import org.sid.models.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface RapportRepository extends JpaRepository<Rapport,Long> {
}
