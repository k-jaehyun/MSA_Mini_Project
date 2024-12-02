package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.core.Order;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final ProductClient productClient;

  @CircuitBreaker(name = "orderService", fallbackMethod = "handleProductCheckFailure")
  public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {

    List<Long> productIdList = orderRequestDto.getProductIds();

    if (productIdList.isEmpty()) {
      throw new IllegalArgumentException("상품 ID 리스트가 비어 있습니다.");
    }

    for (Long productId : productIdList) {
      if (!productClient.checkProductIsPresent(productId)) {
        throw new IllegalArgumentException("product_id: " + productId + " 인 상품이 존재하지 않습니다.");
      }
    }

    Order order = new Order(productIdList);
    orderRepository.save(order);

    return new OrderResponseDto(order);
  }

  public OrderResponseDto handleProductCheckFailure(OrderRequestDto orderRequestDto, Throwable t) {
    return new OrderResponseDto(orderRequestDto.getProductIds(),
        String.format(
            "현재 %s 중에서 %s 잠시 후 주문 추가를 요청해주세요.",
            orderRequestDto.getProductIds(),
            t.getMessage()
        ));
  }

  public OrderResponseDto addProductToOrder(Long orderId, OrderRequestDto orderRequestDto) {

    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 orderId입니다."));

    List<Long> productIdList = orderRequestDto.getProductIds();

    if (productIdList.isEmpty()) {
      throw new IllegalArgumentException("상품 ID 리스트가 비어 있습니다.");
    }

    order.getProductIdList().addAll(productIdList);
    orderRepository.save(order);

    return new OrderResponseDto(order);
  }
}
