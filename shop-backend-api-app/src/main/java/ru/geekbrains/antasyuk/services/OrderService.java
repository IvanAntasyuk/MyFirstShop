package ru.geekbrains.antasyuk.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.geekbrains.antasyuk.dto.OrderDto;
import ru.geekbrains.antasyuk.dto.OrderMessage;
import ru.geekbrains.antasyuk.interfaces.OrderInterface;
import ru.geekbrains.antasyuk.interfaces.OrderRepository;
import ru.geekbrains.antasyuk.interfaces.ProductRepository;
import ru.geekbrains.antasyuk.interfaces.UserRepository;
import ru.geekbrains.antasyuk.models.Order;
import ru.geekbrains.antasyuk.models.OrderLineItem;
import ru.geekbrains.antasyuk.models.Product;
import ru.geekbrains.antasyuk.models.User;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderService implements OrderInterface{

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private final CartService cartService;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final RabbitTemplate rabbitTemplate;

    private final SimpMessagingTemplate template;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CartService cartService,
                        UserRepository userRepository,
                        ProductRepository productRepository, RabbitTemplate rabbitTemplate, SimpMessagingTemplate template) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.template = template;
    }

    public List<OrderDto> findOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username).stream()
                .map(order->
                        new OrderDto(order.getId(),
                                order.getTotalPrice(),
                                order.getStatus().name(),
                                order.getOrderDate(),
                                username
                        ))
                .collect(Collectors.toList());

    }

    @Transactional
    public void createOrder(String username) {
        if (cartService.getLineItems().isEmpty()) {
            logger.info("Can't create order for empty Cart");
            return;
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));


        Order order = orderRepository.save(new Order(
                null,
                LocalDateTime.now(),
                Order.OrderStatus.CREATED,
                user,
                new BigDecimal(0)
        ));

        List<OrderLineItem> orderLineItems = cartService.getLineItems()
                .stream()
                .map(li -> new OrderLineItem(
                        null,
                        order,
                        findProductById(li.getProductId()),
                        li.getProductDto().getPrice(),
                        li.getQty(),
                        li.getColor(),
                        li.getMaterial()
                ))
                .collect(Collectors.toList());
        order.setOrderLineItems(orderLineItems);
//       вычисляем общу сумму заказа
        order.setTotalPrice(orderLineItems.stream()
                .map(orderLineItem -> orderLineItem.getProduct().getCost())
                .reduce((x,y)->x.add(y)).get());


        orderRepository.save(order);
        rabbitTemplate.convertAndSend("order.exchange", "new_order", new OrderMessage(order.getId(), order.getStatus().name()));

    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No product with id"));
    }

    @RabbitListener(queues = "processed.order.queue")
    public void receive(OrderMessage order) {
        logger.info("Order with id '{}' state change to '{}'", order.getId(), order.getState());
        template.convertAndSend("/order_out/order", order);
    }
}