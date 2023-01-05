package dev.eduardova.jwt.services;

import dev.eduardova.jwt.dtos.roles.RoleRequest;
import dev.eduardova.jwt.dtos.roles.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    Page<RoleResponse> getAll(Pageable pageable);

    RoleResponse add(RoleRequest request);

}
