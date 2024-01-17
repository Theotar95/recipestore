package com.recipe.recipestore.material;

import com.recipe.recipestore.recipe.Recipe;
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
public class Material {
    @Id
    @SequenceGenerator(name = "material_seq", sequenceName = "material_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_seq")
    private Long matId;
    @Column(unique = true, nullable = false)
    private String matName;
    private Integer weight;
    private String unit;
    private Integer price;

//    @ManyToMany(mappedBy = "material")
//    public Set<Recipe> recName;

    @ManyToMany
    @JoinTable (
            name= "recipe_like",
            joinColumns = @JoinColumn(name = "mat_id", referencedColumnName = "matId"),
            inverseJoinColumns = @JoinColumn (name = "rec_id", referencedColumnName = "recId"))
    public Set<Recipe> recipe;

}
