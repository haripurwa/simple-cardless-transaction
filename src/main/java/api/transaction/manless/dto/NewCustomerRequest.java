package api.transaction.manless.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NewCustomerRequest {
    private String name;
    private String mobileNo;
    private String email;
    private String nik;
    private String gender;
    private String password;
}
