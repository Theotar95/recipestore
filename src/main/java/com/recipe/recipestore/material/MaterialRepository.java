package com.recipe.recipestore.material;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    @Query("SELECT matName FROM Material matName WHERE matName.matName = ?1")
    Optional<Material> findMaterialByName(String matName);
}
