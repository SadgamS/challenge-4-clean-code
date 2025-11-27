package com.bo.store.port.in;

import com.bo.store.domain.model.Product;

import java.math.BigDecimal;

public interface UpdateProductUseCase {
    Product update(Long id, String name, String description, BigDecimal price, Integer stock);
}
