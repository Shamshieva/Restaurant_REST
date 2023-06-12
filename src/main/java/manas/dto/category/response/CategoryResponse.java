package manas.dto.category.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import manas.dto.subCategory.response.SubCategoryResponse;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor

public class CategoryResponse {
    private Long id;
    private String name;
    private List<SubCategoryResponse> subCategory;

    public CategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
