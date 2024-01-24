package com.recipe.recipestore.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

 @Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
