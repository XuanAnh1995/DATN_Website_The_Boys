package backend.datn.mapper;

import backend.datn.dto.response.EmployeeRespone;
import backend.datn.entities.Employee;


public class EmployeeMapper {
    public static EmployeeRespone toEmployeeRespone(Employee employee) {
        return EmployeeRespone.builder()
                .id(employee.getId())
                .employeeCode(employee.getEmployeeCode())
                .role(RoleMapper.toRoleRespon(employee.getRole()))
                .fullname(employee.getFullname())
                .username(employee.getUsername())
                .password(employee.getPassword())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .photo(employee.getPhoto())
                .status(employee.getStatus())
                .createDate(employee.getCreateDate())
                .updateDate(employee.getUpdateDate())
                .forgetPassword(employee.getForgetPassword())
                .gender(employee.getGender())
                .build();
    }
}
