package org.sid.Controllers;

import org.sid.models.Postulez;
import org.sid.models.StatutCandidature;
import org.sid.services.PostulezService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postulez")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PostulezController {
    @Autowired
    private PostulezService postulezService;

    // Lorsqu'un stagiaire postule
    @PostMapping("/postuler/{idStagiaire}/{idStage}")
    @PreAuthorize("hasAuthority('Role_Stagiaire') or hasAuthority('Role_Admin')")
    public Boolean postuler(@PathVariable Long idStagiaire, @PathVariable Long idStage) {
        return postulezService.postulerPourStage(idStagiaire,idStage);
    }

    // Obtenir la liste de toutes les candidatures pour l'administrateur
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public List<Postulez> getAllPostulez() {
        return postulezService.getAllPostulez();
    }

    // Pour que l'administrateur mette à jour le statut
    @PutMapping("/valider/{id}")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public Postulez updateStatut(@PathVariable Long id) {
        return postulezService.updateStatut(id);
    }

    @GetMapping("/postulations/stagiaire/{idStagiaire}")
    @PreAuthorize("hasAuthority('Role_Stagiaire')")
    public List<Postulez> getStagiairePostulerById(@PathVariable Long idStagiaire) {
        return postulezService.getStagiairePostulerById(idStagiaire);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('Role_Stagiaire') or hasAuthority('Role_Admin')")
    public void CancelPostulez(@PathVariable Long id){
        postulezService.deletePostulez(id);
    }

}
