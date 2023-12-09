package ru.theflampu.backend.service;

import ru.theflampu.backend.dto.CategoryDTO;
import ru.theflampu.backend.entity.Category;
import ru.theflampu.backend.response.ItemsResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    ItemsResponse getItems(long categoryId);

    List<CategoryDTO> getMenu();

    void save(Category category);

    Optional<Category> findById(long id);

    void delete(Category category);
}
