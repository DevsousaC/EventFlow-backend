package spring.infra.api.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.infra.api.dtos.auth.SignupRequest;
import spring.infra.api.dtos.auth.SignupResponse;
import spring.infra.api.exceptions.EmailAlreadyExistsException;
import spring.infra.api.models.Role;
import spring.infra.api.models.User;
import spring.infra.api.repository.RoleRepository;
import spring.infra.api.repository.UserRepository;

import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered: " + request.email());
        }

        // Buscar a role padrão VISITANT (ID 1)
        Role defaultRole = roleRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("Default role VISITANT not found in database."));

        User user = new User();
        user.setEmail(request.email());
        user.setPasswd(passwordEncoder.encode(request.password()));
        user.setName(request.name());
        user.setPhone(request.phone());
        user.setRoles(Collections.singleton(defaultRole));

        User savedUser = userRepository.save(user);

        return new SignupResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName(),
                savedUser.getPhone()
        );
    }
}
