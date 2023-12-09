package ru.theflampu.backend.service;

import ru.theflampu.backend.response.CartResponse;

import javax.transaction.Transactional;
import java.util.Map;

public interface CartService {
    void addItem(long itemId, String email);

    void removeItem(long itemId, String email);

    void deleteItem(long itemId, String email);

    CartResponse getCart(String email);

    @Transactional
    void createOrder(Map<Long, Integer> order, String email);
}
