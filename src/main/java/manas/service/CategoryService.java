package manas.service;

import manas.dto.SimpleResponse;
import manas.dto.category.request.CategoryRequest;
import manas.dto.category.response.CategoriesResponse;
import manas.dto.category.response.CategoryResponse;
import manas.dto.pagination.PaginationResponse;

import java.util.List;

public interface CategoryService {
    List<CategoriesResponse> getAll();

    SimpleResponse save(CategoryRequest categoryRequest);

    SimpleResponse update(Long categoryId, CategoryRequest categoryRequest);

    SimpleResponse delete(Long categoryId);

    CategoryResponse categorySubCategories(Long categoryId);

    PaginationResponse<CategoriesResponse> getCategoryPagination(int page, int size);

}
