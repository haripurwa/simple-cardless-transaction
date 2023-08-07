package api.transaction.manless.message;

import api.transaction.manless.dto.CardlessRequest;
import api.transaction.manless.model.CardlessTransaction;
import api.transaction.manless.repository.CardlessTransRepo;
import api.transaction.manless.service.MailSenderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionMessageListener implements MessageListener {

    @Value("${partner.url.transaction}")
    private String transactionUrl;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
   private final CardlessTransRepo cardlessTransRepo;
    private final MailSenderService mailSenderService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            log.info("request transaction ....");
            CardlessRequest msg = objectMapper.readValue(message.getBody(), CardlessRequest.class);
            log.info("Channel: {}, Message: {}", new String(message.getChannel()), msg.toString());
            String transactionId = msg.getId();
            String custMail = msg.getEmail();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.add("X-API-Key", "apiKey");
            HttpEntity<?> httpEntity = new HttpEntity<>(msg, httpHeaders);
            log.info("HttpEntity to partner: {}", httpEntity.getBody());
            ResponseEntity<String> response = restTemplate.exchange(
                    transactionUrl,
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            log.info("Response Status From Partner: {}", response.getStatusCode());

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                Optional<CardlessTransaction> cardlessTransRepoById = cardlessTransRepo.findById(UUID.fromString(transactionId));
                cardlessTransRepoById.get().setLocation(msg.getLocation());
                cardlessTransRepo.save(cardlessTransRepoById.get());
                mailSenderService.sendMailNotification(custMail, "Penarikan tunai berhasil transaksi sebesar  "+msg.getDebetAmount()+" success", "transaction Successfully");
                log.info("transaction completed: {}", cardlessTransRepoById.get().getId());

                // callback to client
            } else {
                log.info("Response error from partner : {}", response.getStatusCode());
                // callback to client
            }
        } catch (IOException e) {
            log.error("Couldn't convert json", e);
        }
    }

}
