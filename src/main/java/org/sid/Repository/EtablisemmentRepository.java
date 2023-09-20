package org.sid.Repository;

import org.sid.models.Etablissment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface EtablisemmentRepository extends JpaRepository<Etablissment,Long> {
    Etablissment findEtablissmentById(Long id);
}
