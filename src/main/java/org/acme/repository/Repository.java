package org.acme.repository;

import java.util.List;
import java.util.Optional;


public interface Repository<T, K> {
                    
    Optional<T> save(T t); //create

    List<T> getAll();

}
