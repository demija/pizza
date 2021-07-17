package ba.gabela.pizza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        ConstraintViolation<?> violation = ex.getConstraintViolations().iterator().next();

        String field = null;
        for (Path.Node node : violation.getPropertyPath()) {
            field = node.getName();
        }

        CustomExceptionSchema exceptionResponse = new CustomExceptionSchema(HttpStatus.BAD_REQUEST.value(),
                "Invalid input", field + ": " + violation.getMessage());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<Object> handleAllExceptions(CustomException ex) {
        CustomExceptionSchema exceptionResponse =
                new CustomExceptionSchema(ex.getStatus().value(), ex.getMessage(), ex.getDetails());
        return new ResponseEntity(exceptionResponse, ex.getStatus());
    }
}