package ru.theflampu.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.theflampu.backend.dto.CategoryDTO;
import ru.theflampu.backend.request.SaveItemRequest;
import ru.theflampu.backend.service.AdminService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/saveMenu")
    public void saveMenu(@RequestBody List<CategoryDTO> categoryDTOS) {
        adminService.saveMenu(categoryDTOS, null);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/category/{categoryId}")
    public void deleteCategory(@PathVariable long categoryId) {
        adminService.deleteCategory(categoryId);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/saveItem")
    public void saveItem(@RequestBody @Valid SaveItemRequest request) {
        adminService.saveItem(request);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/item/{itemId}")
    public void deleteItem(@PathVariable long itemId) {
        adminService.deleteItem(itemId);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/addImage/{itemId}")
    public void addImage(@RequestParam MultipartFile image, @PathVariable long itemId) {
        adminService.addImage(image, itemId);
    }
}
