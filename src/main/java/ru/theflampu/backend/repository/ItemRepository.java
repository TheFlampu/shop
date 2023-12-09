package ru.theflampu.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.theflampu.backend.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
