package manas.service;

import manas.dto.SimpleResponse;
import manas.dto.stopList.request.StopListRequest;
import manas.dto.stopList.response.StopListResponse;

import java.util.List;

public interface StopListService {
    List<StopListResponse> getStopLists(Long menuItemId);

    SimpleResponse create(Long menuItemId, StopListRequest stopListRequest);

    SimpleResponse update(Long menuItemId, Long stopListId, StopListRequest stopListRequest);

    SimpleResponse delete(Long menuItemId, Long stopListId);

    StopListResponse findById(Long stopListId);

}
