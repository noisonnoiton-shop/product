package com.skcc.product.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.skcc.product.event.channel.ProductOutputChannel;
import com.skcc.product.event.message.ProductEvent;

@Component
@EnableBinding(ProductOutputChannel.class)
public class ProductPublish {
	
	private ProductOutputChannel productOutputchannel;
	
	@Value("${domain.product.name}")
	private String domain;
	
	private static final Logger log = LoggerFactory.getLogger(ProductPublish.class);

	@Autowired
	public ProductPublish(ProductOutputChannel productOutputchannel) {
		this.productOutputchannel = productOutputchannel;
	}
	
	public void send(ProductEvent productEvent) {
		log.info(productEvent.toString());
		this.productOutputchannel.getMessageChannel().send(MessageBuilder.withPayload(productEvent).setHeader("routeTo", domain + "." + productEvent.getEventType()).build());
	}
	
}
