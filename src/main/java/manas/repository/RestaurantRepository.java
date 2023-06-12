package manas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import manas.dto.restaurant.response.RestaurantResponse;
import manas.entities.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    RestaurantResponse getRestaurantById(Long id);
}