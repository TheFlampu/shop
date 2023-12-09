package ru.theflampu.backend.service;

import org.springframework.web.multipart.MultipartFile;
import ru.theflampu.backend.dto.CategoryDTO;
import ru.theflampu.backend.entity.Category;
import ru.theflampu.backend.request.SaveItemRequest;

import javax.transaction.Transactional;
import java.util.List;

public interface AdminService {
    @Transactional
    void saveMenu(List<CategoryDTO> categoryDTOS, Category parentCategory);

    void deleteCategory(long categoryId);

    void saveItem(SaveItemRequest request);

    void deleteItem(long itemId);

    void addImage(MultipartFile image, long itemId);
}
