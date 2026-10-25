package com.vlascares.inventario.repository;

import com.vlascares.inventario.entity.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsByRfc(String rfc);
    boolean existsByRfcAndIdNot(String rfc, Long id);
    Page<Supplier> findByActiveTrue(Pageable pageable);
}
