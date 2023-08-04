package api.transaction.manless.service;

import api.transaction.manless.dto.NewCustomerRequest;
import api.transaction.manless.dto.MsResponse;

public interface CustomerService {

    MsResponse createCs(NewCustomerRequest request);
}
