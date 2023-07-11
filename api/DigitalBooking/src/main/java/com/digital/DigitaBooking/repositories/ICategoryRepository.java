package com.digital.DigitaBooking.repositories;

import com.digital.DigitaBooking.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value="SELECT cat.* FROM category cat WHERE cat.category_name LIKE %:category_name%", nativeQuery = true)
    Category searchCategoryByName(@Param("category_name") String category_name);

    // La consulta buscará todas las categorías cuyo nombre contenga la palabra o parte de la palabra
    // introducida.

}