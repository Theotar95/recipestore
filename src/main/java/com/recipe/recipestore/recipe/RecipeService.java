package com.recipe.recipestore.recipe;

import com.recipe.recipestore.ingredient.Ingredient;
import com.recipe.recipestore.ingredient.IngredientRepository;
import com.recipe.recipestore.ingredient.IngredientRequestDTO;
import com.recipe.recipestore.ingredient.IngredientResponseDTO;
import com.recipe.recipestore.material.Material;
import com.recipe.recipestore.material.MaterialRepository;

import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final MaterialRepository materialRepository;


    public RecipeService (RecipeRepository recipeRepository, IngredientRepository ingredientRepository, MaterialRepository materialRepository){
        this.recipeRepository=recipeRepository;
        this.ingredientRepository=ingredientRepository;
        this.materialRepository=materialRepository;
    }

    public List<RecipeResponseAllDTO> getAllRecipe(){
        List<Recipe> recipes = this.recipeRepository.findAll();
        List<RecipeResponseAllDTO> filterdRecipes = new ArrayList<>();

        for (Recipe element: recipes) {
            RecipeResponseAllDTO recipe = new RecipeResponseAllDTO();
            recipe.setName(element.getName());
            recipe.setDescription(element.getDescription());
            recipe.setImage(element.getImage());
            filterdRecipes.add(recipe);
        }

        return filterdRecipes;
    }

    public ResponseEntity<RecipeResponseDTO> getRecipe(Long recipeId) throws BadRequestException {

        Optional<Recipe> recipeAvailable = recipeRepository.findById(recipeId);

        if(recipeAvailable.isEmpty() ){
            throw new BadRequestException("This recipe doesn't exist");
        }

        Long recipeLongId = recipeAvailable.get().getId();

        RecipeResponseDTO recipeResponseDTO = new RecipeResponseDTO();
        List<IngredientResponseDTO> ingredientResponseDTOS = new ArrayList<>();

        recipeResponseDTO.setName(recipeAvailable.get().getName());
        recipeResponseDTO.setDescription(recipeAvailable.get().getDescription());

        List<Ingredient> ingredients = this.ingredientRepository.findAll();

        for (Ingredient element:ingredients) {
            if(element.getRecipe().getId() == recipeLongId)
            {
                IngredientResponseDTO ingredientResponseDTO = new IngredientResponseDTO();

                ingredientResponseDTO.setName(element.getMaterial().getName());
                ingredientResponseDTO.setWeight(element.getIngredientWeight());

                ingredientResponseDTOS.add(ingredientResponseDTO);
            }
            recipeResponseDTO.setIngredients(ingredientResponseDTOS);
        }
        return new ResponseEntity<>(recipeResponseDTO, HttpStatus.OK);
    }


    public ResponseEntity<RecipeRequestDTO> addNewRecipe (RecipeRequestDTO recipeRequestDTO){
        Recipe recipe = new Recipe();
        recipe.setName(recipeRequestDTO.getName());
        recipe.setDescription(recipeRequestDTO.getDescription());
        recipeRepository.save(recipe);

        List<Ingredient> ingredients = new ArrayList<>();

        for (IngredientRequestDTO element: recipeRequestDTO.getIngredients()) {
            Long id = element.getId();
            Material material = materialRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This recipe doesn't exist"));

            Ingredient ingredient = new Ingredient();
            ingredient.setMaterial(material);
            ingredient.setIngredientWeight(element.getWeight());
            ingredient.setRecipe(recipe);

            ingredients.add(ingredient);
        }

        ingredientRepository.saveAll(ingredients);
        return new ResponseEntity<>(recipeRequestDTO, HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> updateRecipe (Long id, RecipePatchDTO recipePatchDTO) throws BadRequestException {
       Recipe recipe = this.recipeRepository.findById(id)
               .orElseThrow(() -> new BadRequestException("This recipe doesn't exist"));

        if(recipePatchDTO.getName() != null){
            recipe.setName(recipePatchDTO.getName());
        }
        if(recipePatchDTO.getImage() != null){
            recipe.setImage(recipePatchDTO.getImage());
        }
        if(recipePatchDTO.getDescription() != null){
            recipe.setDescription(recipePatchDTO.getDescription());
        }

        this.recipeRepository.save(recipe);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
