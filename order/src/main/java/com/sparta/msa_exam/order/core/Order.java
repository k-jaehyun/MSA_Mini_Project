package com.sparta.msa_exam.order.core;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_order")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "order_product_ids", joinColumns = @JoinColumn(name = "order_id"))
  private List<Long> productIdList;

  public Order(List<Long> productIdList) {
    this.productIdList = productIdList;
  }
}
