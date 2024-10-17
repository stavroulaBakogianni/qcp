package org.acme.service;

import static io.quarkus.arc.ComponentsProvider.LOG;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.acme.dto.CustomerDTO;
import org.acme.entity.Customer;
import org.acme.mapper.CustomerMapper;
import org.acme.repository.CustomerRepositoryImpl;

@ApplicationScoped
public class CustomerServiceImpl implements CustomerService {

    @Inject
    private CustomerRepositoryImpl customerRepo;

    @Inject
    CustomerMapper customerMapper;

    @Override
    public Optional<CustomerDTO> saveCustomer(CustomerDTO customerDto) {
        try {
            LOG.info("Creating customer");
            Customer customerEntity = customerMapper.customerDTOToEntity(customerDto);
            Optional<Customer> savedCustomerOptional = customerRepo.save(customerEntity);
            return savedCustomerOptional.map(customerMapper::customerToDTO);
        } catch (Exception e) {
            LOG.info("Customer not created", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public List<CustomerDTO> findAll() {
        try {
            LOG.info("Feching Customers");
            List<Customer> customers = customerRepo.getAll();
            return customers.stream()
                    .map(customerMapper::customerToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.info("Failed to fetch customers", e.getMessage(), e);
            return List.of();
        }
    }

    @Override
    public Optional<CustomerDTO> findByVat(String vat) {
        try {
            LOG.info("Fechin customer with vat " + vat);
            return customerRepo.getByVat(vat).map(customerMapper::customerToDTO);
        } catch (Exception e) {
            LOG.info("Customer not found", e.getMessage(), e);
            return Optional.empty();
        }
    }

}
