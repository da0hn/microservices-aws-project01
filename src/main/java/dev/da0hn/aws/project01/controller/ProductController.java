package dev.da0hn.aws.project01.controller;

import dev.da0hn.aws.project01.entities.Product;
import dev.da0hn.aws.project01.repository.ProductJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Código implementado da maneira mais simples possível não faça isso em casa
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

  private final ProductJpaRepository productJpaRepository;

  @GetMapping
  public List<Product> findAll() {
    return productJpaRepository.findAll();
  }

  @GetMapping("/{productId}")
  public ResponseEntity<Product> findById(@PathVariable Long productId) {
    return this.productJpaRepository.findById(productId)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Product> create(@RequestBody Product product) {

    this.productJpaRepository.save(product);

    return ResponseEntity.ok(product);
  }

  @PutMapping("/{productId}")
  public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable Long productId) {

    if (!productJpaRepository.existsById(productId)) {
      return ResponseEntity.notFound().build();
    }

    product.setId(productId);
    this.productJpaRepository.save(product);

    return ResponseEntity.ok(product);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Product> deleteById(@PathVariable Long productId) {
    final var maybeProduct = this.productJpaRepository.findById(productId);
    if (maybeProduct.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    this.productJpaRepository.delete(maybeProduct.get());
    return ResponseEntity.ok().build();
  }

  @GetMapping("/code/{code}")
  public ResponseEntity<Product> findByCode(@PathVariable String code) {
    final var maybeProduct = this.productJpaRepository.findByCode(code);
    return maybeProduct.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

}
