package fr.fms.api.trainings.dao;

import fr.fms.api.trainings.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
