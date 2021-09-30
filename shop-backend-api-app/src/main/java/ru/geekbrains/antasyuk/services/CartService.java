package ru.geekbrains.antasyuk.services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.geekbrains.antasyuk.dto.LineItem;
import ru.geekbrains.antasyuk.dto.ProductDto;
import ru.geekbrains.antasyuk.interfaces.CartInterface;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService implements CartInterface {

    private final Map<LineItem, Integer> lineItems;

    public CartService() {
        this.lineItems = new HashMap<>();
    }

    @JsonCreator
    public CartService(@JsonProperty("lineItems ") List<LineItem> lineItems) {
        this.lineItems = lineItems.stream().collect(Collectors.toMap(li -> li, LineItem::getQty));

    }

    @Override
    public void addProductQty(ProductDto productDto, String color, String material, int qty) {
        LineItem lineItem = new LineItem(productDto, color, material);
        lineItems.put(lineItem, lineItems.getOrDefault(lineItem, 0) + qty);

    }

    @Override
    public void removeProductQty(LineItem lineItem, Integer qty) {
        if (qty > 0) {
            lineItems.replace(lineItem, qty);
            lineItems.forEach(LineItem::setQty);
        } else {
            removeProduct(lineItem);
        }
    }

    @Override
    public void removeProduct(LineItem lineItem) {
        lineItems.remove(lineItem);
    }

    @Override
    public List<LineItem> getLineItems() {
        lineItems.forEach(LineItem::setQty);
        return new ArrayList<>(lineItems.keySet());
    }

    @JsonIgnore
    @Override
    public BigDecimal getSubTotal() {
        lineItems.forEach(LineItem::setQty);
        return lineItems.keySet()
                .stream().map(LineItem::getItemTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}