package com.recipe.recipestore.recipe;

import com.recipe.recipestore.ingredient.IngredientRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequestDTO {

    @NotNull (message = "The name is required")
    private String name;

    @NotNull (message = "The description is required")
    private String description;

    private String image;

    private List<IngredientRequestDTO> ingredients;
}