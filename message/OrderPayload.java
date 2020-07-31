package com.skcc.order.event.message;

import java.util.List;

import com.skcc.order.domain.OrderAccount;
import com.skcc.order.domain.OrderPayment;
import com.skcc.order.domain.OrderProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderPayload {
	
	private OrderAccount orderAccount;
	private OrderPayment orderPayment;
	private List<OrderProduct> orderProducts;
	
//	public OrderPayload(OrderAccount orderAccount, OrderPayment orderPayment, List<OrderProduct> orderProducts) {
//		super();
//		this.orderAccount = orderAccount;
//		this.orderPayment = orderPayment;
//		this.orderProducts = orderProducts;
//	}
//	
//	public OrderPayload() {}
//
//	public OrderAccount getOrderAccount() {
//		return orderAccount;
//	}
//
//	public void setOrderAccount(OrderAccount orderAccount) {
//		this.orderAccount = orderAccount;
//	}
//
//	public OrderPayment getOrderPayment() {
//		return orderPayment;
//	}
//
//	public void setOrderPayment(OrderPayment orderPayment) {
//		this.orderPayment = orderPayment;
//	}
//
//	public List<OrderProduct> getOrderProducts() {
//		return orderProducts;
//	}
//
//	public void setOrderProducts(List<OrderProduct> orderProducts) {
//		this.orderProducts = orderProducts;
//	}
	
}
