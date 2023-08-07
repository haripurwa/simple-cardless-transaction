package api.transaction.manless.dto;

import lombok.Data;

@Data
public class CardlessRequest {
    private String id;
    private String accountNo;
    private String nik;
    private String mobileNo;
    private String email;
    private String debetAmount;
    private String location;
}
