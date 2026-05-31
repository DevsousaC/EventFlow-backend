package spring.infra.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.infra.api.dtos.auth.SigninRequest;
import spring.infra.api.dtos.auth.SigninResponse;
import spring.infra.api.services.AuthService;

@RestController()
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService, BCryptPasswordEncoder passwordEncoder){
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest signinRequest) {
        SigninResponse signinResponse = this.authService.signin(signinRequest);
        return ResponseEntity.ok(signinResponse);
    }
}
