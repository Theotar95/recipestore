package com.recipe.recipestore.recipe;

import com.recipe.recipestore.ingredient.IngredientResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponseDTO {
    private String name;

    private String description;

    private String img;

    private List<IngredientResponseDTO> ingredients;
}
