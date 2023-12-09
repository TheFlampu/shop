package ru.theflampu.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.theflampu.backend.entity.Item;
import ru.theflampu.backend.entity.User;
import ru.theflampu.backend.response.CartResponse;
import ru.theflampu.backend.service.CartService;
import ru.theflampu.backend.util.EmailUtil;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultCartService implements CartService {
    private final DefaultItemService itemService;

    private final DefaultUserService userService;

    private final EmailUtil emailUtil;

    @Override
    public void addItem(long itemId, String email) {
        Item item = itemService.findById(itemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

        if (item.getAvailableCount() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Товар закончился");
        }

        if (!email.equals("anonymousUser")) {
            User user = userService.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

            Map<Item, Integer> cart = user.getItems();

            if (cart.containsKey(item)) {
                cart.put(item, cart.get(item) + 1);
            } else {
                cart.put(item, 1);
            }

            userService.save(user);
        }
    }

    @Override
    public void removeItem(long itemId, String email) {
        Item item = itemService.findById(itemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        User user = userService.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Map<Item, Integer> cart = user.getItems();

        if (cart.containsKey(item)) {
            if (cart.get(item) == 1) {
                cart.remove(item);
            } else {
                cart.put(item, cart.get(item) - 1);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Товара нет в корзине");
        }

        userService.save(user);
    }

    @Override
    public void deleteItem(long itemId, String email) {
        Item item = itemService.findById(itemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));
        User user = userService.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Map<Item, Integer> cart = user.getItems();

        if (cart.containsKey(item)) {
            cart.remove(item);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Товар нет в корзине");
        }

        userService.save(user);
    }

    @Override
    public CartResponse getCart(String email) {
        User user = userService.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        CartResponse cartResponse = new CartResponse();

        cartResponse.setItems(user.getItems().entrySet()
                .stream()
                .map(CartResponse.CartItem::new)
                .collect(Collectors.toList()));

        return cartResponse;
    }

    @Override
    @Transactional
    public void createOrder(Map<Long, Integer> order, String email) {
        User user = userService.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        StringBuilder emailMessage = new StringBuilder("Ваш заказ оформлен! \n\n");
        Map<Item, Integer> cart = user.getItems();
        int totalPrice = 0;
        int counter = 1;

        for (Map.Entry<Long, Integer> entry : order.entrySet()) {
            Long itemId = entry.getKey();
            int count = entry.getValue();

            if (count < 1) {
                continue;
            }

            Item item = itemService.findById(itemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар c id " + itemId + " не найден"));

            if(item.getAvailableCount() < count) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Товара c id " + itemId + " недостаточно");
            }

            item.setAvailableCount(item.getAvailableCount() - count);
            itemService.save(item);

            cart.remove(item);

            int price = (item.getSalePrice() == null ? item.getPrice() : item.getSalePrice()) * count;
            totalPrice += price;

            emailMessage
                    .append(counter++)
                    .append(") ")
                    .append(item.getName())
                    .append(" ")
                    .append(count)
                    .append(" шт. за ")
                    .append(price)
                    .append(" руб.\n");
        }

        emailMessage.append("\n").append("Итого: ").append(totalPrice).append(" руб.");

        userService.save(user);

        emailUtil.sendEmail(user.getEmail(), emailMessage.toString());
    }
}
