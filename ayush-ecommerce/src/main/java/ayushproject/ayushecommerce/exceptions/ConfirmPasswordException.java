package ayushproject.ayushecommerce.exceptions;

public class ConfirmPasswordException extends RuntimeException {
    public ConfirmPasswordException(String not_a_match){System.err.println("Password Dont Match");}
}
