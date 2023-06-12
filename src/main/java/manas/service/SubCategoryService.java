package manas.service;

import manas.dto.SimpleResponse;
import manas.dto.subCategory.request.SubCategoryRequest;
import manas.dto.subCategory.response.SubCategoryResponse;

import java.util.List;
import java.util.Map;

public interface SubCategoryService {
    List<SubCategoryResponse> getAllByCategoryId(Long categoryId);

    SimpleResponse save(Long categoryId, SubCategoryRequest subCategoryRequest);

    SimpleResponse update(Long subCategoryId, SubCategoryRequest subCategoryRequest);

    SimpleResponse delete(Long subCategoryId);

    Map<String, SubCategoryResponse> groupByCategory(String categoryId);

    SubCategoryResponse findById(Long subCategoryId);
}
