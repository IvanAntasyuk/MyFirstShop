package ru.geekbrains.antasyuk.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.geekbrains.antasyuk.dto.AllCartDto;
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
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
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
                            ProductRepository productRepository,
                            RabbitTemplate rabbitTemplate,
                            SimpMessagingTemplate template) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.template = template;
    }

    public List<OrderDto> findOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username)
                .stream()
                .map(OrderDto::new)
                .collect(toList());
    }

    @Override
    public void createOrder(String username, AllCartDto allCartDto) {
        if (cartService.getLineItems().isEmpty()) {
            logger.info("Can't create order for empty Cart");
            return;
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order(null,
                LocalDateTime.now(),
                Order.OrderStatus.CREATED,
                user,
                allCartDto.getSubtotal()
        );

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
                .collect(toList());
        order.setOrderLineItems(orderLineItems);
        orderRepository.save(order);
        cartService.clearCart();
        rabbitTemplate.convertAndSend("order.exchange", "new_order",
                new OrderMessage(order.getId(), order.getStatus().name()));
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No product with id"));
    }

    @RabbitListener(queues = "processed.order.queue")
    public void receive(OrderMessage msg) {
        logger.info("Order with id '{}' state change to '{}'", msg.getId(), msg.getStatus());
        Optional<Order> oOrder = orderRepository.findById(msg.getId());
        if (oOrder.isPresent()) {
            Order order = oOrder.get();
            for (Order.OrderStatus status: Order.OrderStatus.values()) {
                if (status.name().equals(msg.getStatus())) {
                    order.setStatus(status);
                    break;
                }
            }
            orderRepository.save(order);
            template.convertAndSend("/order_out/order",
                    new OrderDto(order.getId(), order.getStatus()));
        }
    }
}