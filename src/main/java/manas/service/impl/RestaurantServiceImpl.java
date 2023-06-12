package manas.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import manas.dto.SimpleResponse;
import manas.dto.restaurant.request.RestaurantRequest;
import manas.dto.restaurant.response.RestaurantResponse;
import manas.entities.Restaurant;
import manas.exception.NotFoundException;
import manas.repository.RestaurantRepository;
import manas.service.RestaurantService;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public RestaurantResponse getRestaurant(Long restaurantId) {
        return restaurantRepository.getRestaurantById(restaurantId);
    }

    @Override
    public SimpleResponse save(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setService(restaurantRequest.service());
        restaurantRepository.save(restaurant);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Restaurant - " + restaurant.getName() + " is saved!")
                .build();
    }

    @Override
    public SimpleResponse update(Long restaurantId, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant with id - "
                        + restaurantId + " is not found!"));
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setService(restaurantRequest.service());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Restaurant - " + restaurant.getName() + " is updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .description("Restaurant with id - " + restaurantId + " is not found!")
                    .build();
        }
        restaurantRepository.deleteById(restaurantId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Restaurant with id - " + restaurantId + " is deleted!")
                .build();
    }
}
