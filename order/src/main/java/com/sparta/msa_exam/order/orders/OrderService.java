package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.core.Order;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final ProductClient productClient;

  public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {

    List<Long> productIdList = orderRequestDto.getProductIds();

    for (Long productId : productIdList) {
      if (!productClient.checkProductIsPresent(productId)) {
        throw new IllegalArgumentException("product_id: " + productId + " 인 상품이 존재하지 않습니다.");
      }
    }

    Order order = new Order(productIdList);
    orderRepository.save(order);

    return new OrderResponseDto(order);
  }
}
