package ru.theflampu.backend.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class SaveItemRequest {
    private Long id;

    @NotBlank(message = "Название не может быть пустым")
    @Size(max = 255, message = "Фамилия слишком длинная")
    private String name;

    @NotBlank(message = "Описание не может быть пустым")
    private String description;

    @Min(value = 1, message = "Цена не может быть меньше 1")
    private int price;

    @Min(value = 1, message = "Цена со скидкой не может быть меньше 1")
    private Integer salePrice;

    @Min(value = 0, message = "Количество не может быть отризательным")
    private int availableCount;

    @NotNull(message = "Категории обязательны. (Могут быть пустыми)")
    private List<Long> categories;
}
