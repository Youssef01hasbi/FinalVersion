package org.sid.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sid.Exception.StageNotFoundException;
import org.sid.models.Stage;
import org.sid.models.Tuteur;
import org.sid.services.StageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/stages")
public class StageRestController {

    private final StageService stageService;

    public StageRestController(StageService stageService) {
        this.stageService = stageService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Stage>> getAllStage(){
        List<Stage> stages = stageService.getAllStages();
        return new ResponseEntity<>(stages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Role_Admin') or hasAuthority('Role_Stagiaire')")
    public ResponseEntity<Stage> findStageById(@PathVariable Long id){
        Stage stage = stageService.findStageById(id);
        return new ResponseEntity<>(stage,HttpStatus.OK);
    }

    @PostMapping("/ajouter")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public ResponseEntity<Stage> ajouterStage(@RequestParam("stage") String stageJson,
                                              @RequestParam(value = "photo", required = false) MultipartFile photo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Stage stage = objectMapper.readValue(stageJson, Stage.class);
            Stage addedStage = stageService.ajouterStage(stage, photo);
            return ResponseEntity.ok(addedStage);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }



    @GetMapping("/photo/{id}")
    public ResponseEntity<byte[]> getStagePhoto(@PathVariable Long id) {
        byte[] photoBytes = stageService.getStagePhoto(id);

        if (photoBytes != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Assumption: JPEG image format, adjust as needed
            return new ResponseEntity<>(photoBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping
    @PreAuthorize("hasAuthority('Role_Admin')")
    public ResponseEntity<Stage> updateStage(@RequestBody Stage stage)throws StageNotFoundException {
        Stage updateStage = stageService.updateStage(stage);
        return new ResponseEntity<>(updateStage,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public ResponseEntity<?> deleteStage(@PathVariable Long id){
        stageService.deleteStage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // remove stage from tuteur
    @DeleteMapping("/{tuteurId}/removeStage/{stageId}")
    @PreAuthorize("hasAuthority('Role_Admin')")
    public ResponseEntity<Tuteur> removeStageFromTuteur(
            @PathVariable Long tuteurId,
            @PathVariable Long stageId) {
        stageService.removeStageFromTuteur(tuteurId, stageId);
        return ResponseEntity.ok().build();
    }


}
