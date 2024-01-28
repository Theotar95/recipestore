package com.recipe.recipestore.ingredient;

import com.recipe.recipestore.shared.exception.NotFoundException;
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
    public ResponseEntity<IngredientResponseDTO> updateWeight (Long id, IngredientRequestDTO ingredientRequestDTO) throws NotFoundException {
        Optional<Ingredient> ingredient = this.ingredientRepository.findById(id);

        if(ingredient.isEmpty()){
            throw new NotFoundException("Ingredient doesn't exist!");
        }

        IngredientResponseDTO ingredientResponseDTO = new IngredientResponseDTO();
        ingredientResponseDTO.setName(ingredient.get().getMaterial().getName());
        ingredientResponseDTO.setWeight(ingredientRequestDTO.getWeight());
        ingredient.get().setIngredientWeight(ingredientRequestDTO.getWeight());

        return new ResponseEntity<>(ingredientResponseDTO, HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteIngredient (Long id)throws NotFoundException{
        Optional<Ingredient> ingredient = this.ingredientRepository.findById(id);

        if(ingredient.isEmpty()){
            throw new NotFoundException("Ingredient doesn't exist!");
        }

        this.ingredientRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
