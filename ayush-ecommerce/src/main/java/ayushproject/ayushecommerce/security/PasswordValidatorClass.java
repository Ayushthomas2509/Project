package ayushproject.ayushecommerce.security;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidatorClass implements ConstraintValidator<PasswordValidator,String> {

    public boolean isValid(String s, ConstraintValidatorContext cvc) {
        boolean isCapital=s.matches( ".*[A-Z].*");
        boolean isNonCapital=s.matches(  ".*[a-z].*");
        boolean isNumeric=s.matches( ".*\\d.*");
        boolean isSpecialChar=s.matches(".*[`@#_].*");
        boolean result=isCapital&&isNonCapital&&isSpecialChar&&isNumeric;
        return result;
    }

    @PasswordValidator
    private String password;
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

