package com.recipe.recipestore.ingredient;

import com.recipe.recipestore.material.Material;
import com.recipe.recipestore.recipe.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    @Id
    @SequenceGenerator(name = "ingredient_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_seq")
    private Long id;

    private Integer ingredientWeight;

    @ManyToOne
    @JoinColumn(name= "recId", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name= "matId", nullable = false)
    private Material material;
}
