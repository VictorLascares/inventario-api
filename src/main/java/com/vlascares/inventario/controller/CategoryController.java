package com.vlascares.inventario.controller;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vlascares.inventario.dto.CategoryRequest;
import com.vlascares.inventario.dto.CategoryResponse;
import com.vlascares.inventario.dto.PageResponse;
import com.vlascares.inventario.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/v1/categories")
@RequiredArgsConstructor 
public class CategoryController {
    private final CategoryService categoryService;


    @GetMapping
    public PageResponse<CategoryResponse> findAll(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse created = categoryService.create(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.id())
            .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id,
        @Valid @RequestBody CategoryRequest request) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

}
