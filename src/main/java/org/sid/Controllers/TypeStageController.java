package org.sid.Controllers;



import org.sid.models.TypeStage;
import org.sid.services.TypeStageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/TypeStages")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TypeStageController {

    private final TypeStageService stageService;


    public TypeStageController(TypeStageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping
    public ResponseEntity<List<TypeStage>> getAllType(){
        List<TypeStage> stages = stageService.getAllTypeStage();
        return new ResponseEntity<>(stages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeStage> getTypeById(@PathVariable Long id){
        TypeStage typeStage = stageService.getTypeStageById(id);
        return new ResponseEntity<>(typeStage,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TypeStage> addTypeStage(@RequestBody TypeStage typeStage){
        TypeStage newTypeStage = stageService.createTypeStage(typeStage);
        return new ResponseEntity<>(newTypeStage,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeStage> updateTypeStage(@PathVariable Long id, @RequestBody TypeStage typeStage){
        TypeStage existingType = stageService.getTypeStageById(id);
        if(existingType == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingType.setLabelle(typeStage.getLabelle());
        existingType.setStages(typeStage.getStages());

        TypeStage updateType = stageService.updateTypeStage(existingType);
        return new ResponseEntity<>(updateType,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id){
        stageService.deleteTypeStageById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
