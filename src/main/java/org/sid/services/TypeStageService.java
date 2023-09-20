package org.sid.services;

import org.sid.Repository.TypeStageRepository;
import org.sid.models.TypeStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeStageService {

    @Autowired
    private final TypeStageRepository typeStageRepository ;


    public TypeStageService(TypeStageRepository typeStageRepository) {
        this.typeStageRepository = typeStageRepository;
    }

    public List<TypeStage> getAllTypeStage(){
        return typeStageRepository.findAll();
    }

    public TypeStage getTypeStageById(Long id){
        return typeStageRepository.findTypeStageById(id);
    }

    public TypeStage createTypeStage(TypeStage typeStage){
        return typeStageRepository.save(typeStage);
    }

    public TypeStage updateTypeStage(TypeStage typeStage){
        return typeStageRepository.save(typeStage);
    }

    public void deleteTypeStageById(Long id){
        typeStageRepository.deleteById(id);
    }
}
