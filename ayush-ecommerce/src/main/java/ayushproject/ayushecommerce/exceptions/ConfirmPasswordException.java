package ayushproject.ayushecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ConfirmPasswordException extends RuntimeException {
    public ConfirmPasswordException(String not_a_match){System.err.println("Password Dont Match");}
}
