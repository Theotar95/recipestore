package com.recipe.recipestore.ingredient;

import com.recipe.recipestore.shared.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="api/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    IngredientController(IngredientService ingredientService){
        this.ingredientService= ingredientService;}

    @PutMapping("{id}")
    public ResponseEntity<IngredientResponseDTO> updateWeight (@PathVariable("id") Long id, @RequestBody IngredientRequestDTO ingredientRequestDTO) throws NotFoundException {
        IngredientResponseDTO ingredientResponseDTO = this.ingredientService.updateWeight(id, ingredientRequestDTO).getBody();
        return new ResponseEntity<>(ingredientResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteIngredient (@PathVariable("id") Long id)throws NotFoundException{
        this.ingredientService.deleteIngredient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
