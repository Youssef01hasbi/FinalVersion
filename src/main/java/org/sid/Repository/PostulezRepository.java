package org.sid.Repository;

import org.sid.models.Postulez;
import org.sid.models.Stage;
import org.sid.models.Stagiaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostulezRepository extends JpaRepository<Postulez, Long> {

    List<Postulez> findPostulezByStagiaire_Id(Long idStagiaire);

    Optional<Postulez> findByStagiaireAndStage(Stagiaire stagiaire, Stage stage);
}
