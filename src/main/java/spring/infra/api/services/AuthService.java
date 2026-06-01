package spring.infra.api.services;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import spring.infra.api.dtos.auth.SigninRequest;
import spring.infra.api.dtos.auth.SigninResponse;
import spring.infra.api.models.User;
import spring.infra.api.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    @Transactional
    public SigninResponse signin(SigninRequest signinRequest) {
        try{
            Optional<User> userFound = userRepository.findByEmail(signinRequest.getEmail());
            User user = userFound.get();

            if(!userFound.isEmpty() || !passwordEncoder.matches(signinRequest.getPasswd(), user.getPasswd())){
                throw new BadCredentialsException("user or/and password is invalid!");
            }

            var now = Instant.now();
            var expiresIn = 300L;

            var claims = JwtClaimsSet.builder()
                    .issuer("")
                    .subject(user.getId().toString())
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiresIn))
                    .build();

            var jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            SigninResponse signinResponse = new SigninResponse(jwt, expiresIn);
            return signinResponse;
        } catch (Exception e) {throw new RequestRejectedException("");
        }
    }
}
