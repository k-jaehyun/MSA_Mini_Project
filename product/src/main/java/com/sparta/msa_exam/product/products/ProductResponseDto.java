package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.core.Product;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long productId;

  private String name;

  private Integer supplyPrice;

  public ProductResponseDto(Product product) {
    this.productId = product.getProductId();
    this.name = product.getName();
    this.supplyPrice = product.getSupplyPrice();
  }
}
