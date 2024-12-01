package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.core.Order;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

  private List<Long> productIdList;

  public OrderResponseDto(Order order) {
    this.productIdList = order.getProductIdList();
  }
}
