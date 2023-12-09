package ru.theflampu.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.theflampu.backend.dto.CategoryDTO;
import ru.theflampu.backend.dto.ItemDTO;
import ru.theflampu.backend.entity.Category;
import ru.theflampu.backend.entity.Item;
import ru.theflampu.backend.repository.CategoryRepository;
import ru.theflampu.backend.response.ItemsResponse;
import ru.theflampu.backend.service.CategoryService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public ItemsResponse getItems(long categoryId) {
        Stack<Category> stack = new Stack<>();
        ItemsResponse itemsResponse = new ItemsResponse();

        if (categoryId > 0) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена"));
            stack.push(category);

            itemsResponse.setBreadcrumbs(generateBreadcrumbs(category));
        } else {
            List<Category> rootCategories = categoryRepository.findAllByParentCategory(null);

            rootCategories.forEach(stack::push);

            itemsResponse.setBreadcrumbs(generateBreadcrumbs(null));
        }

        Set<Item> items = new HashSet<>();

        while (!stack.isEmpty()) {
            Category category = stack.pop();

            items.addAll(category.getItems());
            category.getChildCategories().forEach(stack::push);
        }

        itemsResponse.setItems(items.stream()
                .map(ItemDTO::new)
                .collect(Collectors.toSet()));

        return itemsResponse;
    }

    private ItemsResponse.BreadCrumbsDTO generateBreadcrumbs(@Nullable Category category) {
        if (category == null) {
            return new ItemsResponse.BreadCrumbsDTO("Главная", null);
        }

        ItemsResponse.BreadCrumbsDTO breadcrumb = generateBreadcrumbs(category.getParentCategory());
        breadcrumb.setChildCategory(new ItemsResponse.BreadCrumbsDTO(category));

        return breadcrumb;
    }

    @Override
    public List<CategoryDTO> getMenu() {
        List<Category> categories = categoryRepository.findAllByParentCategory(null);

        return categories.stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }
}
