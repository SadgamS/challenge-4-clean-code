package com.bo.store.adapter.out.persistence;

import com.bo.store.adapter.out.persistence.jpa.ProductEntity;
import com.bo.store.adapter.out.persistence.jpa.ProductJpaRepository;
import com.bo.store.domain.model.Product;
import com.bo.store.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductPersistenceAdapter implements ProductRepositoryPort {
    private final ProductJpaRepository jpa;

    public ProductPersistenceAdapter(ProductJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = toEntity(product);
        ProductEntity saved = jpa.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpa.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    private ProductEntity toEntity(Product p) {
        return ProductEntity.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .stock(p.getStock())
                .createdAt(p.getCreatedAt() == null ? java.time.Instant.now() : p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }

    private Product toDomain(ProductEntity e) {
        return Product.of(e.getId(), e.getName(), e.getDescription(), e.getPrice(), e.getStock(), e.getCreatedAt(), e.getUpdatedAt());
    }
}
