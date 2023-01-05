package dev.eduardova.jwt.services;

import dev.eduardova.jwt.dtos.relations.userrole.UserRoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RelationUserRoleService {
    
    void add(Long idUser, Long idRole);

    Page<UserRoleResponse> getAll(Pageable pageable);

}
