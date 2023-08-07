package api.transaction.manless.controller;


import api.transaction.manless.dto.CardlessRequest;
import api.transaction.manless.dto.ListTransactionRequest;
import api.transaction.manless.dto.MsResponse;
import api.transaction.manless.service.CardlessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/cardless")
public class CardlessController {

    private final CardlessService cardlessService;

    public CardlessController(CardlessService cardlessService) {
        this.cardlessService = cardlessService;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@Valid @RequestBody CardlessRequest request) {
        MsResponse response = cardlessService.create(request);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getHttpCode()));
    }

    @GetMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listTransaction(@Valid @RequestBody ListTransactionRequest request) {
        MsResponse response = cardlessService.listTransaction(request);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getHttpCode()));
    }
}
