package com.recipe.recipestore.material;

import com.recipe.recipestore.shared.exception.BadRequestException;
import com.recipe.recipestore.shared.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (path = "api/material")
public class MaterialController {
    private final MaterialService materialService;

    public MaterialController (MaterialService materialService){
        this.materialService = materialService;
    }

    @GetMapping
    public ResponseEntity<List<MaterialRequestDTO>> getAllMaterial (){
       List<MaterialRequestDTO> materialRequestDTOS = this.materialService.getMaterials();
        return new ResponseEntity<>(materialRequestDTOS, HttpStatus.OK);
    }

    @PostMapping(path = "upload")
    public ResponseEntity<MaterialCreateDTO> createMaterial (@RequestBody MaterialCreateDTO materialCreateDTO) throws BadRequestException {
        MaterialCreateDTO materialCreate = this.materialService.addNewMaterial(materialCreateDTO).getBody();
        return new ResponseEntity<>(materialCreate, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteMaterial(@PathVariable("id") Long id){
        this.materialService.deleteMaterial(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<MaterialCreateDTO> updateMaterial(@PathVariable("id") Long id, @RequestBody MaterialCreateDTO materialUpdateDTO) throws NotFoundException {
        MaterialCreateDTO materialCreate = this.materialService.updateMaterial(id, materialUpdateDTO).getBody();
        return new ResponseEntity<>(materialCreate, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<HttpStatus> patchMaterial(@PathVariable("id") Long id, @RequestBody MaterialRequestDTO materialRequestDTO) throws NotFoundException {
        this.materialService.patchMaterial(id, materialRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
