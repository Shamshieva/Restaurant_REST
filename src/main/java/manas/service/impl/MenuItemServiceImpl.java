package manas.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import manas.convert.MenuItemToMenuItemResponseConverter;
import manas.dto.SimpleResponse;
import manas.dto.menuItem.request.MenuItemRequest;
import manas.dto.menuItem.response.MenuItemResponse;
import manas.dto.pagination.PaginationResponse;
import manas.entities.MenuItem;
import manas.entities.Restaurant;
import manas.entities.Subcategory;
import manas.exception.ExistsException;
import manas.exception.NotFoundException;
import manas.repository.MenuItemRepository;
import manas.repository.RestaurantRepository;
import manas.repository.SubCategoryRepository;
import manas.service.MenuItemService;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@Transactional
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final MenuItemToMenuItemResponseConverter menuItemConverter;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository,
                               RestaurantRepository restaurantRepository,
                               SubCategoryRepository subCategoryRepository, MenuItemToMenuItemResponseConverter menuItemConverter) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.menuItemConverter = menuItemConverter;
    }

    @Override
    public List<MenuItemResponse> getAll(Long subCategoryId, String keyWord) {
        List<MenuItemResponse> list = new ArrayList<>();
        if (keyWord == null) {
            list.addAll(menuItemRepository.getAll());
            return list;
        }
        list.addAll(menuItemRepository.globalSearch(keyWord));
        return list;
    }

    @Override
    public SimpleResponse save(Long restaurantId, Long subCategoryId, MenuItemRequest menuItemRequest) {
        if (menuItemRequest.price().intValue() < 0) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .description("Price can't be negative number!")
                    .build();
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());

        Subcategory subcategory = subCategoryRepository.findById(subCategoryId).orElseThrow(
                () -> new NotFoundException("Sub category not found!"));
        menuItem.setSubcategory(subcategory);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new NotFoundException("Restaurant not found!"));
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Menu item - " + menuItem.getName() + " is saved!")
                .build();
    }

    @Override
    public MenuItemResponse getById(Long menuItemId) {
        try {
            return menuItemRepository.getMenuItemById(menuItemId).orElseThrow(() -> {
                log.error("Menu item with id - " + menuItemId + " is not found!");
                throw new NotFoundException("Menu item with id - " + menuItemId + " is not found!");
            });
        } catch (BadCredentialsException e) {
            throw new AccessDeniedException(e.getMessage());
        }
    }

    @Override
    public SimpleResponse update(Long menuItemId, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(
                () -> new NotFoundException("Menu with id - " + menuItemId + " is not found!"));
        if (menuItemRequest.price().intValue() < 0) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .description("Price can't be negative number!")
                    .build();
        }
        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Menu item - " + menuItem.getName() + " is updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long menuItemId) {
        if (!menuItemRepository.existsById(menuItemId)) {
            throw new ExistsException("Menu item with id - " + menuItemId + " doesn't exists!");
        }
        menuItemRepository.deleteById(menuItemId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Menu item with id - " + menuItemId + " is deleted!")
                .build();
    }

    @Override
    public List<MenuItemResponse> sort(String ascOrDesc) {
        if (ascOrDesc.equals("desc")) {
            return menuItemRepository.getAllByOrderByPriceDesc();
        } else {
            return menuItemRepository.getAllByOrderByPriceAsc();
        }
    }

    @Override
    public List<MenuItemResponse> isVegetarian(boolean isTrue) {
        return menuItemRepository.findMenuItemByIsVegetarian(isTrue);
    }

    @Override
    public PaginationResponse<MenuItemResponse> pagination(PageRequest pageRequest) {
        Page<MenuItemResponse> menuItemPage = menuItemRepository.pagination(pageRequest);

        return PaginationResponse.<MenuItemResponse>builder()
                .elements(menuItemPage.getContent())
                .currentPage(pageRequest.getPageNumber() + 1)
                .totalPage(menuItemPage.getTotalPages())
                .build();
    }
}
