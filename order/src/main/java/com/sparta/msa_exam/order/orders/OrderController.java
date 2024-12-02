package com.sparta.msa_exam.order.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    if (orderResponseDto.getMessage() == null) {
      return ResponseEntity.ok(orderResponseDto);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderResponseDto);
    }
  }

  @PatchMapping("/{orderId}")
  public ResponseEntity addProductToOrder(
      @PathVariable Long orderId,
      @RequestBody OrderRequestDto orderRequestDto) {

    OrderResponseDto orderResponseDto = orderService.addProductToOrder(orderId, orderRequestDto);

    return ResponseEntity.ok(orderResponseDto);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity getOrder(@PathVariable Long orderId) {

    OrderResponseDto orderResponseDto = orderService.getOrder(orderId);

    return ResponseEntity.ok(orderResponseDto);
  }


}
