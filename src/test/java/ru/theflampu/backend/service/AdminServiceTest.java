package ru.theflampu.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.theflampu.backend.entity.Category;
import ru.theflampu.backend.entity.Item;
import ru.theflampu.backend.request.SaveItemRequest;
import ru.theflampu.backend.service.impl.DefaultAdminService;
import ru.theflampu.backend.service.impl.DefaultCategoryService;
import ru.theflampu.backend.service.impl.DefaultItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
class AdminServiceTest {
    @Autowired
    private DefaultAdminService adminService;
    @MockBean
    private DefaultCategoryService categoryService;
    @MockBean
    private DefaultItemService itemService;

    @Test
    void saveItem() {
        SaveItemRequest request = new SaveItemRequest();
        List<Long> categories = new ArrayList<>();
        categories.add(1L);
        categories.add(2L);
        categories.add(3L);

        request.setCategories(categories);

        Mockito.doReturn(Optional.of(new Category()))
                .when(categoryService)
                .findById(ArgumentMatchers.anyLong());

        adminService.saveItem(request);

        Mockito.verify(categoryService, Mockito.times(categories.size())).findById(ArgumentMatchers.anyLong());
        Mockito.verify(itemService, Mockito.times(1)).save(ArgumentMatchers.any(Item.class));
    }
}