package com.bo.store.dto;

import com.bo.store.domain.model.Product;

public class ProductMapper {
    public static ProductDTO toDto(Product p) {
        if (p == null) return null;
        return ProductDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .stock(p.getStock())
                .build();
    }

    public static Product toDomain(ProductDTO dto) {
        if (dto == null) return null;
        // Use factories in domain
        if (dto.getId() == null) {
            return Product.createNew(dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStock());
        } else {
            return Product.of(dto.getId(), dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStock(), null, null);
        }
    }
}
