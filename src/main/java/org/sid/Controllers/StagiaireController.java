package org.sid.Controllers;


import jakarta.validation.Valid;
import org.sid.Repository.RoleRepository;
import org.sid.models.*;
import org.sid.services.EtablissmentService;
import org.sid.services.FormationService;
import org.sid.services.StagiaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stagiaires")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StagiaireController {

    @Autowired
    private final StagiaireService stagiaireService;

    private final FormationService formationService;

    private final EtablissmentService etablissmentService;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    public StagiaireController(StagiaireService stagiaireService, FormationService formationService, EtablissmentService etablissmentService, RoleRepository roleRepository) {
        this.stagiaireService = stagiaireService;
        this.formationService = formationService;
        this.etablissmentService = etablissmentService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<Stagiaire> createStagiaire(@Valid @RequestBody Stagiaire stagiaire) {
        // Create a Role instance (you might need to fetch it from your database)
        Role roleStagiaire = roleRepository.findByName(ERole.Role_Stagiaire)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Add the role to the Stagiaire's roles set
        stagiaire.getRoles().add(roleStagiaire);
        stagiaire.setPassword(encoder.encode(stagiaire.getPassword()));

        // Ensure Etablissment is saved
        Etablissment etablissment = stagiaire.getFormation().getEtablissment();
        if (etablissment != null && etablissment.getId() == null) {
            etablissment = etablissmentService.addEtablissment(etablissment); // Replace with your actual service method
            stagiaire.getFormation().setEtablissment(etablissment);
        }

        // Ensure Formation is saved
        Formation formation = stagiaire.getFormation();
        if (formation != null && formation.getId() == null) {
            formation = formationService.addFormation(formation); // Replace with your actual service method
            stagiaire.setFormation(formation);
        }

        // Save the Stagiaire
        Stagiaire newStagiaire = stagiaireService.createStagiaire(stagiaire);

        return new ResponseEntity<>(newStagiaire, HttpStatus.OK);
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<Stagiaire> getStagiaireById(@PathVariable Long id) {
        Stagiaire stagiaire = stagiaireService.getStagiaireById(id);
        if (stagiaire == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stagiaire);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public ResponseEntity<List<Stagiaire>> getAllStagiaires() {
        List<Stagiaire> stagiaires = stagiaireService.getAllStagiaires();
        return ResponseEntity.ok(stagiaires);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public ResponseEntity<Void> deleteStagiaireById(@PathVariable Long id) {
        stagiaireService.deleteStagiaireById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public ResponseEntity<Stagiaire> updateStagiaire(@PathVariable Long id, @RequestBody Stagiaire updatedStagiaire) {
        Stagiaire updatedStagiaireResult = stagiaireService.updateStagiaire(id, updatedStagiaire);
        if (updatedStagiaireResult == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStagiaireResult);
    }

    @PutMapping("/{stagiaireId}/addFormation")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public ResponseEntity<Stagiaire> entrerLaFormation(
            @PathVariable Long stagiaireId,
            @RequestBody Formation formation
    ) {
        Stagiaire stagiaire = stagiaireService.entrerLaFormation(stagiaireId, formation);

        if (stagiaire == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(stagiaire);
    }

    @Transactional
    @PutMapping("/updateInformation/{username}")
    @PreAuthorize("hasAuthority('Role_Stagiaire')")
    public ResponseEntity<?> updateStagiaireInformation(@PathVariable String username, @RequestBody Stagiaire stagiaire) {
        Stagiaire updatedStagiaire = stagiaireService.updateStagiaireInformation(username, stagiaire);
        if (updatedStagiaire != null) {
            return ResponseEntity.ok(updatedStagiaire);
        } else {
            return ResponseEntity.badRequest().body("La mise à jour des informations du stagiaire a échoué.");
        }
    }

}

