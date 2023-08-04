package api.transaction.manless.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class MsResponse {
    @JsonIgnore
    private Integer httpCode;
    private String responseCode;
    private String responseStatus;
    private String responseMessage;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object responseData;
}
