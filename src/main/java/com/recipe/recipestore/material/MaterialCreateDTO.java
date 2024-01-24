package com.recipe.recipestore.material;

import com.recipe.recipestore.shared.utils.UnitTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialCreateDTO {
    @NotNull (message = "The name is required")
    private String name;

    @NotNull (message = "The weight is required")
    private Integer weight;

    private UnitTypeEnum unit;
    @NotNull (message = "The price is required")
    private Integer price;

}
