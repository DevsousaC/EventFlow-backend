package spring.infra.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.infra.api.models.UserRole;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<Set<UserRole>> findByUserId(UUID userId);
}
