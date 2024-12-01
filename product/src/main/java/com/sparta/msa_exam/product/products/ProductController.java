package com.sparta.msa_exam.product.products;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity createProduct(
      @RequestBody productRequestDto productRequestDto) {

    ProductResponseDto productResponseDto = productService.createProduct(
        productRequestDto);

    return ResponseEntity.ok(productResponseDto);
  }

}
