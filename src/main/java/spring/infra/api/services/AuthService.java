package spring.infra.api.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.infra.api.dtos.auth.SigninRequest;
import spring.infra.api.dtos.auth.SigninResponse;
import spring.infra.api.models.User;
import spring.infra.api.repository.UserRepository;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional(readOnly = true)
    public SigninResponse signin(SigninRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.passwd(), user.getPasswd())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String accessToken = tokenService.generateAccessToken(user);
        String refreshToken = tokenService.generateRefreshToken(user.getId());

        return new SigninResponse(accessToken, refreshToken, 300L);
    }

    @Transactional(readOnly = true)
    public SigninResponse refresh(String oldRefreshToken) {
        String userIdStr = tokenService.getUserIdFromRefreshToken(oldRefreshToken);
        UUID userId = UUID.fromString(userIdStr);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadCredentialsException("User not found or disabled"));

        // Revogar token antigo (uso único - Rotação)
        tokenService.revokeRefreshToken(oldRefreshToken);

        // Gerar novos tokens
        String newAccessToken = tokenService.generateAccessToken(user);
        String newRefreshToken = tokenService.generateRefreshToken(user.getId());

        return new SigninResponse(newAccessToken, newRefreshToken, 300L);
    }

    public void logout(String refreshToken) {
        tokenService.revokeRefreshToken(refreshToken);
    }
}
