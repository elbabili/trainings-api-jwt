package fr.fms.api.trainings.dao;

import fr.fms.api.trainings.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
