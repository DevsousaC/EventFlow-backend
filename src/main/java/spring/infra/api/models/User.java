package spring.infra.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Email(message = "Invalid email format")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String passwd;

    @Column(name = "name", nullable = false)
    private String name;

    public User() {}
    public User(String email, String passwd, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.passwd = passwd;
    }

    public Long getId() {return id;}
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {return passwd;}
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {return name;}
    public void setName(String name) {
        this.name = name;
    }
}