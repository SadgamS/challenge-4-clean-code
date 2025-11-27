package com.bo.store.usecase;

import com.bo.store.domain.model.Product;
import com.bo.store.port.in.GetProductUseCase;
import com.bo.store.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class GetProductImpl implements GetProductUseCase {
    private final ProductRepositoryPort productRepository;

    public GetProductImpl(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
