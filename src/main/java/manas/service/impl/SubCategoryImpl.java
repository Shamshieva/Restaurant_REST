package manas.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import manas.dto.SimpleResponse;
import manas.dto.subCategory.request.SubCategoryRequest;
import manas.dto.subCategory.response.SubCategoryResponse;
import manas.entities.Subcategory;
import manas.exception.ExistsException;
import manas.exception.NotFoundException;
import manas.repository.CategoryRepository;
import manas.repository.SubCategoryRepository;
import manas.service.SubCategoryService;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class SubCategoryImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    public SubCategoryImpl(SubCategoryRepository subCategoryRepository,
                           CategoryRepository categoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<SubCategoryResponse> getAllByCategoryId(Long categoryId) {
        return subCategoryRepository.getAllByCategoryId(categoryId);
    }

    @Override
    public SimpleResponse save(Long categoryId, SubCategoryRequest subCategoryRequest) {
        Subcategory subcategory = new Subcategory();
        subcategory.setCategory(categoryRepository.findById(categoryId).orElseThrow(()-> {
            log.error("Category with id - " + categoryId + " is not found!");
            throw new NotFoundException("Category with id - " + categoryId + " is not found!");
        }));
        subcategory.setName(subCategoryRequest.name());
        subCategoryRepository.save(subcategory);
        log.info("Sub category - " + subcategory.getName() + " is saved!");
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Sub category - " + subcategory.getName() + " is saved!")
                .build();
    }

    @Override
    public SubCategoryResponse findById(Long subCategoryId) {
        return subCategoryRepository.getSubcategoryById(subCategoryId).orElseThrow(()-> {
            log.error("Sub category with id - " + subCategoryId + " is not found!");
            throw new NotFoundException("Sub category with id - " + subCategoryId + " is not found!");
        });
    }

    @Override
    public SimpleResponse update(Long subCategoryId, SubCategoryRequest subCategoryRequest) {
        Subcategory subcategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> {
            log.error("Sub category with id - " + subCategoryId + " is not found!");
            throw new NotFoundException("Sub category with id - " + subCategoryId + " is not found!");
        });
        subcategory.setName(subCategoryRequest.name());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Sub category - " + subcategory.getName() + " is updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long subCategoryId) {
        if (!subCategoryRepository.existsById(subCategoryId)) {
            log.error("Sub category with id - " + subCategoryId + " doesn't exists!");
            throw new ExistsException("Sub category with id - " + subCategoryId + " doesn't exists!");
        }
        subCategoryRepository.deleteById(subCategoryId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Sub category with id - " + subCategoryId + " is deleted!")
                .build();
    }

    @Override
    public Map<String, SubCategoryResponse> groupByCategory(String categoryId) {

        return null;
    }
}
