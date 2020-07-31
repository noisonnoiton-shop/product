package com.skcc.product.event.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProductOutputChannel {
	String ProductOutput = "ProductOutput";
	
	@Output(ProductOutputChannel.ProductOutput)
	MessageChannel getMessageChannel();
}
