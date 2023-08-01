package com.example.conveyor.controllers;

import com.example.conveyor.dto.CreditDTO;
import com.example.conveyor.dto.LoanApplicationRequestDTO;
import com.example.conveyor.dto.LoanOfferDTO;
import com.example.conveyor.dto.ScoringDataDTO;
import com.example.conveyor.exceptions.ScoreDenyedException;
import com.example.conveyor.service.ConveyorService;
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
public class ConveyorController {

    private final ConveyorService conveyorService;


    @Autowired
    public ConveyorController(ConveyorService conveyorService) {
        this.conveyorService = conveyorService;
    }


    @PostMapping("/offers")
    public ResponseEntity <List<LoanOfferDTO>> getOffers (@Valid @RequestBody LoanApplicationRequestDTO requestDTO){
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

    @PostMapping("/calculation")
    public ResponseEntity<CreditDTO> calculationCredit (@RequestBody ScoringDataDTO scoringDataDTO){
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
