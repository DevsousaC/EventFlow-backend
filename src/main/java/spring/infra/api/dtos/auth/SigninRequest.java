package spring.infra.api.dtos.auth;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class SigninRequest {
    @NotNull
    @Email(message = "Invalid email format")
    private String email;

    @NotNull
    private String passwd;

    public String getEmail() {return email;}
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {return passwd;}
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
