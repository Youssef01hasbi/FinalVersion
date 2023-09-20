package org.sid.Repository;


import org.sid.models.TypeStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;


@RepositoryRestResource
@CrossOrigin("*")
public interface TypeStageRepository extends JpaRepository<TypeStage,Long> {
    TypeStage findTypeStageById(Long id);
}
