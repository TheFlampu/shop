package ru.theflampu.backend.response;

import lombok.Getter;
import lombok.Setter;
import ru.theflampu.backend.dto.ItemDTO;
import ru.theflampu.backend.entity.Category;

import java.util.Set;

@Setter
@Getter
public class ItemsResponse {
    private Set<ItemDTO> items;
    private BreadCrumbsDTO breadcrumbs;

    @Getter
    @Setter
    public static class BreadCrumbsDTO {
        private final String name;
        private final Long id;
        private BreadCrumbsDTO childCategory;

        public BreadCrumbsDTO(Category category) {
            this.name = category.getName();
            this.id = category.getId();
        }

        public BreadCrumbsDTO(String name, Long id) {
            this.name = name;
            this.id = id;
        }
    }
}
