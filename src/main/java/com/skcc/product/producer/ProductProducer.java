package com.skcc.product.producer;

import com.skcc.product.event.message.ProductEvent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductProducer {

  private final StreamBridge streamBridge;

  @Value("${domain.product.name}")
  private String domain;

  public boolean send(ProductEvent productEvent) {
    log.info("routeTo" + domain + "." + productEvent.getEventType());

    return this.streamBridge.send("productOutput", MessageBuilder.withPayload(productEvent)
    .setHeader("routeTo", domain + "." + productEvent.getEventType()).build());
  }
}
