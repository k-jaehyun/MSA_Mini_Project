package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.core.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findProductByName(String name);
}
