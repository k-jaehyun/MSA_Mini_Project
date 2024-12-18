package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.core.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  @CacheEvict(cacheNames = "productSearchCache", allEntries = true)
  public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {

    if (productRepository.findProductByName(productRequestDto.getName()).isPresent()) {
      throw new IllegalArgumentException("이미 존재하는 product 입니다.");
    }

    Product product = new Product(productRequestDto.getName(), productRequestDto.getSupplyPrice());
    product = productRepository.save(product);

    return new ProductResponseDto(product);
  }

  @Cacheable(cacheNames = "productSearchCache", key = "{ #size, #keyword, #sortBy, #direction, #page }")
  public Page<ProductResponseDto> getProducts(int size, String keyword, String sortBy,
      Direction direction, Integer page) {

    Pageable pageable = PageRequest.of(page, size, direction, sortBy);

    Page<Product> pagedProduct = productRepository.searchProducts(keyword, pageable);

    return pagedProduct.map(ProductResponseDto::new);
  }

  public Boolean checkProductIsPresent(Long productId) {
    return productRepository.findByIdAndReturnBoolean(productId);
  }
}
