package ru.theflampu.backend.service;

import ru.theflampu.backend.entity.Item;

import java.util.Optional;

public interface ItemService {
    Optional<Item> findById(long id);

    void save(Item item);

    void delete(Item item);
}
