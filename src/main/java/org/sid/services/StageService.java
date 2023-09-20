package org.sid.services;

import org.sid.Exception.StageNotFoundException;
import org.sid.Repository.StageRepository;
import org.sid.Repository.TuteurRepository;

import org.sid.models.Stage;
import org.sid.models.Tuteur;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class StageService {


    private final StageRepository stageRepository;
    private final TuteurRepository tuteurRepository;


    public StageService(StageRepository stageRepository,
                        TuteurRepository tuteurRepository) {
        this.stageRepository = stageRepository;
        this.tuteurRepository = tuteurRepository;
    }

    public List<Stage> getAllStages(){
        return stageRepository.findAll();
    }

    public Stage getStageById(Long id){
        return stageRepository.findStageById(id);
    }

   public byte[] getStagePhoto(Long id) {
       Stage stage = getStageById(id);
       if (stage != null) {
           return stage.getPhoto();
       }
       return null;
   }

    public Stage ajouterStage(Stage stage, MultipartFile photo) {
        // Sauvegarder le stage dans la base de données
        Stage savedStage = stageRepository.save(stage);
        // Traiter le téléchargement de la photo et la lier au stage
        if (photo != null && !photo.isEmpty()) {
            byte[] photoBytes;
            try {
                photoBytes = photo.getBytes();
                savedStage.setPhoto(photoBytes);
                stageRepository.save(savedStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return savedStage;
    }


    public Stage findStageById(Long id){
        return stageRepository.findStageById(id);
    }

    public Stage updateStage(Stage stage) throws StageNotFoundException {
        // Fetch the existing stage from the repository using its ID
        Optional<Stage> existingStageOptional = stageRepository.findById(stage.getId());

        if (existingStageOptional.isPresent()) {
            Stage existingStage = existingStageOptional.get();

            // Update the fields of the existing stage with the data from the provided stage
            existingStage.setDateDebut(stage.getDateDebut());
            existingStage.setDateFin(stage.getDateFin());
            existingStage.setSujet(stage.getSujet());
            existingStage.setPhoto(stage.getPhoto());
            existingStage.setTypeStage(stage.getTypeStage());
            existingStage.setTuteur(stage.getTuteur());

            // You can similarly update other attributes as well

            // Save the updated stage
            return stageRepository.save(existingStage);
        } else {
            // Handle error if the stage with the given ID doesn't exist
            throw new StageNotFoundException("Stage not found with ID: " + stage.getId());
        }
    }

    // remove stage from tuteur
    public Stage removeStageFromTuteur(Long idTuteur, Long idStage) {
        Tuteur tuteur = tuteurRepository.findTuteurById(idTuteur);
        if (tuteur != null) {
            Stage stage = stageRepository.findStageById(idStage);
            if (stage != null) {
                tuteur.getStages().remove(stage);
                stage.setTuteur(null); // Définir le tuteur pour le stage
                tuteurRepository.save(tuteur);
                stageRepository.save(stage); // Enregistrer le stage mis à jour
                return stage;
            }

        }
        return null;
    }

    public void deleteStage(Long id){
        stageRepository.deleteById(id);
    }

}
