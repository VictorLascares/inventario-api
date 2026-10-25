package com.vlascares.inventario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@Entity 
@Table (name = "suppliers")
public class Supplier extends BaseEntity {
    @Column (nullable = false, length = 150)
    private String name;

    @Column (unique = true, length = 13)
    private String rfc;

    @Column (length = 150)
    private String email;

    @Column (length = 20)
    private String phone;

    @Column (nullable = false)
    private boolean active = true;
}
