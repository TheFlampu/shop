package ru.theflampu.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.theflampu.backend.dto.CategoryDTO;
import ru.theflampu.backend.entity.Category;
import ru.theflampu.backend.entity.Item;
import ru.theflampu.backend.request.SaveItemRequest;
import ru.theflampu.backend.service.AdminService;
import ru.theflampu.backend.util.UploadFileUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultAdminService implements AdminService {
    private final DefaultCategoryService categoryService;
    private final DefaultItemService itemService;
    private final UploadFileUtil uploadFileUtil;

    @Override
    @Transactional
    public void saveMenu(List<CategoryDTO> categoryDTOS, Category parentCategory) {
        for (CategoryDTO categoryDTO : categoryDTOS) {
            Category category;

            if (categoryDTO.getId() == null) {
                category = new Category();
            } else {
                category = categoryService.findById(categoryDTO.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Категория с " + categoryDTO.getId() + " не найдена"));
            }

            category.setId(categoryDTO.getId());
            category.setName(categoryDTO.getName());
            category.setParentCategory(parentCategory);

            categoryService.save(category);

            saveMenu(categoryDTO.getChildCategories(), category);
        }
    }

    @Override
    public void deleteCategory(long categoryId) {
        Category category = categoryService.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Категория не найдена"));

        Category parentCategory = category.getParentCategory();

        category.getChildCategories().forEach(el -> el.setParentCategory(parentCategory));

        category.getItems().forEach(el -> el.getCategories().remove(category));

        categoryService.delete(category);
    }

    @Override
    public void saveItem(SaveItemRequest request) {
        Set<Category> categories = request.getCategories()
                .stream()
                .map(el -> categoryService.findById(el).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Категория с id " + el + " не найдена")))
                .collect(Collectors.toSet());

        Item item = new Item();

        item.setId(request.getId());
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setAvailableCount(request.getAvailableCount());
        item.setCategories(categories);

        itemService.save(item);
    }

    @Override
    public void deleteItem(long itemId) {
        Item item = itemService.findById(itemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

        itemService.delete(item);
    }

    @Override
    public void addImage(MultipartFile image, long itemId) {
        Item item = itemService.findById(itemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Товар не найден"));

        if (image.isEmpty()) {
            item.setImage(null);
        } else {
            try {
                String path = uploadFileUtil.saveFile(image, "images");
                item.setImage(path);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            } catch (IOException ignored) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Не удалось загрузить файл");
            }
        }

        itemService.save(item);
    }
}
