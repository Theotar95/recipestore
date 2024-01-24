package com.recipe.recipestore.recipe;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (path = "api/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    public RecipeController (RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<RecipeResponseAllDTO>> getAllRecipe (){
        return new ResponseEntity<>(this.recipeService.getAllRecipe(), HttpStatus.OK);
    }

    @GetMapping (value = "/{id}")
    public ResponseEntity<RecipeResponseDTO> getRecipe (@PathVariable("id") Long id) throws BadRequestException {
        RecipeResponseDTO recipeResponseDTO = this.recipeService.getRecipe(id).getBody();
        return new ResponseEntity<>(recipeResponseDTO, HttpStatus.OK);
    }

    @PostMapping(path = "up")
    public ResponseEntity<RecipeRequestDTO> saveNewRecipe (@RequestBody RecipeRequestDTO recipeRequestDTO){
       this.recipeService.addNewRecipe(recipeRequestDTO);
       return new ResponseEntity<>(recipeRequestDTO, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<HttpStatus> updateRecipe (@PathVariable("id") Long id,@RequestBody RecipePatchDTO recipePatchDTO) throws BadRequestException {
        this.recipeService.updateRecipe(id,recipePatchDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
