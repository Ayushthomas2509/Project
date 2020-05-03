package ayushproject.ayushecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConfirmPasswordException extends RuntimeException {
    public ConfirmPasswordException(String errorMessage){super(errorMessage);}
}
