package com.skcc.product.subscribe;

import com.skcc.order.event.message.OrderEvent;
import com.skcc.product.event.channel.ProductInputChannel;
import com.skcc.product.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(ProductInputChannel.class)
public class ProductSubscribe {

	private ProductService productService;
	
	@Autowired
	public ProductSubscribe(ProductService productService) {
		this.productService = productService;
	}
	
	@StreamListener(ProductInputChannel.orderCreated)
	public void receiveOrderCreatedEvent(OrderEvent orderEvent) {
		this.productService.subtractProductAmountAndCreatePublishProductEvent(orderEvent);
	}
	
	@StreamListener(ProductInputChannel.orderCanceled)
	public void receiveOrderCanceledEvent(OrderEvent orderEvent) {
		this.productService.addProductAmountAndCreatePublishProductEvent(orderEvent);
	}
	
}
