package ru.geekbrains.antasyuk.services;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.geekbrains.antasyuk.dto.*;
import ru.geekbrains.antasyuk.interfaces.OrderRepository;
import ru.geekbrains.antasyuk.interfaces.ProductRepository;
import ru.geekbrains.antasyuk.interfaces.UserRepository;
import ru.geekbrains.antasyuk.models.Order;
import ru.geekbrains.antasyuk.models.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;
    @MockBean
    private RabbitTemplate rabbitTemplate;
    @MockBean
    private SimpMessagingTemplate template;

    @Test
    public void findOrdersByUsernameTest() {
        User petr = new User();
        petr.setUsername("vasya");
        petr.setAge(23);
        userRepository.save(petr);
        Order order = new Order(1L, LocalDateTime.now(), Order.OrderStatus.CLOSED, petr, new BigDecimal("18.00"));
        orderRepository.save(order);

        List<OrderDto> orders = orderService.findOrdersByUsername(petr.getUsername());
        assertFalse(orders.isEmpty());
        assertEquals(orders.get(0).getOrderDate(), order.getOrderDate());
        assertEquals(orders.get(0).getStatus(), order.getStatus());
        assertEquals(orders.get(0).getPrice(), order.getTotalPrice());
    }

    @Test
    public void createOrderTest() {
        User petr = new User();
        petr.setUsername("petr");
        petr.setAge(23);
        userRepository.save(petr);

        List<Long> pictures = new ArrayList<>();
        Long mainPicture = new Long(3L);
        CategoryDto cat = new CategoryDto(1L, "Cat");
        BrandDto dog = new BrandDto(1L,"Dog");
        ProductDto productDto = new ProductDto(1L, "First", "S", new BigDecimal(7),
                cat,dog,pictures,mainPicture);

        LineItem e1 = new LineItem(productDto, "green", "gold");
        e1.setQty(10);
        List<LineItem> lineItems = List.of(e1);
        cartService = new CartService(lineItems);

        AllCartDto cartDto = new AllCartDto(lineItems, new BigDecimal("9.00"));
        orderService.createOrder(petr.getUsername(), cartDto);
        List<OrderDto> ordersByUsername = orderService.findOrdersByUsername(petr.getUsername());
        assertFalse(ordersByUsername.isEmpty());
        assertEquals(ordersByUsername.get(0).getPrice(), cartDto.getSubtotal());
    }
}