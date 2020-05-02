package ayushproject.ayushecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidFieldValueException extends RuntimeException {
    public InvalidFieldValueException(String errorMessage){super(errorMessage);}
}
