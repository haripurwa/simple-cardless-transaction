package api.transaction.manless.dto;

public interface ListTransactionResponse {
    String getId();
    String getAccountNo();
    String getDebetAmount();
    String getLocation();
    String getCreatedAt();
}
