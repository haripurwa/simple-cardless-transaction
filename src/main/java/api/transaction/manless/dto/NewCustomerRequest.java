package api.transaction.manless.dto;

import api.transaction.manless.util.Wording;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ToString
public class NewCustomerRequest {
    @NotBlank(message = Wording.Msg.STR_CANNOT_NULL_EMPTY)
    @Pattern(regexp = "^[A-Za-z]+$", message = Wording.Msg.STR_ONLY_AlPHABET)
    private String name;
    @NotBlank(message = Wording.Msg.STR_CANNOT_NULL_EMPTY)
    private String mobileNo;
    @NotBlank(message = Wording.Msg.STR_CANNOT_NULL_EMPTY)
    @Email(message = Wording.Msg.STR_NOT_VALID)
    private String email;
    @NotBlank(message = Wording.Msg.STR_CANNOT_NULL_EMPTY)
    @Pattern(regexp = "^\\d+$", message = Wording.Msg.STR_ONLY_NUMERIC)
    private String nik;
    @NotBlank(message = Wording.Msg.STR_CANNOT_NULL_EMPTY)
    @Pattern(regexp = "^\\d+$", message = Wording.Msg.STR_ONLY_NUMERIC)
    private String accountNo;
    @NotBlank(message = Wording.Msg.STR_CANNOT_NULL_EMPTY)
    private String gender;
    @NotBlank(message = Wording.Msg.STR_CANNOT_NULL_EMPTY)
    private String password;
}
