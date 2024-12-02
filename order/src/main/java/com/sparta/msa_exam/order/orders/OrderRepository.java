package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.core.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

  default Order findByIdOrElseThrow(Long orderId) {
    return findById(orderId).orElseThrow(
        () -> new IllegalArgumentException("존재하지 않는 orderId 입니다."));
  }

}
