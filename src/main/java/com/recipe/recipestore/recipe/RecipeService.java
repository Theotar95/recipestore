package com.recipe.recipestore.recipe;

import com.recipe.recipestore.ingredient.Ingredient;
import com.recipe.recipestore.ingredient.IngredientRepository;
import com.recipe.recipestore.ingredient.IngredientRequestDTO;
import com.recipe.recipestore.ingredient.IngredientResponseDTO;
import com.recipe.recipestore.material.Material;
import com.recipe.recipestore.material.MaterialRepository;

import com.recipe.recipestore.shared.exception.BadRequestException;
import com.recipe.recipestore.shared.exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final MaterialRepository materialRepository;

    @PersistenceContext
    private final EntityManager entityManager;


    public RecipeService (RecipeRepository recipeRepository, IngredientRepository ingredientRepository, MaterialRepository materialRepository, EntityManager entityManager){
        this.recipeRepository=recipeRepository;
        this.ingredientRepository=ingredientRepository;
        this.materialRepository=materialRepository;
        this.entityManager=entityManager;
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

    public ResponseEntity<RecipeResponseDTO> getRecipe(Long recipeId) throws NotFoundException {

        Optional<Recipe> recipeAvailable = recipeRepository.findById(recipeId);

        if(recipeAvailable.isEmpty() ){
            throw new NotFoundException("Recipe doesn't exist");
        }

        Long recipeLongId = recipeAvailable.get().getId();

        RecipeResponseDTO recipeResponseDTO = new RecipeResponseDTO();
        List<IngredientResponseDTO> ingredientResponseDTOS = new ArrayList<>();

        recipeResponseDTO.setName(recipeAvailable.get().getName());
        recipeResponseDTO.setDescription(recipeAvailable.get().getDescription());

        List<Ingredient> ingredients = this.ingredientRepository.findAll();

        for (Ingredient element:ingredients) {
            if(element.getRecipe().getId().equals(recipeId))
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


    public ResponseEntity<RecipeRequestDTO> addNewRecipe (RecipeRequestDTO recipeRequestDTO) throws NotFoundException, BadRequestException {
        Optional<Recipe> nameAvailable= Optional.ofNullable(recipeRepository.findRecipeByName(recipeRequestDTO.getName()));

        if(nameAvailable.isPresent()) {
        throw new BadRequestException("This recipe is exist, name must be unique!");
        }

        Set<Long> uniqueIds = new HashSet<>();

        Recipe recipe = new Recipe();
        recipe.setName(recipeRequestDTO.getName());
        recipe.setDescription(recipeRequestDTO.getDescription());

        List<Ingredient> ingredients = new ArrayList<>();

        for (IngredientRequestDTO element: recipeRequestDTO.getIngredients()) {
            if(!uniqueIds.add(element.getId())){
                throw new BadRequestException("The id must be unique!");
            }
            Long id = element.getId();
            Material material = materialRepository.findById(id).orElseThrow(() -> new NotFoundException("Material doesn't exist"));

            Ingredient ingredient = new Ingredient();
            ingredient.setMaterial(material);
            ingredient.setIngredientWeight(element.getWeight());
            ingredient.setRecipe(recipe);

            ingredients.add(ingredient);
        }

        recipe.setIngredients(ingredients);
        recipeRepository.save(recipe);
        return new ResponseEntity<>(recipeRequestDTO, HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> updateRecipe (Long id, RecipePatchDTO recipePatchDTO) throws NotFoundException, BadRequestException {
       Recipe recipe = this.recipeRepository.findById(id)
               .orElseThrow(() -> new NotFoundException("Recipe doesn't exist"));
       List<Ingredient> ingredients = new ArrayList<>();
       Set<Long> uniqueIds = new HashSet<>();

        if(recipePatchDTO.getName() != null){
            recipe.setName(recipePatchDTO.getName());
        }
        if(recipePatchDTO.getImage() != null){
            recipe.setImage(recipePatchDTO.getImage());
        }
        if(recipePatchDTO.getDescription() != null){
            recipe.setDescription(recipePatchDTO.getDescription());
        }
        if(recipePatchDTO.getIngredients() !=null){
            for (IngredientRequestDTO element: recipePatchDTO.getIngredients()) {
                if(!uniqueIds.add(element.getId())){
                    throw new BadRequestException("The id must be unique!");
                }
                Material material = materialRepository.findById(element.getId()).orElseThrow(() -> new NotFoundException("Material doesn't exist"));

                Ingredient ingredient = new Ingredient();
                ingredient.setMaterial(material);
                ingredient.setIngredientWeight(element.getWeight());
                ingredient.setRecipe(recipe);

                ingredients.add(ingredient);
            }
        }
        recipe.setIngredients(ingredients);
        this.recipeRepository.save(recipe);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteRecipe (Long id) throws NotFoundException {
        Optional<Recipe> recipe = this.recipeRepository.findById(id);

        if(recipe.isEmpty()){
            throw new NotFoundException("Recipe doesn't exist");
        }
        this.recipeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @Transactional
    public ResponseEntity<HttpStatus> deleteIngredient (Long id) throws NotFoundException {
        Optional<Ingredient> ingredient = this.ingredientRepository.findById(id);

        if(ingredient.isEmpty()){
            throw new NotFoundException("Ingredient doesn't exist!");
        }

        Optional<Recipe> recipe = this.recipeRepository.findById(ingredient.get().getRecipe().getId());

        if(recipe.isEmpty()){
            throw new NotFoundException("Ingredient doesn't exist!");
        }

        List<Ingredient> ingredients = recipe.get().getIngredients();
        Iterator<Ingredient> iterator = ingredients.iterator();

        while (iterator.hasNext()) {
            Ingredient element = iterator.next();
            if (element.getId().equals(id)) {
                iterator.remove();
            }
        }

        this.ingredientRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
