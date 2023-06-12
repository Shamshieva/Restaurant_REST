package manas.dto.restaurant.response;

public record RestaurantResponse(
        Long id,
        String name,
        String location,
        String restType,
        int numberOfEmployees,
        int service
) {
}
