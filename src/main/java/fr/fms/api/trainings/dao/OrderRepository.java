package fr.fms.api.trainings.dao;

import fr.fms.api.trainings.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
