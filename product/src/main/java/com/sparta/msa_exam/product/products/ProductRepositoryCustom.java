package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.core.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

  Page<Product> searchProducts(String keyword, Pageable pageable);
}
