package ru.theflampu.backend.dto;

import lombok.Getter;
import lombok.Setter;
import ru.theflampu.backend.entity.Item;

@Getter
@Setter
public class ItemDTO {
    private final long id;
    private final String name;
    private final String description;
    private final String image;
    private final int price;
    private final Integer salePrice;
    private final int availableCount;

    public ItemDTO(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.image = item.getImage();
        this.price = item.getPrice();
        this.salePrice = item.getSalePrice();
        this.availableCount = item.getAvailableCount();
    }
}
