package com.vlascares.inventario.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@Entity 
@Table (name = "products")
public class Product extends BaseEntity {
    @Column (nullable = false, unique = true, length = 50)
    private String sku;

    @Column (nullable = false, length = 150)
    private String name;

    @Column (columnDefinition = "TEXT")
    private String description;

    @Column (nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column (nullable = false)
    private Integer stock = 0;

    @Column (name = "min_stock", nullable = false)
    private Integer minStock = 0;

    @Column (nullable = false)
    private boolean active = true;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @Column (name = "category_id", nullable = false)
    private Category category;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "supplier_id")
    private Supplier supplier;

    @Version 
    @Setter (AccessLevel.NONE)
    private Long version;

    public boolean isLowStock() {
        return stock <= minStock;
    }
}
