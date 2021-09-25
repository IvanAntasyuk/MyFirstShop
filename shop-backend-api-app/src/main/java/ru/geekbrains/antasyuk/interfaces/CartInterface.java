package ru.geekbrains.antasyuk.interfaces;

import ru.geekbrains.antasyuk.dto.LineItem;
import ru.geekbrains.antasyuk.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface CartInterface {

    void addProductQty(ProductDto productDto, String color, String material, int qty);

    void removeProductQty(LineItem lineItem,Integer qty);

    void removeProduct(LineItem lineItem);

    List<LineItem> getLineItems();

    BigDecimal getSubTotal();

}