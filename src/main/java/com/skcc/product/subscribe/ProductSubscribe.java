package com.skcc.product.subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import com.esotericsoftware.minlog.Log;
import com.skcc.order.event.message.OrderEvent;
import com.skcc.product.event.channel.ProductInputChannel;
import com.skcc.product.service.ProductService;

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
		Log.info(orderEvent.toString());
		this.productService.subtractProductAmountAndCreatePublishProductEvent(orderEvent);
	}
	
	@StreamListener(ProductInputChannel.orderCanceled)
	public void receiveOrderCanceledEvent(OrderEvent orderEvent) {
		this.productService.addProductAmountAndCreatePublishProductEvent(orderEvent);
	}
	
}
