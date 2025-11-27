package com.bo.store.port.in;

import com.bo.store.domain.model.Product;

import java.util.List;

public interface ListProductsUseCase {
    List<Product> listAll();
}
