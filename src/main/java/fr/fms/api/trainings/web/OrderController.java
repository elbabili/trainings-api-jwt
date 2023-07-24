package fr.fms.api.trainings.web;

import fr.fms.api.trainings.dao.CustomerRepository;
import fr.fms.api.trainings.dao.OrderItemRepository;
import fr.fms.api.trainings.dao.OrderRepository;
import fr.fms.api.trainings.dao.TrainingRepository;
import fr.fms.api.trainings.entities.Customer;
import fr.fms.api.trainings.entities.Order;
import fr.fms.api.trainings.entities.OrderItem;
import fr.fms.api.trainings.entities.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin("*")
@RestController
//@RequestMapping("/api")
public class OrderController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    @PostMapping("/order")
    public Order saveOrder(@RequestBody Order order){
        Customer customer = new Customer();
        customer.setFirstName(order.getCustomer().getFirstName());
        customer.setName(order.getCustomer().getName());
        customer.setAddress(order.getCustomer().getAddress());
        customer.setPhone(order.getCustomer().getPhone());
        customer.setEmail(order.getCustomer().getEmail());
        customer = customerRepository.save(customer);       //sauvegarde du client

        Order orderLocal = new Order();
        orderLocal.setCustomer(customer);
        orderLocal.setDate(new Date());
        orderLocal.setAmount(order.getAmount());                 //ToDo on doit refaire le calcul côté back
        orderLocal = orderRepository.save(orderLocal);           //sauvegarde de la commande

        for(OrderItem oi : order.getTrainingItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setName(oi.getName());
            orderItem.setQuantity(oi.getQuantity());
            orderItem.setPrice(oi.getPrice());
            Training training = trainingRepository.findById(oi.getId()).get();
            orderItem.setTraining(training);
            orderItem.setOrder(orderLocal);
            orderItemRepository.save(orderItem);
        }
        return orderLocal;
    }
}
