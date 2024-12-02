package com.sparta.msa_exam.order.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.msa_exam.order.core.Order;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDto {

  private Long orderId;

  private List<Long> productIdList;

  private String message;

  public OrderResponseDto(Order order) {
    this.orderId = order.getOrderId();
    this.productIdList = order.getProductIdList();
  }

  public OrderResponseDto(List<Long> productIds, String message) {
    this.productIdList = productIds;
    this.message = message;
  }
}
