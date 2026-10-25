package com.vlascares.inventario.dto;

import java.time.Instant;

public record CategoryResponse(
    Long id,
    String name,
    String description,
    Instant createdAt,
    Instant updatedAt
) {}
