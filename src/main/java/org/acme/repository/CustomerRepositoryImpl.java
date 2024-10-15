package org.acme.repository;

import java.util.logging.Logger;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.acme.entity.Customer;
import org.acme.exception.ResourceNotFoundException;

@ApplicationScoped
//@NoArgsConstactor
@Slf4j
public class CustomerRepositoryImpl implements Repository<Customer, Long> {

    @Inject
    EntityManager entityManager;

    @Override
    @Transactional
    public Optional<Customer> save(Customer customer) {
        try {
            entityManager.persist(customer);
            return Optional.of(customer);
        } catch (ResourceNotFoundException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> getByVat(String vat) {
        try {
            TypedQuery<Customer> query = entityManager.createQuery("FROM Customer WHERE vat = :vat", Customer.class);
            query.setParameter("vat", vat);
            return query.getResultStream().findFirst();
        } catch (ResourceNotFoundException e) {

            Logger.getLogger("Error retrieving data");
            System.out.println(e.getMessage());
            return Optional.empty();
        }

    }

    @Override
    public List<Customer> getAll() {
        try {
            TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
            return query.getResultList();
        } catch (ResourceNotFoundException e) {
            Logger.getLogger("Error retrieving data");
            System.out.println(e.getMessage());
        }
        return List.of();
    }

}
