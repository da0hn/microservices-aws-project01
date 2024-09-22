package dev.da0hn.aws.project01.repository;

import dev.da0hn.aws.project01.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByCode(String code);

  default Product findByCodeOrThrow(String code) {
    return this.findByCode(code)
      .orElseThrow(() -> new RuntimeException("Code %s not found".formatted(code)));
  }

}
