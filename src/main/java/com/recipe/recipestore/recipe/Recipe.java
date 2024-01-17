package com.recipe.recipestore.recipe;

import com.recipe.recipestore.material.Material;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


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
    private Long recId;
    @Column(unique = true, nullable = false)
    private String recName;
    private String description;
    private String image;

    @ManyToMany(mappedBy = "recipe")
    public Set<Material> material;

//    @ManyToMany
//    @JoinTable(
//            name = "material_like",
//            joinColumns = @JoinColumn( name = "rec_id", referencedColumnName = "recId"),
//            inverseJoinColumns = @JoinColumn (name = "mat_id", referencedColumnName = "matId"))
//    public Set<Material> material;

}
