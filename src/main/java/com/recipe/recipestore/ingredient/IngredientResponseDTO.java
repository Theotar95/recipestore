package com.recipe.recipestore.ingredient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponseDTO {
    private String name;

    private Integer weight;
}
