package org.sid.services;

import org.sid.Repository.PostulezRepository;
import org.sid.Repository.StageRepository;
import org.sid.Repository.StagiaireRepository;
import org.sid.models.Postulez;
import org.sid.models.Stage;
import org.sid.models.Stagiaire;
import org.sid.models.StatutCandidature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostulezService {
    @Autowired
    private PostulezRepository postulezRepository;

    private final StageRepository stageRepository;
    private final StagiaireRepository stagiaireRepository;

    public PostulezService(StageRepository stageRepository, StagiaireRepository stagiaireRepository) {
        this.stageRepository = stageRepository;
        this.stagiaireRepository = stagiaireRepository;
    }

    // Lorsqu'un stagiaire postule
    public boolean postulerPourStage(Long stagiaireId, Long stageId) {
        Optional<Stagiaire> stagiaire = stagiaireRepository.findById(stagiaireId);
        Optional<Stage> stage = stageRepository.findById(stageId);

        if (stagiaire.isPresent() && stage.isPresent()) {

            // Vérifie si une candidature existe déjà pour ce stagiaire et ce stage
            Optional<Postulez> existingPostulez = postulezRepository.findByStagiaireAndStage(stagiaire.get(), stage.get());
            if (existingPostulez.isPresent()) {
                // Candidature existante, retourne false
                return false;
            }

            // Sinon, crée une nouvelle candidature
            Postulez candidature = new Postulez();
            candidature.setStatut(StatutCandidature.En_attente);
            candidature.setStagiaire(stagiaire.get());
            candidature.setStage(stage.get());

            postulezRepository.save(candidature);
            return true;
        }
        return false;
    }

    // Obtenir la liste de toutes les candidatures
    public List<Postulez> getAllPostulez() {
        return postulezRepository.findAll();
    }

    // Pour que l'administrateur mette à jour le statut
    public Postulez updateStatut(Long id ) {
        Optional<Postulez> postulezOptional = postulezRepository.findById(id);
        if (postulezOptional.isPresent()) {
            Postulez postulez = postulezOptional.get();
            postulez.setStatut(StatutCandidature.Accepte);
            return postulezRepository.save(postulez);
        }
        return null;
    }



    public List<Postulez> getStagiairePostulerById(Long idStagiaire) {
        return postulezRepository.findPostulezByStagiaire_Id(idStagiaire);
    }

    public void deletePostulez(Long id) {
    	postulezRepository.deleteById(id);
    }

}
