package org.sid.Controllers;


import org.sid.models.Formation;
import org.sid.services.FormationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Formations")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FormationController {

    public final FormationService formationService;

    public FormationController(FormationService formationService) {
        this.formationService = formationService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Formation>> getAllFormation(){
        List<Formation> formations = formationService.getAllFormation();
        return new ResponseEntity<>(formations, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable Long id){
        Formation formation = formationService.findFormationById(id);
        return new ResponseEntity<>(formation,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Formation> addFormation(@RequestBody Formation formation){
        Formation NewFormation = formationService.addFormation(formation);
        return new ResponseEntity<>(NewFormation,HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Formation> updateFormation(@RequestBody Formation formation,@PathVariable Long id){
        Formation existingFormation = formationService.findFormationById(id);
        if (existingFormation == null ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingFormation.setSpecialite(formation.getSpecialite());
        existingFormation.setDiplome(formation.getDiplome());
        existingFormation.setEtablissment(formation.getEtablissment());
        existingFormation.setStagiaires(formation.getStagiaires());


        Formation updateFormation = formationService.updateFormation(existingFormation);
        return new ResponseEntity<>(updateFormation,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        formationService.deleteFormationById(id);
        return new  ResponseEntity<>(HttpStatus.OK);
    }
}
