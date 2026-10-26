package com.vlascares.inventario.mapper;

import org.springframework.stereotype.Component;

import com.vlascares.inventario.dto.CategoryRequest;
import com.vlascares.inventario.dto.CategoryResponse;
import com.vlascares.inventario.entity.Category;

@Component 
public class CategoryMapper {
    public Category toEntity(CategoryRequest categoryRequest) {
        Category category = new Category();
        updateEntity(category, categoryRequest);
        return category;
    }

    public void updateEntity(Category category, CategoryRequest categoryRequest) {
        category.setName(categoryRequest.name().trim());
        category.setDescription(categoryRequest.description());
    }

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
            category.getId(),
            category.getName(),
            category.getDescription(),
            category.getCreatedAt(),
            category.getUpdatedAt()
        );
    }
}
