package org.sid.Controllers;


import org.sid.models.Etablissment;
import org.sid.models.Formation;
import org.sid.services.EtablissmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Etablissments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EtablismmentController {

    private final EtablissmentService etablissmentService;


    public EtablismmentController(EtablissmentService etablissmentService) {
        this.etablissmentService = etablissmentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Etablissment>> getAllEtablissment(){
        List<Etablissment> etablissments = etablissmentService.findAllEtablissment();
        return new ResponseEntity<>(etablissments, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Etablissment> getEtablissmentById(@PathVariable("id") Long id){
        Etablissment etablissment = etablissmentService.findEtablissmentById(id);
        return new ResponseEntity<>(etablissment,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Etablissment> addEtablisment(@RequestBody Etablissment etablissment){
        Etablissment NewEtablissment = etablissmentService.addEtablissment(etablissment);
        return new ResponseEntity<>(NewEtablissment,HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Etablissment> updateEtablissment(@PathVariable Long id, @RequestBody Etablissment etablissment) {
        // First, check if the etablissment with the given id exists in the database
        Etablissment existingEtablissment = etablissmentService.findEtablissmentById(id);
        if (existingEtablissment == null) {
            // Return a response with 404 Not Found status if the etablissment is not found.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingEtablissment.setNom(etablissment.getNom());
        existingEtablissment.setFormations(etablissment.getFormations());

        // Save the updated etablissment back to the database.
        Etablissment updatedEtablissment = etablissmentService.updateEtablissment(existingEtablissment);

        // Return a response with the updated etablissment and 200 OK status.
        return new ResponseEntity<>(updatedEtablissment, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEtablissment(@PathVariable("id") Long id){
        etablissmentService.deleteEtablissmentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{etablissmentId}/formations")
    public ResponseEntity<Etablissment> addFormationToEtablissment(
            @PathVariable Long etablissmentId,
            @RequestBody Formation formation
    ) {
        Etablissment updatedEtablissment = etablissmentService.addFormationToEtablissment(etablissmentId, formation);

        if (updatedEtablissment == null) {
            // Handle the case when the Etablissment with the given ID is not found.
            // You can return an error response with 404 Not Found status.
            return ResponseEntity.notFound().build();
        }

        // Return the updated Etablissment with 201 Created status.
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedEtablissment);
    }

}
