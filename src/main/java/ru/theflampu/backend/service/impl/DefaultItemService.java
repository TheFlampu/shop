package ru.theflampu.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.theflampu.backend.entity.Item;
import ru.theflampu.backend.repository.ItemRepository;
import ru.theflampu.backend.service.ItemService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultItemService implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public Optional<Item> findById(long id) {
        return itemRepository.findById(id);
    }

    @Override
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void delete(Item item) {
        itemRepository.delete(item);
    }
}
