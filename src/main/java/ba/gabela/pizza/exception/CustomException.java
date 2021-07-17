package ba.gabela.pizza.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
public class CustomException extends RuntimeException {
    private HttpStatus status;
    private String message;
    private String details;
    private LocalDateTime timestamp;

    public CustomException(HttpStatus status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
}
