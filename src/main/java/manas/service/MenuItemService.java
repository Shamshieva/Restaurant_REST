package manas.service;

import org.springframework.data.domain.PageRequest;
import manas.dto.SimpleResponse;
import manas.dto.menuItem.request.MenuItemRequest;
import manas.dto.menuItem.response.MenuItemResponse;
import manas.dto.pagination.PaginationResponse;

import java.util.List;

public interface MenuItemService {
    List<MenuItemResponse> getAll(Long subCategoryId, String keyWord);

    SimpleResponse save(Long restaurantId, Long subCategoryId, MenuItemRequest menuItemRequest);

    SimpleResponse update(Long menuItemId, MenuItemRequest menuItemRequest);

    SimpleResponse delete(Long menuItemId);

    List<MenuItemResponse> sort(String ascOrDesc);

    List<MenuItemResponse> isVegetarian(boolean isTrue);

    MenuItemResponse getById(Long menuItemId);

    PaginationResponse<MenuItemResponse> pagination(PageRequest pageRequest);
}
