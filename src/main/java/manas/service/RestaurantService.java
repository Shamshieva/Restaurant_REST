package manas.service;

import manas.dto.SimpleResponse;
import manas.dto.restaurant.request.RestaurantRequest;
import manas.dto.restaurant.response.RestaurantResponse;

public interface RestaurantService {

    RestaurantResponse getRestaurant(Long restaurantId);

    SimpleResponse save(RestaurantRequest restaurantRequest);

    SimpleResponse update(Long restaurantId, RestaurantRequest restaurantRequest);

    SimpleResponse delete(Long restaurantId);
}
