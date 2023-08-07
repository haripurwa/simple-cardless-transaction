package api.transaction.manless.dto;

import api.transaction.manless.util.Wording;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InquiryRequest {
    @NotBlank(message = Wording.Msg.STR_CANNOT_NULL_EMPTY)
    private String nik;
}
