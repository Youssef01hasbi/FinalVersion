package org.sid.services;


import org.sid.Repository.FormationRepository;
import org.sid.models.Formation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormationService {

    private final FormationRepository formationRepository;

    public FormationService(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    public List<Formation> getAllFormation(){
        return formationRepository.findAll();
    }

    public Formation findFormationById(Long id){
        return formationRepository.findFormationById(id);
    }

    public Formation addFormation(Formation formation){
        return formationRepository.save(formation);
    }

    public Formation updateFormation(Formation formation){
        return formationRepository.save(formation);
    }

    public void deleteFormationById(Long id){
         formationRepository.deleteById(id);
    }
}
