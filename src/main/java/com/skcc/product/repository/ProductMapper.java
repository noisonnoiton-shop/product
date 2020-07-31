package com.skcc.product.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.skcc.product.domain.Product;
import com.skcc.product.event.message.ProductEvent;

@Mapper
public interface ProductMapper {
	
//	@Select("select * from products where categoryId = #{categoryId} and active = 'active'")
	public List<Product> findByCategoryId(long categoryId);
	
//	@Select("select * from products where salePercentage > 0 and active = 'active'")
	public List<Product> findProductOnSale();
	
//	@select("select * from products")
	public List<Product> getAllProducts();
	
//	@Select("select * from products where id = #{id}")
	public Product findById(long id);
	
//	@Update("update products set active = 'inactive' where id = #{id}")
	public long setProductInactive(long id); 
	 
//	@Update("update products set active = 'active', amount = amount + #{quantity} where id = #{id}")
	public long addProductAmount(@Param("id") long id, @Param("quantity") long quantity); 
	
//	@Update("update products set active = #{active}, amount = amount + #{quantity} where id = #{id}")
	public long subtractProductAmount(@Param("id") long id, @Param("quantity") long quantity); 
	
//	@Insert("INSERT INTO PRODUCT_EVENTS(ID, DOMAIN, PRODUCTID, EVENTTYPE, PAYLOAD, TXID, CREATEDAT) VALUES(#{ID}, #{DOMAIN}, #{PRODUCTID}, #{EVENTTYPE}, #{PAYLOAD}, #{TXID}, NOW())")
	public void createProductEvent(ProductEvent productEvent);
	
//	@Select("select get_account_eventid_seq('eventId')")
	public long getProductEventId();
	
//	@select("select * from products where txId = #{txId} and eventType = #{eventType}")
	public ProductEvent findProductEvent(@Param("productId") long productId, @Param("txId") String txId,@Param("eventType") String eventType);
	
//	@select("select * from product_events")
	public List<ProductEvent> getProductEvent();
	
}
