package com.skcc.product.repository;

import com.skcc.product.event.message.ProductEvent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductEventRepository extends JpaRepository<ProductEvent, Long>{
  public ProductEvent findProductEventByProductIdAndTxIdAndEventType(long productId, String txId, String eventType);
}
