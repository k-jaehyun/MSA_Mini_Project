package com.sparta.msa_exam.order.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity createOrder(@RequestBody OrderRequestDto orderRequestDto) {

    OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto);

    return ResponseEntity.ok(orderResponseDto);
  }

}
