package manas.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import manas.dto.menuItem.response.MenuItemResponse;
import manas.entities.MenuItem;

@Component
public class MenuItemToMenuItemResponseConverter implements Converter<MenuItem, MenuItemResponse> {
    @Override
    public MenuItemResponse convert(MenuItem menuItem) {
        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getImage(),
                menuItem.getPrice(),
                menuItem.getDescription(),
                menuItem.isVegetarian()
        );
    }
}
