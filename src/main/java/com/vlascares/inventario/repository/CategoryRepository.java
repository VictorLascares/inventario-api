package com.vlascares.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlascares.inventario.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}
