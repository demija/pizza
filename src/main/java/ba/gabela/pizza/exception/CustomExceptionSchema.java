package ba.gabela.pizza.exception;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CustomExceptionSchema {
    private Integer status;
    private String message;
    private String details;
    private LocalDateTime timestamp;

    public CustomExceptionSchema(Integer status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
}
