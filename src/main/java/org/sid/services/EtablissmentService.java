package org.sid.services;


import org.sid.Exception.EtablissmentNotFoundException;
import org.sid.Repository.EtablisemmentRepository;
import org.sid.Repository.FormationRepository;

import org.sid.models.Etablissment;
import org.sid.models.Formation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtablissmentService {

    private final EtablisemmentRepository etablisemmentRepository;

    private final FormationRepository formationRepository;

    public EtablissmentService(EtablisemmentRepository etablisemmentRepository, FormationRepository formationRepository) {
        this.etablisemmentRepository = etablisemmentRepository;
        this.formationRepository = formationRepository;
    }

    public List<Etablissment> findAllEtablissment(){
        return etablisemmentRepository.findAll();
    }

    public Etablissment addEtablissment(Etablissment etablissment){
        return etablisemmentRepository.save(etablissment);
    }

    public Etablissment updateEtablissment(Etablissment etablissment){
        return etablisemmentRepository.save(etablissment);
    }

    public Etablissment findEtablissmentById(Long id){
        return etablisemmentRepository.findEtablissmentById(id);
    }

    public void deleteEtablissmentById(Long id){
        etablisemmentRepository.deleteById(id);
    }

    public Etablissment addFormationToEtablissment(Long etablissmentId, Formation formation) {
        Etablissment etablissment = etablisemmentRepository.findById(etablissmentId).orElse(null);

        if (etablissment == null) {
            throw new EtablissmentNotFoundException("Etablissment with ID " + etablissmentId + " not found.");
        }

        formation.setEtablissment(etablissment);
        etablissment.getFormations().add(formation);

        formationRepository.save(formation);
        etablisemmentRepository.save(etablissment);

        return etablissment;
    }
}

