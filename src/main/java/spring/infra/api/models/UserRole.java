package spring.infra.api.models;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_roles_id")
    private Long userRolesId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    public UserRole(){}
    public UserRole(UUID userId, Long roleId){
        this.userId = userId;
        this.roleId = roleId;
    }

    public UUID getUserId() {return userId;}
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Long getRoleId() {return roleId;}
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserRolesId() {return userRolesId;}
    public void setUserRolesId(Long userRolesId) {
        this.userRolesId = userRolesId;
    }
}
