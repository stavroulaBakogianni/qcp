package org.acme.mapper;

import org.acme.dto.CustomerDTO;
import org.acme.entity.Customer;
import org.mapstruct.factory.Mappers;

public interface CustomerMapper {
    
    CustomerMapper INSTANCE = Mappers.getMapper( CustomerMapper.class);
    
    CustomerDTO customerToDTO(Customer customer);
    
     Customer customerDTOToEntity(CustomerDTO customerDTO);
}
