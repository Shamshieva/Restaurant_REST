package manas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import manas.dto.menuItem.response.MenuItemResponse;
import manas.entities.MenuItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    @Query("select distinct new manas.dto.menuItem.response.MenuItemResponse" +
            "(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) " +
            "from MenuItem m left join StopList s on s.menuItem.id = m.id where s.id is null or s.date <> current_date ")
    List<MenuItemResponse> getAll();

    Optional<MenuItemResponse> getMenuItemById(Long menuItemId);

    List<MenuItemResponse> getMenuItemByStopListsNull();

    @Query("select new manas.dto.menuItem.response.MenuItemResponse" +
            "(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) " +
            "from MenuItem m where m.name ilike concat('%', :keyWord, '%') or m.description " +
            "ilike concat('%', :keyWord, '%') or m.subcategory.name ilike concat('%', :keyWord, '%')" +
            " or m.subcategory.category.name ilike concat('%', :keyWord, '%')")
    List<MenuItemResponse> globalSearch(String keyWord);

    @Query("select new manas.dto.menuItem.response.MenuItemResponse" +
            "(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) " +
            "from MenuItem m where m.stopLists = null and (m.name ilike concat('%', :keyWord, '%')" +
            " or m.subcategory.name ilike concat('%', :keyWord, '%') or m.subcategory.category.name " +
            "ilike concat('%', :keyWord, '%'))")
    List<MenuItemResponse> globalSearchStopListsNull(String keyWord);

    List<MenuItemResponse> getAllByOrderByPriceDesc();

    List<MenuItemResponse> getAllByOrderByPriceAsc();

    List<MenuItemResponse> findMenuItemByIsVegetarian(boolean isTrue);
    @Query("select new manas.dto.menuItem.response.MenuItemResponse" +
            "(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) " +
            "from MenuItem m")
    Page<MenuItemResponse> pagination(Pageable pageable);
}