package org.acme.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.acme.dto.CustomerDTO;
import org.acme.entity.Customer;
import org.acme.exception.ResourceNotFoundException;
import org.acme.mapper.CustomerMapper;
import org.acme.repository.CustomerRepositoryImpl;

@ApplicationScoped 
public class CustomerServiceImpl implements CustomerService{
    @Inject
    private CustomerRepositoryImpl customerRepo;
    
    @Inject
    CustomerMapper customerMapper;

    @Override
    public Optional<CustomerDTO> saveCustomer(CustomerDTO customerDto) {
        try {
        Customer customerEntity = customerMapper.customerDTOToEntity(customerDto);  
        Optional<Customer> savedCustomerOptional = customerRepo.save(customerEntity);
        return savedCustomerOptional.map(customerMapper::customerToDTO);
        } catch (ResourceNotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<CustomerDTO> findAll() {
        try {
        List<Customer> customers = customerRepo.getAll();
        return customers.stream()
                .map(customerMapper::customerToDTO)
             .collect(Collectors.toList());
        } catch (ResourceNotFoundException e) {
            return List.of();
        }
    }

    @Override
    public Optional<CustomerDTO> findByVat(String vat) {
        try {
        return customerRepo.getByVat(vat).map(customerMapper::customerToDTO);
        } catch (ResourceNotFoundException e) {
            return Optional.empty();
        }
    }
    
    
    
}
