package ru.theflampu.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.theflampu.backend.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private List<CategoryDTO> childCategories;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.childCategories = category.getChildCategories()
                .stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }
}
