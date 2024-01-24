package com.recipe.recipestore.material;

import com.recipe.recipestore.shared.utils.UnitTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialRequestDTO {
    private String name;

    private Integer weight;

    private UnitTypeEnum unit;

    private Integer price;
}
