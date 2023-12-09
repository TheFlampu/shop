package ru.theflampu.backend.response;

import lombok.Getter;
import lombok.Setter;
import ru.theflampu.backend.dto.ItemDTO;
import ru.theflampu.backend.entity.Item;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CartResponse {
    private List<CartItem> items;


    @Getter
    public static class CartItem {
        private final ItemDTO item;
        private final int count;

        public CartItem(Map.Entry<Item, Integer> entry) {
            this.item = new ItemDTO(entry.getKey());
            this.count = entry.getValue();
        }
    }
}
