package com.skcc.product.event.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ProductInputChannel {
	
	String orderCreated = "orderCreated";
	String orderCanceled = "orderCanceled";
	
	@Input(ProductInputChannel.orderCreated)
	SubscribableChannel orderCreated();
	
	@Input(ProductInputChannel.orderCanceled)
	SubscribableChannel orderCanceled();
	
}
