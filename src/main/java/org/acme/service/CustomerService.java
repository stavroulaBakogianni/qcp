package org.acme.service;

import java.util.List;
import java.util.Optional;
import org.acme.dto.CustomerDTO;

public interface CustomerService{
    
    Optional<CustomerDTO> saveCustomer(CustomerDTO customerDto);
    List<CustomerDTO> findAll();
    Optional<CustomerDTO> findByVat(String vat);
}
