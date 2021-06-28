package com.skcc.product.config;

import java.io.IOException;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skcc.product.event.message.ProductPayload;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductPayloadConverter implements AttributeConverter<ProductPayload, String> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(ProductPayload tData) {

    try {
      return objectMapper.writeValueAsString(tData);
    } catch (JsonProcessingException e) {
      log.error("fail to serialize as object into Json : {}", tData, e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public ProductPayload convertToEntityAttribute(String jsonStr) {

    try {
      return objectMapper.readValue(jsonStr, ProductPayload.class);
    } catch (IOException e) {
      log.error("fail to deserialize as Json into Object : {}", jsonStr, e);
      throw new RuntimeException(e);
    }
  }
}
