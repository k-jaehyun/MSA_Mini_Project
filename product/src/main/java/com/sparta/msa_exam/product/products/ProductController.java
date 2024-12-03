package com.sparta.msa_exam.product.products;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity createProduct(
      @RequestBody ProductRequestDto productRequestDto) {

    ProductResponseDto productResponseDto = productService.createProduct(
        productRequestDto);

    return ResponseEntity.ok(productResponseDto);
  }

  @GetMapping
  ResponseEntity getProductList(
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "") String keyword,
      @RequestParam(defaultValue = "name") String sortBy,
      @RequestParam(defaultValue = "DESC") Direction direction,
      @RequestParam(defaultValue = "0") Integer page
  ) {

    Page<ProductResponseDto> productResponseDtoPage
        = productService.getProducts(size, keyword, sortBy, direction, page);

    return ResponseEntity.ok(productResponseDtoPage);
  }


  @GetMapping("/{productId}")
  public Boolean checkProductIsPresent(
      @PathVariable Long productId) {

    return productService.checkProductIsPresent(productId);
  }

}
