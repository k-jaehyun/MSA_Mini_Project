package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.core.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public ProductResponseDto createProduct(productRequestDto productRequestDto) {

    if (productRepository.findProductByName(productRequestDto.getName()).isPresent()) {
      throw new IllegalArgumentException("이미 존재하는 product 입니다.");
    }

    Product product = new Product(productRequestDto.getName(), productRequestDto.getSupplyPrice());
    productRepository.save(product);

    return new ProductResponseDto(product);
  }


  public List<ProductResponseDto> getProductList() {
    return productRepository.findAll().stream().map(ProductResponseDto::new).toList();
  }
}
