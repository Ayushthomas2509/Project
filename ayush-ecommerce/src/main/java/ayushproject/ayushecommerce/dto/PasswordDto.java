//package ayushproject.ayushecommerce.dto;
//
//import com.sun.istack.NotNull;
//
//import javax.persistence.Transient;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.Pattern;
//
//public class PasswordDto {
//
//    @NotEmpty(message = "Password is a mandatory field")
//    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})",message = "Password should contain atleast 8 characters and one Uppercase,lowercase,digit and special character")
//    private String password;
//
//    @Transient
//    @NotNull
//    @NotEmpty(message = "Password is a mandatory field")@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})",message = "Password should contain atleast 8 characters and one Uppercase,lowercase,digit and special character")
//    private String confirmpassword;
//
//    PasswordDto(){
//
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getConfirmpassword() {
//        return confirmpassword;
//    }
//
//    public void setConfirmpassword(String confirmpassword) {
//        this.confirmpassword = confirmpassword;
//    }
//
//    @Override
//    public String toString() {
//        return "PasswordDto{" +
//                "password='" + password + '\'' +
//                ", confirmpassword='" + confirmpassword + '\'' +
//                '}';
//    }
//
//
//}
