package api.transaction.manless.service;

import api.transaction.manless.dto.NewCustomerRequest;
import api.transaction.manless.dto.MsResponse;
import api.transaction.manless.model.Customer;
import api.transaction.manless.repository.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private final MsResponse response;
    private final CustomerRepo customerRepo;

    public CustomerServiceImpl(MsResponse response, CustomerRepo customerRepo) {
        this.response = response;
        this.customerRepo = customerRepo;
    }

    private String serviceCode = "01";

    @Override
    public MsResponse createCs(NewCustomerRequest request) {
        try {
            Optional<Customer> byMobileNo = customerRepo.findByMobileNo(request.getMobileNo());
            if (byMobileNo.isPresent()) {
                response.setHttpCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.setResponseCode(serviceCode + HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.setResponseStatus("Gagal");
                response.setResponseMessage("mobileNo already exist");
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

    private void saveNewCustomer(NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setMobileNo(request.getMobileNo());
        customer.setNik(request.getNik());
        customer.setEmail(request.getEmail());
        customer.setGender(request.getGender());
        customer.setPassword(request.getPassword());
        customerRepo.save(customer);
        log.info(request.getMobileNo() + " - Saved");
    }


}
