package manas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import manas.dto.category.response.CategoriesResponse;
import manas.entities.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select new manas.dto.category.response.CategoriesResponse(c.id, c.name) from Category c")
    List<CategoriesResponse> getAll();

    @Query("select new manas.dto.category.response.CategoriesResponse(c.id, c.name) from Category c")
    Page<CategoriesResponse> pagination(Pageable pageable);
}