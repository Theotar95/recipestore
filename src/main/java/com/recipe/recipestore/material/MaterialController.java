package com.recipe.recipestore.material;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping (path = "api/material")
public class MaterialController {
    private final MaterialService materialService;

    public MaterialController (MaterialService materialService){
        this.materialService = materialService;
    }

    @GetMapping
    public List<Material> getMaterials() {return this.materialService.getMaterials();}

    @GetMapping(value = "/{name}")
    public Optional<Material> getMaterial(@PathVariable("name")String name){return this.materialService.getMaterial(name);}

    @PostMapping(path = "up")
    public void createMaterial(@RequestBody Material material){
        this.materialService.addMaterial(material);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteMaterial(@PathVariable("id") Long id){this.materialService.deleteMaterial(id);}
}
