package com.recipe.recipestore.ingredient;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IngredientService {
private final IngredientRepository ingredientRepository;

    IngredientService (IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;}

    @Transactional
    public ResponseEntity<IngredientResponseDTO> updateWeight (Long id, IngredientRequestDTO ingredientRequestDTO){
        Optional<Ingredient> ingredient = this.ingredientRepository.findById(id);

        if(ingredient.isEmpty()){
            throw new IllegalStateException("This item doesn't exist");
        }

        IngredientResponseDTO ingredientResponseDTO = new IngredientResponseDTO();
        ingredientResponseDTO.setName(ingredient.get().getMaterial().getName());
        ingredientResponseDTO.setWeight(ingredientRequestDTO.getWeight());
        ingredient.get().setIngredientWeight(ingredientRequestDTO.getWeight());

        return new ResponseEntity<>(ingredientResponseDTO, HttpStatus.OK);
    }
}
