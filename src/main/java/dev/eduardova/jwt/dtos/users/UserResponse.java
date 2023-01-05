package dev.eduardova.jwt.dtos.users;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserResponse extends UsersRequest {

    private Long id;

    @Override
    public String getPassword() {
        return null;
    }
}
