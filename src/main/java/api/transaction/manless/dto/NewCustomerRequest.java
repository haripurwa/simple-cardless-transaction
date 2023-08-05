package api.transaction.manless.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class NewCustomerRequest {
    @NotBlank(message = "cannot be null and empty")
    private String name;
    @NotBlank(message = "cannot be null and empty")
    private String mobileNo;
    @NotBlank(message = "cannot be null and empty")
    private String email;
    @NotBlank(message = "cannot be null and empty")
    private String nik;
    @NotBlank(message = "cannot be null and empty")
    private String gender;
    @NotBlank(message = "cannot be null and empty")
    private String password;
}
