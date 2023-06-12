package manas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import manas.dto.cheque.response.ChequeResponse;
import manas.dto.cheque.response.MenuItemForCheque;
import manas.entities.Cheque;


import java.util.List;
import java.util.Optional;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {

    @Query("select new manas.dto.cheque.response.ChequeResponse(c.id, c.createdAt," +
            " concat(c.user.firstName,' ',c.user.lastName), sum(m.price), m.restaurant.service)" +
            " from Cheque c join c.menuItems m where c.user.id = ?1 group by c.id, c.createdAt," +
            " c.user.firstName, c.user.lastName, m.restaurant.service")
    Page<ChequeResponse> getAllChequeByUserId (Long userId, PageRequest pageRequest);

    @Query("select new manas.dto.cheque.response.MenuItemForCheque(m.id, m.name, m.price, " +
            "count(m)) from MenuItem m join m.cheques c where c.id = ?1 group by m.id, m.name, m.price")
    List<MenuItemForCheque> getMeals(Long chequeId);

    @Query("select new manas.dto.cheque.response.ChequeResponse(c.id, c.createdAt," +
            " concat(c.user.firstName,' ',c.user.lastName), sum(m.price), m.restaurant.service)" +
            " from Cheque c join c.menuItems m where c.id = ?1 group by c.id, c.createdAt," +
            " c.user.firstName, c.user.lastName, m.restaurant.service")
    Optional<ChequeResponse> getChequeById(Long chequeId);

}