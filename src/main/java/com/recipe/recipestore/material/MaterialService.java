package com.recipe.recipestore.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository){
        this.materialRepository= materialRepository;
    }

    public List<Material> getMaterials() {return materialRepository.findAll();}

    public Optional<Material> getMaterial(String name) {return materialRepository.findMaterialByName(name);}

    public Optional<Material> getId(Long id) {return materialRepository.findById(id);}

    public void addMaterial(Material material){
        Optional<Material> nameCheck =materialRepository.findMaterialByName(material.getMatName());
        if(nameCheck.isPresent()){
            throw new IllegalStateException();
        }
        materialRepository.saveAndFlush(material);
    }

    public void deleteMaterial(Long id){
        Optional<Material> nameCheck = materialRepository.findById(id);
        if(nameCheck.isEmpty()){
            throw new IllegalStateException();
        }
        this.materialRepository.deleteById(id);
    }
}
