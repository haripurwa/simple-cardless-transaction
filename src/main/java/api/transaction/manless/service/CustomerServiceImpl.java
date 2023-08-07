package api.transaction.manless.service;

import api.transaction.manless.dto.*;
import api.transaction.manless.model.AccountCustomer;
import api.transaction.manless.model.Customer;
import api.transaction.manless.repository.AccountCustomerRepo;
import api.transaction.manless.repository.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    @Value("${balanceCore.base.url}")
    private String apiUrl;
    private final MsResponse response;
    private final CustomerRepo customerRepo;

    private  final AccountCustomerRepo accountCustomerRepo;

    public CustomerServiceImpl(MsResponse response, CustomerRepo customerRepo,AccountCustomerRepo accountCustomerRepo) {
        this.response = response;
        this.customerRepo = customerRepo;
        this.accountCustomerRepo=accountCustomerRepo;
    }

    private String serviceCode = "01";

    @Override
    public MsResponse createCs(NewCustomerRequest request) {
        try {
            Optional<Customer> byNikAndAccountNo = customerRepo.findByNikAndAccountNo(request.getNik(), request.getAccountNo());
            if (byNikAndAccountNo.isPresent()) {
                response.setHttpCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.setResponseCode(serviceCode + HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.setResponseStatus("Gagal");
                response.setResponseMessage("account already exist");
                return response;
            }
            saveNewCustomer(request);
            response.setHttpCode(HttpStatus.OK.value());
            response.setResponseCode(serviceCode + HttpStatus.OK.value());
            response.setResponseStatus("Successfully");
            response.setResponseMessage("customer saved " + request.getMobileNo());
            return response;
        } catch (Exception e) {
            log.error("error : " + e.getMessage());
            return new MsResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), serviceCode + HttpStatus.INTERNAL_SERVER_ERROR, "unsuccessfully", "error:" + e.getMessage(), request.getMobileNo());
        }
    }

    @Override
    public MsResponse inquiry(InquiryRequest request) {
        List<Customer> byNik = customerRepo.findByNik(request.getNik());
        if (byNik.isEmpty()) {
            response.setHttpCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            response.setResponseCode(serviceCode + HttpStatus.UNPROCESSABLE_ENTITY.value());
            response.setResponseStatus("Gagal");
            response.setResponseMessage("data tidak ditemukan");
            return response;
        }
        List<Customer> cs = byNik.stream()
                .filter(a -> a.isActive())
                .collect(Collectors.toList());

        if(cs.size()<=0){
            response.setResponseData("Account not active");
        }else {
            response.setResponseData(cs);
        }
        response.setHttpCode(HttpStatus.OK.value());
        response.setResponseCode(serviceCode + HttpStatus.OK.value());
        response.setResponseStatus("Successfully");
        response.setResponseMessage("have account");
        return response;
    }

    @Override
    public MsResponse balance(BalanceRequest request) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            CoreBalanceResponse balanceResponse = restTemplate.getForObject(apiUrl, CoreBalanceResponse.class);

            // Process the response
            if (balanceResponse.getResponseCode().equals("200")) {
                AccountCustomer accountCustomer = new AccountCustomer();
                accountCustomer.setSaldo(balanceResponse.getResponseMessage());
                accountCustomerRepo.save(accountCustomer);
                log.info(request.getAccountNo() + " - balance");
            }
            response.setHttpCode(HttpStatus.OK.value());
            response.setResponseCode(serviceCode + HttpStatus.OK.value());
            response.setResponseStatus("Successfully");
            response.setResponseMessage(request.getAccountNo()+" balance");
            return response;
        }catch (Exception e){
            return new MsResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), serviceCode + HttpStatus.INTERNAL_SERVER_ERROR, "unsuccessfully", "error:" + e.getMessage(), request.getAccountNo());
        }
    }

    private void saveNewCustomer(NewCustomerRequest request) {
        //save data customer
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setMobileNo(request.getMobileNo());
        customer.setNik(request.getNik());
        customer.setAccountNo(request.getAccountNo());
        customer.setEmail(request.getEmail());
        customer.setGender(request.getGender());
        customerRepo.save(customer);
        //set account customer
        AccountCustomer accountCustomer = new AccountCustomer();
        accountCustomer.setAccountNo(request.getAccountNo());
        accountCustomerRepo.save(accountCustomer);
        log.info(request.getAccountNo() + " - Saved");

    }
}
