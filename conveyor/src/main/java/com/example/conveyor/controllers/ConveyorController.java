package com.example.conveyor.controllers;

import com.example.conveyor.dto.CreditDTO;
import com.example.conveyor.dto.LoanApplicationRequestDTO;
import com.example.conveyor.dto.LoanOfferDTO;
import com.example.conveyor.dto.ScoringDataDTO;
import com.example.conveyor.exceptions.ScoreDenyedException;
import com.example.conveyor.service.ConveyorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/conveyor")
@Tag(name = "Контроллер кредитного конвейера",
        description = "Принимается заявка от пользователя для предварительного и полного расчета кредита")
public class ConveyorController {

    private final ConveyorService conveyorService;

    @Autowired
    public ConveyorController(ConveyorService conveyorService) {
        this.conveyorService = conveyorService;
    }

    @Operation(
            summary = "Принимается предварительная заявка на кредит",
            description = "Данный инструмент предоставляет возможность оценить предварительную стоимость кредита " +
                    "Затем он предоставляет пользователю четыре варианта, " +
                    "упорядоченных от наихудшего (с наивысшей процентной ставкой) " +
                    "к наилучшему."
    )
    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDTO>> getOffers(@Valid @RequestBody LoanApplicationRequestDTO requestDTO) {
        log.info(" Method /conveyor/offers  - Request {}", requestDTO.toString());
        try {
            conveyorService.preScoring(requestDTO);
            List<LoanOfferDTO> result = conveyorService.getOffers(requestDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.info("Form Filling error");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Принимается полная кредитная заявка  для точного определения суммы займа",
            description = "Позволяет расчитать итоговую ставку, полную стомость кредита, подготовить график платежей " +
                    "на основе полной информации о заемщике."
    )
    @PostMapping("/calculation")
    public ResponseEntity<CreditDTO> calculationCredit(@RequestBody ScoringDataDTO scoringDataDTO) {
        log.info(" Method /conveyor/calculation  - Request {}", scoringDataDTO.toString());
        try {
            CreditDTO creditDTO = conveyorService.calculationCredit(scoringDataDTO);
            return new ResponseEntity<>(creditDTO, HttpStatus.OK);
        } catch (ScoreDenyedException ex) {
            log.info(ex.getMessage());
            return ResponseEntity.unprocessableEntity().header("error", ex.getMessage()).build();
        }
    }
}
