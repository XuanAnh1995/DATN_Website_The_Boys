package backend.datn.mapper;

import backend.datn.dto.response.CustomerRespone;
import backend.datn.entities.Customer;


public class CustomerMapper {
    public static CustomerRespone toCustomerRespone(Customer customer) {
        return CustomerRespone.builder().
                id(customer.getId()).
                fullname(customer.getFullname()).
                username(customer.getUsername()).
                build();
    }
}
