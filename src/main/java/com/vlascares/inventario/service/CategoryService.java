package com.vlascares.inventario.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vlascares.inventario.dto.CategoryRequest;
import com.vlascares.inventario.dto.CategoryResponse;
import com.vlascares.inventario.dto.PageResponse;
import com.vlascares.inventario.entity.Category;
import com.vlascares.inventario.exception.DuplicateResourceException;
import com.vlascares.inventario.exception.ResourceNotFoundException;
import com.vlascares.inventario.mapper.CategoryMapper;
import com.vlascares.inventario.repository.CategoryRepository;
import com.vlascares.inventario.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
@Transactional (readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;


    public PageResponse<CategoryResponse> findAll(Pageable pageable) {
        return PageResponse.from(
            categoryRepository.findAll(pageable).map(categoryMapper::toResponse)
        );
    }

    public CategoryResponse findById(Long id) {
        return categoryMapper.toResponse(getCategory(id));
    }

    @Transactional 
    public CategoryResponse create(CategoryRequest request) {
        String name = request.name().trim();
        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new DuplicateResourceException("Ya existe una categoría con el nombre '%s'".formatted(name));
        }
        return categoryMapper.toResponse(categoryRepository.save(categoryMapper.toEntity(request)));
    }

    @Transactional 
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category existingCategory = getCategory(id);

        String name = request.name().trim();
        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new DuplicateResourceException("Ya existe una categoría con el nombre '%s'".formatted(name));
        }
        categoryMapper.updateEntity(existingCategory, request);
        Category updatedCategory = categoryRepository.saveAndFlush(existingCategory);
        return categoryMapper.toResponse(updatedCategory);
    }

    @Transactional 
    public void delete(Long id) {
        Category category = getCategory(id);
        if (productRepository.existsByCategoryId(id)) {
            throw new DuplicateResourceException("No se puede eliminar la categoría '%s' porque tiene productos asociados"
                .formatted(category.getName()));
        }
        categoryRepository.delete(category);
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));
    }
}
