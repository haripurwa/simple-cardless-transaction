package api.transaction.manless.service;

import api.transaction.manless.dto.BalanceRequest;
import api.transaction.manless.dto.InquiryRequest;
import api.transaction.manless.dto.NewCustomerRequest;
import api.transaction.manless.dto.MsResponse;

public interface CustomerService {

    MsResponse createCs(NewCustomerRequest request);
    MsResponse inquiry(InquiryRequest request);
    MsResponse balance(BalanceRequest request);
}
