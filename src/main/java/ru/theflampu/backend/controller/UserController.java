package ru.theflampu.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.theflampu.backend.dto.CategoryDTO;
import ru.theflampu.backend.response.CartResponse;
import ru.theflampu.backend.response.ItemsResponse;
import ru.theflampu.backend.service.CartService;
import ru.theflampu.backend.service.CategoryService;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final CategoryService categoryService;
    private final CartService cartService;

    @GetMapping("/items")
    public ItemsResponse getAllItems() {
        return categoryService.getItems(-1);
    }
    @GetMapping("/items/{categoryId}")
    public ItemsResponse getItems(@PathVariable long categoryId) {
        return categoryService.getItems(categoryId);
    }

    @PostMapping("/addItem/{itemId}")
    public void addItem(@PathVariable long itemId, @AuthenticationPrincipal String email) {
        cartService.addItem(itemId, email);
    }

    @PostMapping("/removeItem/{itemId}")
    @Secured("ROLE_USER")
    public void removeItem(@PathVariable long itemId, @AuthenticationPrincipal String email) {
        cartService.removeItem(itemId, email);
    }

    @PostMapping("/deleteItem/{itemId}")
    @Secured("ROLE_USER")
    public void deleteItem(@PathVariable long itemId, @AuthenticationPrincipal String email) {
        cartService.deleteItem(itemId, email);
    }

    @GetMapping("/cart")
    @Secured("ROLE_USER")
    public CartResponse getCart(@AuthenticationPrincipal String email) {
        return cartService.getCart(email);
    }

    @PostMapping("/order")
    @Secured("ROLE_USER")
    public void createOrder(@RequestBody Map<Long, Integer> order, @AuthenticationPrincipal String email) {
        cartService.createOrder(order, email);
    }

    @GetMapping("/menu")
    public List<CategoryDTO> getMenu() {
        return categoryService.getMenu();
    }
}
