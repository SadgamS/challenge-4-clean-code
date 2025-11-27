package com.bo.store.port.in;

import com.bo.store.domain.model.Product;

public interface CreateProductUseCase {
    Product create(String name, String description, java.math.BigDecimal price, Integer stock);
}
