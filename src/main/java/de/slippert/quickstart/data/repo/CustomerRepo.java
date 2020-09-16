package de.slippert.quickstart.data.repo;

import de.slippert.quickstart.data.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepo extends CrudRepository<Customer, Long> {
}
