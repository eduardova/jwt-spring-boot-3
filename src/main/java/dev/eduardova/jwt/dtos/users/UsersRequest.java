package dev.eduardova.jwt.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
