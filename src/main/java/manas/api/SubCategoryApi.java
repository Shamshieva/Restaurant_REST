package manas.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import manas.dto.SimpleResponse;
import manas.dto.subCategory.request.SubCategoryRequest;
import manas.dto.subCategory.response.SubCategoryResponse;
import manas.service.SubCategoryService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/{categoryId}/subCategories")
public class SubCategoryApi {
    private final SubCategoryService subCategoryService;

    public SubCategoryApi(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<SubCategoryResponse> getAll(@PathVariable Long categoryId){
        return subCategoryService.getAllByCategoryId(categoryId);
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse save(@PathVariable Long categoryId,
                        @RequestBody SubCategoryRequest subCategoryRequest){
        return subCategoryService.save(categoryId, subCategoryRequest);
    }

    @GetMapping("/{subCategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    SubCategoryResponse findById(@PathVariable Long categoryId,
                                 @PathVariable Long subCategoryId){
        return subCategoryService.findById(subCategoryId);
    }

    @PutMapping("/{subCategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse update(@PathVariable Long categoryId,
                          @PathVariable Long subCategoryId,
                          @RequestBody SubCategoryRequest subCategoryRequest){
        return subCategoryService.update(subCategoryId, subCategoryRequest);
    }
    @DeleteMapping("/{subCategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse delete(@PathVariable Long categoryId,
                          @PathVariable Long subCategoryId){
        return subCategoryService.delete(subCategoryId);
    }
    @GetMapping("/groupingByCategory")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    Map<String, SubCategoryResponse> groupByCategory(@PathVariable String categoryId){
        return subCategoryService.groupByCategory(categoryId);
    }
}