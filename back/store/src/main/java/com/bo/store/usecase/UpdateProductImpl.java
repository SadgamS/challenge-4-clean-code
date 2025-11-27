package com.bo.store.usecase;

import com.bo.store.domain.model.Product;
import com.bo.store.port.in.UpdateProductUseCase;
import com.bo.store.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UpdateProductImpl implements UpdateProductUseCase {
    private final ProductRepositoryPort productRepository;

    public UpdateProductImpl(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product update(Long id, String name, String description, BigDecimal price, Integer stock) {
        Product existing = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        Product updated = existing.withUpdatedInfo(name, description, price, stock);
        return productRepository.save(updated);
    }
}
