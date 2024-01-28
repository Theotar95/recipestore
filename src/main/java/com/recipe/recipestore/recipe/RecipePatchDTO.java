package com.recipe.recipestore.recipe;

import com.recipe.recipestore.ingredient.IngredientRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipePatchDTO {
    private String name;

    private String description;

    private String image;

    private List<IngredientRequestDTO> ingredients;

}
