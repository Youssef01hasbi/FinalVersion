package org.sid.services;


import org.sid.Repository.StageRepository;
import org.sid.Repository.TuteurRepository;
import org.sid.models.Stage;
import org.sid.models.Stagiaire;
import org.sid.models.Tuteur;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class TuteurService {

    private final TuteurRepository tuteurRepository;

    private final StageRepository stageRepository;

    public TuteurService(TuteurRepository tuteurRepository, StageRepository stageRepository) {
        this.tuteurRepository = tuteurRepository;
        this.stageRepository = stageRepository;
    }

    public List<Tuteur> getAllTuteur(){
        return tuteurRepository.findAll();
    }

    public Tuteur getTuteurById(Long id){
        return tuteurRepository.findTuteurById(id);
    }

    public Tuteur createTuteur(Tuteur tuteur){
        return tuteurRepository.save(tuteur);
    }

    public Tuteur updateTuteur(Tuteur tuteur){
        return tuteurRepository.save(tuteur);
    }

    public void deleteById(Long id){
        tuteurRepository.deleteById(id);
    }

    //add stage to tuteur
    public Tuteur addStageToTuteur(Long idTuteur, Long idStage) {
        Tuteur tuteur = tuteurRepository.findTuteurById(idTuteur);
        if (tuteur != null) {
            Stage stageAffecter = stageRepository.findStageById(idStage);
            if (stageAffecter != null) {
                tuteur.getStages().add(stageAffecter);
                stageAffecter.setTuteur(tuteur); // Définir le tuteur pour le stage
                tuteurRepository.save(tuteur);
                stageRepository.save(stageAffecter); // Enregistrer le stage mis à jour
                return tuteur;
            }

        }
        return null;
    }

    public Tuteur getTuteurByStageId(Long idStage){
        Stage stage = stageRepository.findStageById(idStage);
        if (stage != null) {
            return stage.getTuteur();
        }
        return null;
    }



    public Collection<Stage> getStagesByTuteurId(Long idTuteur){
        Tuteur tuteur = tuteurRepository.findTuteurById(idTuteur);
        if (tuteur != null) {
            return tuteur.getStages();
        }
        return null;
    }
}
