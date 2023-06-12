package manas.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import manas.dto.SimpleResponse;
import manas.dto.stopList.request.StopListRequest;
import manas.dto.stopList.response.StopListResponse;
import manas.service.StopListService;

import java.util.List;

@RestController
@RequestMapping("/api/{menuItemId}/stopLists")
public class StopListApi {
    private final StopListService stopListService;

    public StopListApi(StopListService stopListService) {
        this.stopListService = stopListService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    List<StopListResponse> getStopLists(@PathVariable Long menuItemId) {
        return stopListService.getStopLists(menuItemId);
    }

    @GetMapping("/{stopListId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    StopListResponse findById(@PathVariable Long menuItemId,
                              @PathVariable Long stopListId) {
        return stopListService.findById(stopListId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse create(@PathVariable Long menuItemId,
                          @RequestBody StopListRequest stopListRequest) {
        return stopListService.create(menuItemId, stopListRequest);
    }

    @PutMapping("/{stopListId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse update(@PathVariable Long menuItemId,
                          @PathVariable Long stopListId,
                          @RequestBody StopListRequest stopListRequest) {
        return stopListService.update(menuItemId, stopListId, stopListRequest);
    }
    @DeleteMapping("/{stopListId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse delete(@PathVariable Long menuItemId,
                          @PathVariable Long stopListId){
        return stopListService.delete(menuItemId, stopListId);
    }
}
