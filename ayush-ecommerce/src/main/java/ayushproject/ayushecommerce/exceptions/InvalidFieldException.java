package ayushproject.ayushecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidFieldException extends RuntimeException {
    public InvalidFieldException(String errorMessage){
        super(errorMessage);
    }
}
