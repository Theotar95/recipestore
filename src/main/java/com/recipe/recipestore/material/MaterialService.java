package com.recipe.recipestore.material;

import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository){
        this.materialRepository= materialRepository;
    }
    public List<MaterialRequestDTO> getMaterials() {
        List<Material> materials = materialRepository.findAll();
        List<MaterialRequestDTO> materialRequestDTOS = new ArrayList<>();

        for (Material element: materials ) {
            MaterialRequestDTO materialRequestDTO = new MaterialRequestDTO();
            materialRequestDTO.setName(element.getName());
            materialRequestDTO.setUnit(element.getUnit());
            materialRequestDTO.setWeight(element.getWeight());
            materialRequestDTO.setPrice(element.getPrice());
            materialRequestDTOS.add(materialRequestDTO);
        }
        return (materialRequestDTOS);
    }

    public ResponseEntity<MaterialCreateDTO> addNewMaterial (MaterialCreateDTO materialCreateDTO){
        Optional<Material> nameAvailable = Optional.ofNullable(materialRepository.findMaterialByName(materialCreateDTO.getName()));

        if (nameAvailable.isPresent()){
            throw new IllegalStateException("This name is exist!");
        }

        Material newMaterial = new Material();
        newMaterial.setName(materialCreateDTO.getName());
        newMaterial.setUnit(materialCreateDTO.getUnit());
        newMaterial.setWeight(materialCreateDTO.getWeight());
        newMaterial.setPrice(materialCreateDTO.getPrice());
        materialRepository.save(newMaterial);

        return new ResponseEntity<>(materialCreateDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<HttpStatus> deleteMaterial(Long id){
        Optional<Material> nameCheck = materialRepository.findById(id);
        if(nameCheck.isEmpty()){
            throw new IllegalStateException();
        }
        this.materialRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    public  ResponseEntity<MaterialCreateDTO> updateMaterial (Long id, MaterialCreateDTO materialUpdateDto){
        Optional<Material> material = this.materialRepository.findById(id);

        if (material.isEmpty()){
            throw new IllegalStateException("This item doesn't exist");
        }

        MaterialCreateDTO materialRequest = new MaterialCreateDTO();
        materialRequest.setName(materialUpdateDto.getName());
        materialRequest.setWeight(materialUpdateDto.getWeight());
        materialRequest.setUnit(materialUpdateDto.getUnit());
        materialRequest.setPrice(materialUpdateDto.getPrice());

        material.get().setName(materialRequest.getName());
        material.get().setPrice(materialRequest.getPrice());
        material.get().setUnit(materialRequest.getUnit());
        material.get().setWeight(materialRequest.getWeight());

        return new ResponseEntity<>(materialRequest, HttpStatus.OK);

    }

    public ResponseEntity<HttpStatus> patchMaterial(Long id, MaterialRequestDTO materialRequestDTO) throws BadRequestException {
        Material existingMaterial = this.materialRepository.findById(id)
                .orElseThrow( () -> new BadRequestException("This material doesn't exist"));

        if(materialRequestDTO.getName() != null){
            existingMaterial.setName(materialRequestDTO.getName());
        }
        if(materialRequestDTO.getPrice() != null){
            existingMaterial.setPrice(materialRequestDTO.getPrice());
        }
        if(materialRequestDTO.getWeight() != null){
            existingMaterial.setWeight(materialRequestDTO.getWeight());
        }
        if(materialRequestDTO.getUnit() != null){
            existingMaterial.setUnit(materialRequestDTO.getUnit());
        }
        this.materialRepository.save(existingMaterial);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
