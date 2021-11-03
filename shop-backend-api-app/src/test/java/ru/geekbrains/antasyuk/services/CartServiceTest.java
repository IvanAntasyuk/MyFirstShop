package ru.geekbrains.antasyuk.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.antasyuk.dto.LineItem;
import ru.geekbrains.antasyuk.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService = new CartService();
    }

    @Test
    public void testIfNewCartIsEmpty() {
        assertNotNull(cartService.getLineItems());
        assertEquals(0, cartService.getLineItems().size());
        assertEquals(BigDecimal.ZERO, cartService.getSubTotal());
    }

    @Test
    public void testAddProduct() {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setPrice(new BigDecimal(123));
        expectedProduct.setTitle("Product name");

        cartService.addProductQty(expectedProduct, "color", "material", 2);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());

        LineItem lineItem = lineItems.get(0);
        assertEquals("color", lineItem.getColor());
        assertEquals("material", lineItem.getMaterial());
        assertEquals(2, lineItem.getQty());

        assertEquals(expectedProduct.getId(), lineItem.getProductId());
        assertNotNull(lineItem.getProductDto());
        assertEquals(expectedProduct.getTitle(), lineItem.getProductDto().getTitle());
    }

    @Test
    public void testRemoveProduct() {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setPrice(new BigDecimal(123));
        expectedProduct.setTitle("Product name");

        ProductDto expectedProduct2 = new ProductDto();
        expectedProduct2.setId(2L);
        expectedProduct2.setPrice(new BigDecimal(125));
        expectedProduct2.setTitle("Product name 2");

        cartService.addProductQty(expectedProduct, "color", "material", 2);
        cartService.addProductQty(expectedProduct2, "color2", "material2", 1);

        List<LineItem> lineItems = cartService.getLineItems();
        assertEquals(2, cartService.getLineItems().size());



        LineItem lineItem = lineItems.get(0);
        assertEquals("color", lineItem.getColor());
        assertEquals("material", lineItem.getMaterial());
        assertEquals(2, lineItem.getQty());

        assertEquals(expectedProduct.getId(), lineItem.getProductId());
        assertNotNull(lineItem.getProductDto());
        assertEquals(expectedProduct.getTitle(), lineItem.getProductDto().getTitle());
    }

    @Test
    public void testRemoveAllProduct() {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setPrice(new BigDecimal(123));
        expectedProduct.setTitle("Product name");

        ProductDto expectedProduct2 = new ProductDto();
        expectedProduct2.setId(2L);
        expectedProduct2.setPrice(new BigDecimal(125));
        expectedProduct2.setTitle("Product name 2");

        cartService.addProductQty(expectedProduct, "color", "material", 2);
        cartService.addProductQty(expectedProduct2, "color2", "material2", 1);

        assertEquals(2, cartService.getLineItems().size());

        cartService.clearCart();
        assertEquals(0, cartService.getLineItems().size());
    }

    @Test
    public void testGetSubtotal() {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setPrice(new BigDecimal(123));
        expectedProduct.setTitle("Product name");

        cartService.addProductQty(expectedProduct,"color","assa",2;

        assertEquals(new BigDecimal(246) , cartService.getSubTotal());
    }


}