package api.transaction.manless.controller;

import api.transaction.manless.dto.MsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ResponseControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MsResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = "invalid request params";
        String details = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .reduce("", (a, b) -> a + ", " + b);

        MsResponse response = new MsResponse(HttpStatus.BAD_REQUEST.value(), "00000", "Gagal",message+details,null);
        log.error("bad request = " + details);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getHttpCode()));
    }
}
