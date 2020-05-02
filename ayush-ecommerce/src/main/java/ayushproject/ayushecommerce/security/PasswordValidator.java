package ayushproject.ayushecommerce.security;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Size;
@Component
public class PasswordValidator implements ConstraintValidator<PasswordValidatorAnnotation,String> {

    public boolean isValid(String s, ConstraintValidatorContext cvc) {
        boolean isCapital=s.matches( ".*[A-Z].*");
        boolean isNonCapital=s.matches(  ".*[a-z].*");
        boolean isNumeric=s.matches( ".*\\d.*");
        boolean isSpecialChar=s.matches(".*[`@#_].*");
        boolean result=isCapital&&isNonCapital&&isSpecialChar&&isNumeric;
        return result;
    }

    @PasswordValidatorAnnotation
    @Size(min = 8,max = 15)
    private String password;
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

