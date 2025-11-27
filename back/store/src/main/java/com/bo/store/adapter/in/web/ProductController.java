package com.bo.store.adapter.in.web;

import com.bo.store.domain.model.Product;
import com.bo.store.dto.ProductDTO;
import com.bo.store.dto.ProductMapper;
import com.bo.store.port.in.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final CreateProductUseCase createProduct;
    private final ListProductsUseCase listProducts;
    private final GetProductUseCase getProduct;
    private final UpdateProductUseCase updateProduct;
    private final DeleteProductUseCase deleteProduct;

    public ProductController(CreateProductUseCase createProduct, ListProductsUseCase listProducts,
                             GetProductUseCase getProduct, UpdateProductUseCase updateProduct,
                             DeleteProductUseCase deleteProduct) {
        this.createProduct = createProduct;
        this.listProducts = listProducts;
        this.getProduct = getProduct;
        this.updateProduct = updateProduct;
        this.deleteProduct = deleteProduct;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> list() {
        List<Product> products = listProducts.listAll();
        List<ProductDTO> dtos = products.stream().map(ProductMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> get(@PathVariable Long id) {
        Product p = getProduct.getById(id);
        return ResponseEntity.ok(ProductMapper.toDto(p));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO dto) {
        Product created = createProduct.create(dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStock());
        ProductDTO out = ProductMapper.toDto(created);
        return ResponseEntity.created(URI.create("/products/" + out.getId())).body(out);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        Product updated = updateProduct.update(id, dto.getName(), dto.getDescription(), dto.getPrice(), dto.getStock());
        return ResponseEntity.ok(ProductMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteProduct.delete(id);
        return ResponseEntity.noContent().build();
    }
}
