package com.recipe.recipestore.material;

import com.recipe.recipestore.ingredient.Ingredient;
import com.recipe.recipestore.shared.utils.UnitTypeEnum;
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
public class Material {
    @Id
    @SequenceGenerator(name = "material_seq", sequenceName = "material_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_seq")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private Integer weight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitTypeEnum unit;

    private Integer price;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;
}
