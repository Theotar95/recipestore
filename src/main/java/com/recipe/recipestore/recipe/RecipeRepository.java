package com.recipe.recipestore.recipe;

import com.recipe.recipestore.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
   Recipe findRecipeByName(String name);

}