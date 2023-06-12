package manas.service;

import manas.dto.SimpleResponse;
import manas.dto.user.request.AcceptOrRejectRequest;
import manas.dto.user.request.RegisterRequest;
import manas.dto.user.request.UserRequest;
import manas.dto.user.response.*;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();

    SimpleResponse register(RegisterRequest registerRequest);

    UserTokenResponse authenticate(UserRequest userRequest);

    List<UserResponse> getApplications();

    SimpleResponse acceptResponse(Long restaurantId, AcceptOrRejectRequest acceptOrRejectRequest);

    SimpleResponse updateUser(Long userId, RegisterRequest request);

    SimpleResponse deleteUser(Long userId);

    UserResponse findById(Long userId);
}
