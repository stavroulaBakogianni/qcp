package org.acme.repository;

import static io.quarkus.arc.ComponentsProvider.LOG;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.acme.entity.Customer;
import org.acme.exception.ResourceNotFoundException;

@ApplicationScoped
public class CustomerRepositoryImpl implements Repository<Customer, Long> {

    @Inject
    EntityManager entityManager;

    @Override
    @Transactional
    public Optional<Customer> save(Customer customer) {
        try {
            entityManager.persist(customer);
            LOG.info("Customer saved successfully");
            return Optional.of(customer);
        } catch (ResourceNotFoundException e) {
            LOG.error("Error saving customer: {}" + e.getMessage());
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<Customer> getByVat(String vat) {
        try {
            TypedQuery<Customer> query = entityManager.createQuery("FROM Customer WHERE vat = :vat", Customer.class);
            query.setParameter("vat", vat);
            Optional<Customer> customer = query.getResultStream().findFirst();
            if (customer.isPresent()) {
                LOG.info("Customer retrieved successfully with VAT:" + vat);
            } else {
                LOG.warn("No customer found with VAT:" + vat);
            }
            return customer;
        } catch (Exception e) {
            LOG.error("Error retrieving customer with VAT:" + vat + e.getMessage());
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public List<Customer> getAll() {
        try {
            TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
            List<Customer> customers = query.getResultList();
            LOG.info("Retrieved {} customers from the database" + customers.size());
            return customers;
        } catch (Exception e) {
            LOG.error("Error retrieving customers:" + e.getMessage());
            return List.of();
        }
    }

}
