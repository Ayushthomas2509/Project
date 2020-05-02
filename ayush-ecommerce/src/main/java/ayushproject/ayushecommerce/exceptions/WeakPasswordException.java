package ayushproject.ayushecommerce.exceptions;

public class WeakPasswordException extends RuntimeException {
    public WeakPasswordException(){
        super("Password Not Correct");
    }
}
