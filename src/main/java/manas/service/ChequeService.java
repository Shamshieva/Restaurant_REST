package manas.service;

import org.springframework.data.domain.PageRequest;
import manas.dto.SimpleResponse;
import manas.dto.cheque.request.ChequeRequest;
import manas.dto.cheque.response.ChequePagination;
import manas.dto.cheque.response.ChequeResponse;
import manas.dto.cheque.response.SumAllChequeOfDay;

import java.time.LocalDate;


public interface ChequeService {
    ChequePagination getAll(Long userId, PageRequest pageRequest);

    SimpleResponse createCheque(Long userId, ChequeRequest chequeRequest);

    SimpleResponse update(Long chequeId, ChequeRequest chequeRequest);

    SimpleResponse delete(Long chequeId);

    SumAllChequeOfDay sumAllChequeOfDay(Long userId, LocalDate date);

    SimpleResponse avgCheque(Long userId, LocalDate date);

    ChequeResponse getById(Long chequeId);
}
