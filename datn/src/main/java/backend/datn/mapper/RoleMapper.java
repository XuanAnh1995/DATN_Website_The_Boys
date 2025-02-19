package backend.datn.mapper;

import backend.datn.dto.response.RoleRespone;
import backend.datn.entities.Role;


public class RoleMapper {
    public static RoleRespone toRoleRespon(Role role) {
        return RoleRespone.builder().
                id(role.getId()).
                name(role.getName()).
                build();
    }
}
