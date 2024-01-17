package com.recipe.recipestore.ingredient;

import jakarta.persistence.*;

@Entity
@Table
public class Ingredient {

    @Id
    @SequenceGenerator(name = "ingredient_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_seq")
    private Long ingId;
    private Integer ingredientWeight;

    public Ingredient() {
    }

    public Ingredient(Long ingId, Integer ingredientWeight) {
        this.ingId = ingId;
        this.ingredientWeight = ingredientWeight;
    }

    public Ingredient(Integer ingredientWeight) {
        this.ingredientWeight = ingredientWeight;
    }

    public Long getIngId() {
        return ingId;
    }

    public void setIngId(Long ingId) {
        this.ingId = ingId;
    }

    public Integer getIngredientWeight() {
        return ingredientWeight;
    }

    public void setIngredientWeight(Integer ingredientWeight) {
        this.ingredientWeight = ingredientWeight;
    }
}
