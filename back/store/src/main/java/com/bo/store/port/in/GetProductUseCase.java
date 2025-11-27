package com.bo.store.port.in;

import com.bo.store.domain.model.Product;

public interface GetProductUseCase {
    Product getById(Long id);
}
