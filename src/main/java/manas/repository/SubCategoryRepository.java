package manas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import manas.dto.subCategory.response.SubCategoryResponse;
import manas.entities.Subcategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<Subcategory, Long> {

    @Query("select new manas.dto.subCategory.response.SubCategoryResponse(s.id, s.name)" +
            " from Subcategory s where s.category.id = ?1 order by s.name")
    List<SubCategoryResponse> getAllByCategoryId(Long categoryId);

    Optional<SubCategoryResponse> getSubcategoryById(Long subCategoryId);
}