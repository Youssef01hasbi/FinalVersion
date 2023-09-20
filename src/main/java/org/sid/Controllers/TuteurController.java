package org.sid.Controllers;


import jakarta.validation.Valid;
import org.sid.Repository.RoleRepository;
import org.sid.models.*;
import org.sid.services.TuteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/Tuteurs")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TuteurController {

    private final TuteurService tuteurService;

    private final RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public TuteurController(TuteurService tuteurService, RoleRepository roleRepository) {
        this.tuteurService = tuteurService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Role_Stagiaire') or hasAuthority('Role_Encadrant') or hasAuthority('Role_Admin')")
    public ResponseEntity<List<Tuteur>> getAll(){
        List<Tuteur> tuteurs = tuteurService.getAllTuteur();
        return new ResponseEntity<>(tuteurs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public ResponseEntity<Tuteur> findTuteur(@PathVariable Long id){
        Tuteur tuteur = tuteurService.getTuteurById(id);
        return new ResponseEntity<>(tuteur,HttpStatus.OK);
    }

    /*@PostMapping
    @PreAuthorize("hasAuthority('Role_Admin')")
    public ResponseEntity<Tuteur> addTuteur(@RequestBody Tuteur tuteur){
        Tuteur newTuteur = tuteurService.createTuteur(tuteur);
        return new ResponseEntity<>(newTuteur,HttpStatus.CREATED);
    }*/

    @PostMapping
    @PreAuthorize("hasAuthority('Role_Admin')")
    public ResponseEntity<Tuteur> addTuteur(@Valid @RequestBody Tuteur tuteur){
        Role roleEncadrant = roleRepository.findByName(ERole.Role_Encadrant)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        tuteur.getRoles().add(roleEncadrant);
        tuteur.setPassword(encoder.encode(tuteur.getPassword()));

        Tuteur newTuteur = tuteurService.createTuteur(tuteur);
        return new ResponseEntity<>(newTuteur,HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('Role_Admin')")
    public ResponseEntity<Tuteur> updateTuteur(@Valid @RequestBody Tuteur tuteur){
        Tuteur updateTuteur = tuteurService.updateTuteur(tuteur);
        return new ResponseEntity<>(updateTuteur,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        tuteurService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //add stage to tuteur

    @PutMapping("/{tuteurId}/addStage/{stageId}")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public ResponseEntity<Tuteur> addStageToTuteur(
            @PathVariable Long tuteurId,
            @PathVariable Long stageId) {
        Tuteur tuteur = tuteurService.addStageToTuteur(tuteurId, stageId);
        if (tuteur == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tuteur);
    }


    @GetMapping("/stage/{id}")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public ResponseEntity<Tuteur> getTuteurByStageId(@PathVariable Long id){
        Tuteur tuteur = tuteurService.getTuteurByStageId(id);
        return new ResponseEntity<>(tuteur,HttpStatus.OK);
    }



    @GetMapping("/{idTuteur}/stages")
    @PreAuthorize("hasAuthority('Role_Encadrant')")
    public ResponseEntity<Collection<Stage>> getStagesByTuteurId(@PathVariable Long idTuteur) {
        Collection<Stage> listStage = tuteurService.getStagesByTuteurId(idTuteur);
        if (listStage != null && !listStage.isEmpty()) {
            return new ResponseEntity<>(listStage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
