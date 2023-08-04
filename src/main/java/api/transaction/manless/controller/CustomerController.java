package api.transaction.manless.controller;

import api.transaction.manless.dto.NewCustomerRequest;
import api.transaction.manless.dto.MsResponse;
import api.transaction.manless.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/internal")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/create-customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody NewCustomerRequest request) {
        MsResponse response = customerService.createCs(request);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getHttpCode()));
    }

}
