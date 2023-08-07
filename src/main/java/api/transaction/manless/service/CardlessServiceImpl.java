package api.transaction.manless.service;

import api.transaction.manless.dto.CardlessRequest;
import api.transaction.manless.dto.MsResponse;
import api.transaction.manless.message.TransactionMessagePublisher;
import api.transaction.manless.model.AccountCustomer;
import api.transaction.manless.model.CardlessTransaction;
import api.transaction.manless.model.Customer;
import api.transaction.manless.model.DataParam;
import api.transaction.manless.repository.AccountCustomerRepo;
import api.transaction.manless.repository.CardlessTransRepo;
import api.transaction.manless.repository.CustomerRepo;
import api.transaction.manless.repository.DataParamRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class CardlessServiceImpl implements CardlessService {
    private String serviceCode = "02";
    private final ObjectMapper objectMapper;
    private final CustomerRepo customerRepo;
    private final CardlessTransRepo cardlessTransRepo;
    private final AccountCustomerRepo accountCustomerRepo;
    private final DataParamRepo dataParamRepo;
    private final MsResponse response;

    private final TransactionMessagePublisher messagePublisher;

    public CardlessServiceImpl(CustomerRepo customerRepo, CardlessTransRepo cardlessTransRepo, MsResponse response, ObjectMapper objectMapper, TransactionMessagePublisher messagePublisher, AccountCustomerRepo accountCustomerRepo, DataParamRepo dataParamRepo) {
        this.customerRepo = customerRepo;
        this.cardlessTransRepo = cardlessTransRepo;
        this.response = response;
        this.objectMapper = objectMapper;
        this.messagePublisher = messagePublisher;
        this.accountCustomerRepo = accountCustomerRepo;
        this.dataParamRepo = dataParamRepo;
    }


    @Override
    public MsResponse create(CardlessRequest request) {
        try {
            Optional<Customer> byMobileNo = customerRepo.findByNikAndAccountNo(request.getNik(), request.getAccountNo());
            //cek account valid
            if (byMobileNo.isEmpty()) {
                response.setHttpCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.setResponseCode(serviceCode + HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.setResponseStatus("Gagal");
                response.setResponseMessage("data not found");
                return response;
            }
            //cek limit request
            Optional<DataParam> cardless = dataParamRepo.findByTransaction("cardless");
            double limitTransaction = Double.parseDouble(cardless.get().getLimitTransaction());
            double debetAmount = Double.parseDouble(request.getDebetAmount());

            if (limitTransaction >= debetAmount) {
                response.setHttpCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.setResponseCode(serviceCode + HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.setResponseStatus("Gagal");
                response.setResponseMessage("maximal transaksi " + cardless.get().getLimitTransaction());
                return response;
            }

            //transactional
            CardlessTransaction cardlessTransaction = getCardlessTransaction(request, debetAmount);

            //notification
            String payload = objectMapper.writeValueAsString(cardlessTransaction);
            this.messagePublisher.publish(payload);
            log.info("Successfully publish : {}", payload);

            //response
            response.setHttpCode(HttpStatus.OK.value());
            response.setResponseCode(serviceCode + HttpStatus.OK.value());
            response.setResponseStatus("Successfully");
            response.setResponseMessage(request.getAccountNo() + " balance");
            return response;
        } catch (Exception er) {
            return null;
        }
    }

    @Override
    public MsResponse listTransaction(CardlessRequest request) {
        return null;
    }

    @Transactional
    CardlessTransaction getCardlessTransaction(CardlessRequest request, double debetAmount) {
        //save transaction
        CardlessTransaction cardlessTransaction = new CardlessTransaction();
        cardlessTransaction.setId(UUID.randomUUID());
        cardlessTransaction.setAccountNo(request.getAccountNo());
        cardlessTransaction.setDebetAmount(request.getDebetAmount());
        cardlessTransaction.setDescription("sukses");
        cardlessTransaction.setLocation(request.getLocation());
        cardlessTransRepo.save(cardlessTransaction);

        //update balance
        Optional<AccountCustomer> byAccountNo = accountCustomerRepo.findByAccountNo(request.getAccountNo());
        double saldo = Double.parseDouble(byAccountNo.get().getSaldo());
        byAccountNo.get().setSaldo(String.valueOf(saldo - debetAmount));
        accountCustomerRepo.save(byAccountNo.get());
        return cardlessTransaction;
    }
}
