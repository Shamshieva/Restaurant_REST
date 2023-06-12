package manas.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import manas.dto.SimpleResponse;
import manas.dto.cheque.request.ChequeRequest;
import manas.dto.cheque.response.ChequePagination;
import manas.dto.cheque.response.ChequeResponse;
import manas.dto.cheque.response.SumAllChequeOfDay;
import manas.entities.Cheque;
import manas.entities.MenuItem;
import manas.entities.User;
import manas.exception.ExistsException;
import manas.exception.NotFoundException;
import manas.repository.ChequeRepository;
import manas.repository.MenuItemRepository;
import manas.repository.UserRepository;
import manas.service.ChequeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;

    public ChequeServiceImpl(ChequeRepository chequeRepository,
                             MenuItemRepository menuItemRepository,
                             UserRepository userRepository) {
        this.chequeRepository = chequeRepository;
        this.menuItemRepository = menuItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ChequePagination getAll(Long userId, PageRequest pageRequest) {
        Page<ChequeResponse> chequesPage = chequeRepository.getAllChequeByUserId(userId, pageRequest);
        List<ChequeResponse> cheques = chequesPage.getContent();
        for (ChequeResponse c : cheques) {
            BigDecimal total = c.getAveragePrice()
                    .multiply(new BigDecimal(c.getService()))
                    .divide(new BigDecimal(100))
                    .add(c.getAveragePrice());
            c.setGrandTotal(total);
            c.setMeals(chequeRepository.getMeals(c.getId()));
        }
        return ChequePagination.builder()
                .cheques(cheques)
                .currentPage(chequesPage.getPageable().getPageNumber() + 1)
                .totalPages(chequesPage.getTotalPages())
                .build();
    }

    @Override
    public SimpleResponse createCheque(Long userId, ChequeRequest chequeRequest) {
        List<MenuItem> menuItems = new ArrayList<>();
        Cheque cheque = new Cheque();
        cheque.setCreatedAt(LocalDate.now());
        for (Long mealId : chequeRequest.mealsId()) {
            MenuItem menuItem = menuItemRepository.findById(mealId).orElseThrow(
                    () -> new NotFoundException("Meal with id - " + mealId + " is not found!"));
            menuItems.add(menuItem);
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id - " + userId + " not found!"));
        cheque.setUser(user);

        for (MenuItem menuItem : menuItems) {
            menuItem.getCheques().add(cheque);
        }
        chequeRepository.save(cheque);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Your check has been accepted!")
                .build();
    }

    @Override
    public ChequeResponse getById(Long chequeId) {
        ChequeResponse cheque = chequeRepository.getChequeById(chequeId).orElseThrow(() -> {
            log.error("Cheque with id: " + chequeId + " is not found!");
            throw new NotFoundException("Cheque with id: " + chequeId + " is not found!");
        });

        cheque.setMeals(chequeRepository.getMeals(chequeId));
        BigDecimal total = cheque.getAveragePrice()
                .multiply(new BigDecimal(cheque.getService()))
                .divide(new BigDecimal(100)).add(cheque.getAveragePrice());
        cheque.setGrandTotal(total);

        return cheque;
    }

    @Override
    public SimpleResponse update(Long chequeId, ChequeRequest chequeRequest) {
        List<MenuItem> menuItems;
        Cheque cheque = chequeRepository.findById(chequeId).orElseThrow(
                () -> new NotFoundException("Cheque with id - " + chequeId + " is not found!"));
        menuItems = chequeRequest.mealsId().stream().map(mealId -> menuItemRepository.findById(mealId).orElseThrow(
                () -> new NotFoundException("Meal with id - " + mealId + " is not found!"))).collect(Collectors.toList());

        for (MenuItem menuItem : cheque.getMenuItems()) {
            menuItem.getCheques().remove(cheque);
        }
        for (MenuItem menuItem : menuItems) {
            menuItem.getCheques().add(cheque);
        }
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Your check has been updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long chequeId) {
        if (!chequeRepository.existsById(chequeId)) {
            throw new ExistsException("Cheque with id - " + chequeId + " doesn't exists!");
        }
        Cheque cheque = chequeRepository.findById(chequeId).orElseThrow(() ->
                new NotFoundException("Cheque with id - " + chequeId + " doesn't exists!"));
        cheque.getMenuItems().forEach(menuItem -> menuItem.getCheques().remove(cheque));
        chequeRepository.deleteById(chequeId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Cheque with id - " + chequeId + " is deleted!")
                .build();
    }

    @Override
    public SumAllChequeOfDay sumAllChequeOfDay(Long userId, LocalDate date) {
        List<Cheque> cheques = chequeRepository.findAll();
        SumAllChequeOfDay sumAllChequeOfDay = new SumAllChequeOfDay();
        Long count = 0L;
        BigDecimal totalSum = new BigDecimal(0);
        int ser = 1;
        for (Cheque cheque : cheques) {
            if (cheque.getUser().getId().equals(userId) && cheque.getCreatedAt().equals(date)) {
                sumAllChequeOfDay.setWaiter(cheque.getUser().getFirstName() + " " + cheque.getUser().getLastName());
                sumAllChequeOfDay.setDate(cheque.getCreatedAt());
                count++;
                for (MenuItem menuItem : cheque.getMenuItems()) {
                    totalSum = totalSum.add(menuItem.getPrice());
                    ser = menuItem.getRestaurant().getService();
                }
            }

        }
        BigDecimal service = totalSum.multiply(new BigDecimal(ser)).divide(new BigDecimal(100));
        sumAllChequeOfDay.setCounterOfCheque(count);
        sumAllChequeOfDay.setTotalSumma(totalSum.add(service));
        return sumAllChequeOfDay;
    }

    @Override
    public SimpleResponse avgCheque(Long userId, LocalDate date) {
        int total = 0;
        int count = 0;
        int ser = 1;
        for (Cheque cheque : chequeRepository.findAll()) {
            if (cheque.getCreatedAt().equals(date)) {
                for (MenuItem menuItem : cheque.getMenuItems()) {
                    total += menuItem.getPrice().intValue();
                    ser = menuItem.getRestaurant().getService();
                }
                count++;
            }
        }
        total += total * ser / 100;
        int avg = total / count;
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .description("Average restaurant cheque - " + avg)
                .build();
    }
}
