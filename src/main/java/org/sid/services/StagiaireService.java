package org.sid.services;

import org.sid.Repository.EtablisemmentRepository;
import org.sid.Repository.FormationRepository;
import org.sid.Repository.StagiaireRepository;
import org.sid.Repository.UserRepository;
import org.sid.models.Formation;
import org.sid.models.Stagiaire;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StagiaireService {

    private final StagiaireRepository stagiaireRepository;
    private final FormationRepository formationRepository;

    private final EtablisemmentRepository etablisemmentRepository;

    private final UserRepository userRepository;

    public StagiaireService(StagiaireRepository stagiaireRepository, FormationRepository formationRepository, EtablisemmentRepository etablisemmentRepository, UserRepository userRepository) {
        this.stagiaireRepository = stagiaireRepository;
        this.formationRepository = formationRepository;
        this.etablisemmentRepository = etablisemmentRepository;
        this.userRepository = userRepository;
    }

    public Stagiaire createStagiaire(Stagiaire stagiaire) {

        return stagiaireRepository.save(stagiaire);
    }

    public Stagiaire getStagiaireById(Long id) {
        return stagiaireRepository.findById(id).orElse(null);
    }

    public List<Stagiaire> getAllStagiaires() {
        return stagiaireRepository.findAll();
    }

    public void deleteStagiaireById(Long id) {
        stagiaireRepository.deleteById(id);
    }

    public Stagiaire updateStagiaire(Long id, Stagiaire updatedStagiaire) {
        Stagiaire existingStagiaire = stagiaireRepository.findById(id).orElse(null);

        if (existingStagiaire != null) {
            // Update the fields of the existingStagiaire with the fields of updatedStagiaire
            existingStagiaire.setNom(updatedStagiaire.getNom());
            existingStagiaire.setPrenom(updatedStagiaire.getPrenom());
            existingStagiaire.setCin(updatedStagiaire.getCin());
            existingStagiaire.setPhoto(updatedStagiaire.getPhoto());
            existingStagiaire.setDateNaissance(updatedStagiaire.getDateNaissance());
            existingStagiaire.setPathCv(updatedStagiaire.getPathCv());
            existingStagiaire.setTelephone(updatedStagiaire.getTelephone());
            existingStagiaire.setNiveauEtude(updatedStagiaire.getNiveauEtude());
            existingStagiaire.setFormation(updatedStagiaire.getFormation());

            // Save the updatedStagiaire to the database
            return stagiaireRepository.save(existingStagiaire);
        } else {
            return null; // Handle the case when the Stagiaire with the given ID is not found.
        }
    }

    public Stagiaire entrerLaFormation(Long stagiaireId, Formation formation) {
        Stagiaire stagiaire = stagiaireRepository.findById(stagiaireId).orElse(null);

        if (stagiaire == null || formation == null) {
            return null;
        }

        stagiaire.setFormation(formation);
        formation.getStagiaires().add(stagiaire);
        formationRepository.save(formation);
        return stagiaireRepository.save(stagiaire);
    }

   public Stagiaire updateStagiaireInformation(String username, Stagiaire updatedStagiaire) {
        Stagiaire existingStagiaire = stagiaireRepository.findStagiaireByUsername(username);

        if (existingStagiaire != null) {
            // Mettez à jour les informations du stagiaire existant avec les nouvelles informations
            existingStagiaire.setNom(updatedStagiaire.getNom());
            existingStagiaire.setPrenom(updatedStagiaire.getPrenom());
            existingStagiaire.setCin(updatedStagiaire.getCin());
            existingStagiaire.setPhoto(updatedStagiaire.getPhoto());
            existingStagiaire.setDateNaissance(updatedStagiaire.getDateNaissance());
            existingStagiaire.setPathCv(updatedStagiaire.getPathCv());
            existingStagiaire.setNiveauEtude(updatedStagiaire.getNiveauEtude());
            existingStagiaire.setTelephone(updatedStagiaire.getTelephone());
            existingStagiaire.setFormation(updatedStagiaire.getFormation());

            // Si vous voulez également mettre à jour l'établissement de la formation
            if (updatedStagiaire.getFormation() != null) {
                existingStagiaire.getFormation().setEtablissment(updatedStagiaire.getFormation().getEtablissment());
                // Enregistrez la formation mise à jour
                formationRepository.save(existingStagiaire.getFormation());
            }

            // Si vous voulez également mettre à jour l'établissement du stagiaire
            if (updatedStagiaire.getFormation() != null && updatedStagiaire.getFormation().getEtablissment() != null) {
                etablisemmentRepository.save(updatedStagiaire.getFormation().getEtablissment());
            }

            // Enregistrez les modifications apportées au stagiaire existant
            return stagiaireRepository.save(existingStagiaire);
        } else {
            // Le stagiaire n'a pas été trouvé, vous pouvez renvoyer null ou déclencher une exception personnalisée
            return null;
        }
    }


}

