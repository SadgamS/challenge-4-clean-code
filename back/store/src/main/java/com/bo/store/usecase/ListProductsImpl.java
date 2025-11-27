package com.bo.store.usecase;

import com.bo.store.domain.model.Product;
import com.bo.store.port.in.ListProductsUseCase;
import com.bo.store.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProductsImpl implements ListProductsUseCase {
    private final ProductRepositoryPort productRepository;

    public ListProductsImpl(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listAll() {
        return productRepository.findAll();
    }
}
