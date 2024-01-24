package com.recipe.recipestore.recipe;

import com.recipe.recipestore.ingredient.Ingredient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @SequenceGenerator(name = "recipe_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_seq")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    private String image;

    @OneToMany(fetch = FetchType.LAZY,
        cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;



}
