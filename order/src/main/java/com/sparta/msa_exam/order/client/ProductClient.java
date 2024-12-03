package com.sparta.msa_exam.order.client;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {

  @Cacheable(cacheNames = "checkProductIsPresent", key = "args[0]")
  @GetMapping("/products/{productId}")
  Boolean checkProductIsPresent(
      @PathVariable Long productId);

}
