package manas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import manas.dto.stopList.response.StopListResponse;
import manas.entities.StopList;

import java.util.List;
import java.util.Optional;

@Repository
public interface StopListRepository extends JpaRepository<StopList, Long> {

    List<StopListResponse> findAllByMenuItemId(Long menuItemId);
    Optional<StopListResponse> getStopListById(Long stopListId);
}