package com.recipe.recipestore.recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponseAllDTO {
    private String name;

    private String description;

    private String image;
}
