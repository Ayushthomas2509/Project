package ayushproject.ayushecommerce.exceptions;

public class WeakPasswordEx extends RuntimeException {
    public WeakPasswordEx(){
        super("Password Not Correct");
    }
}
