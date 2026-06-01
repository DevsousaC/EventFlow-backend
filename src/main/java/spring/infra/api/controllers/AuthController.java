package spring.infra.api.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.infra.api.dtos.auth.SigninRequest;
import spring.infra.api.dtos.auth.SigninResponse;
import spring.infra.api.dtos.auth.SignupRequest;
import spring.infra.api.dtos.auth.SignupResponse;
import spring.infra.api.services.AuthService;
import spring.infra.api.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService){
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signin(@Valid @RequestBody SigninRequest signinRequest) {
        SigninResponse signinResponse = this.authService.signin(signinRequest);
        return ResponseEntity.ok(signinResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        SignupResponse signupResponse = this.userService.signup(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(signupResponse);
    }
}
