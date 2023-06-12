package manas.dto.menuItem.response;

import java.util.List;

public record MenuItemPagination(
        List<MenuItemResponse> menuItems,
        int currentPage,
        int pageSize
) {
}
