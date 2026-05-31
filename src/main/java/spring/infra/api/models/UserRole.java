package spring.infra.api.models;

import jakarta.persistence.*;
import spring.infra.api.enums.Role;

import java.util.UUID;

@Entity
@Table(name = "user_roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "role_id", nullable = false)
    private Role roleId;

    public UserRole(){}
    public UserRole(UUID userId, Role roleId){
        this.userId = userId;
        this.roleId = roleId;
    }

    public UUID getUserId() {return userId;}
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Role getRoleId() {return roleId;}
    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    public UUID getId() {return id;}
    public void setId(UUID id) {
        this.id = id;
    }
}
