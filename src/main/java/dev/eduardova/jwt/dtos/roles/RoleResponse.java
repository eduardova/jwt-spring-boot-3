package dev.eduardova.jwt.dtos.roles;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleResponse extends RoleRequest {

    private Long id;

}
