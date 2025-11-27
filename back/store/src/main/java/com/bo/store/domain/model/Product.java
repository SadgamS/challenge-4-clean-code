package com.bo.store.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public final class Product {
    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer stock;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Product(Long id, String name, String description, BigDecimal price, Integer stock, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validate();
    }

    // Factory for creation
    public static Product createNew(String name, String description, BigDecimal price, Integer stock) {
        return new Product(null, name, description, price, stock, Instant.now(), null);
    }

    // Factory from persistence (with id and timestamps)
    public static Product of(Long id, String name, String description, BigDecimal price, Integer stock, Instant createdAt, Instant updatedAt) {
        return new Product(id, name, description, price, stock, createdAt, updatedAt);
    }

    // domain behaviours (examples)
    public Product withUpdatedInfo(String name, String description, BigDecimal price, Integer stock) {
        return Product.of(this.id, name, description, price, stock, this.createdAt, Instant.now());
    }

    private void validate() {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name is required");
        if (price == null || price.signum() < 0) throw new IllegalArgumentException("price must be >= 0");
        if (stock == null || stock < 0) throw new IllegalArgumentException("stock must be >= 0");
    }

    // getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
