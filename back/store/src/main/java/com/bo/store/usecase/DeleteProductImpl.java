package com.bo.store.usecase;

import com.bo.store.port.in.DeleteProductUseCase;
import com.bo.store.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductImpl implements DeleteProductUseCase {
    private final ProductRepositoryPort productRepository;

    public DeleteProductImpl(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
