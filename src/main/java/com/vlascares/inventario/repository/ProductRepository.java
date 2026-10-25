package com.vlascares.inventario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vlascares.inventario.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySku(String sku);
    boolean existsBySkuAndIdNot(String sku, Long id);
    boolean existsByCategoryId(Long categoryId);
    @EntityGraph(attributePaths = {"category", "supplier"})
    Optional<Product> findWithRelationsById(Long id);

    @EntityGraph(attributePaths = {"category", "supplier"})
    @Query("""
            SELECT p FROM Product p
            WHERE p.active = true
              AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))
              AND (:categoryId IS NULL OR p.category.id = :categoryId)
            """)
    Page<Product> search(@Param("name") String name,
                         @Param("categoryId") Long categoryId,
                         Pageable pageable);

    @EntityGraph(attributePaths = {"category", "supplier"})
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.stock <= p.minStock")
    List<Product> findLowStock();
}
