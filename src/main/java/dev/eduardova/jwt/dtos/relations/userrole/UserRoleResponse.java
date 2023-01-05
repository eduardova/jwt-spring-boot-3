package dev.eduardova.jwt.dtos.relations.userrole;

import dev.eduardova.jwt.dtos.roles.RoleResponse;
import dev.eduardova.jwt.dtos.users.UserResponse;
import java.util.List;
import lombok.Data;

@Data
public class UserRoleResponse {

    private UserResponse user;
    private List<RoleResponse> roles;

}
