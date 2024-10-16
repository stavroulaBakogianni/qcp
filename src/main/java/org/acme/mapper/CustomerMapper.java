package org.acme.mapper;

import org.acme.dto.CustomerDTO;
import org.acme.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi") 
public interface CustomerMapper {
    
    CustomerMapper INSTANCE = Mappers.getMapper( CustomerMapper.class);
    
    CustomerDTO customerToDTO(Customer customer);
    
     Customer customerDTOToEntity(CustomerDTO customerDTO);
}
