package manas.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import manas.dto.SimpleResponse;
import manas.dto.category.request.CategoryRequest;
import manas.dto.category.response.CategoriesResponse;
import manas.dto.category.response.CategoryResponse;
import manas.dto.pagination.PaginationResponse;
import manas.entities.Category;
import manas.exception.ExistsException;
import manas.exception.NotFoundException;
import manas.repository.CategoryRepository;
import manas.repository.SubCategoryRepository;
import manas.service.CategoryService;

import java.util.List;

@Slf4j
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               SubCategoryRepository subCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public List<CategoriesResponse> getAll() {
        return categoryRepository.getAll();
    }

    @Override
    public SimpleResponse save(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        categoryRepository.save(category);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Category - " + category.getName() + " is saved!")
                .build();
    }

    @Override
    public SimpleResponse update(Long categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " is not found!"));
        category.setName(categoryRequest.name());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Category - " + category.getName() + " is updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ExistsException("Category with id - " + categoryId + " doesn't exists!");
        }
        categoryRepository.deleteById(categoryId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Category with id - " + categoryId + " is deleted")
                .build();
    }

    @Override
    public CategoryResponse categorySubCategories(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException("Category with id - " + categoryId + " is not found!"));
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .subCategory(subCategoryRepository.getAllByCategoryId(categoryId))
                .build();
    }

    @Override
    public PaginationResponse<CategoriesResponse> getCategoryPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("name" ).descending());
        Page<CategoriesResponse> pageCategory = categoryRepository.pagination(pageable);
        return PaginationResponse.<CategoriesResponse>builder()
                .elements(pageCategory.getContent())
                .currentPage(pageable.getPageNumber()+1)
                .totalPage(pageCategory.getTotalPages())
                .build();
    }
}
