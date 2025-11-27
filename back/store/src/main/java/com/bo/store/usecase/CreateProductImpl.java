package com.bo.store.usecase;

import com.bo.store.domain.model.Product;
import com.bo.store.port.in.CreateProductUseCase;
import com.bo.store.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreateProductImpl implements CreateProductUseCase {
    private final ProductRepositoryPort productRepository;

    public CreateProductImpl(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(String name, String description, BigDecimal price, Integer stock) {
        Product product = Product.createNew(name, description, price, stock);
        return productRepository.save(product);
    }
}
