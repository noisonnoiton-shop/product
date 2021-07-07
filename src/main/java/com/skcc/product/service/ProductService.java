package com.skcc.product.service;

import java.time.LocalDateTime;
import java.util.List;

import com.skcc.order.domain.OrderProduct;
import com.skcc.order.event.message.OrderEvent;
import com.skcc.product.domain.Product;
import com.skcc.product.event.message.ProductEvent;
import com.skcc.product.event.message.ProductEventType;
import com.skcc.product.event.message.ProductPayload;
import com.skcc.product.producer.ProductProducer;
import com.skcc.product.publish.ProductPublish;
import com.skcc.product.repository.ProductEventRepository;
import com.skcc.product.repository.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

	private ProductRepository productRepository;
	private ProductEventRepository productEventRepository;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private ProductProducer productProducer;
	
	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

	@Value("${domain.product.name}")
	String domain;
	
	@Autowired
	public ProductService(ProductRepository productRepository, ProductEventRepository productEventRepository) {
		this.productRepository = productRepository;
		this.productEventRepository = productEventRepository;
	}
	
	public List<Product> findByCategoryId(long categoryId){
		// return this.productMapper.findByCategoryId(categoryId);
		return this.productRepository.findProductByCategoryId(categoryId);
	}
	
	public List<Product> findProductOnSale(){
		// return this.productMapper.findProductOnSale();
		return this.productRepository.findProductBySalePercentageGreaterThan(0);
	}
	
	public Product findById(long id) {
		// return this.productMapper.findById(id);
		return this.productRepository.findById(id);
	}
	
	public List<Product> getAllProducts(){
		// return this.productMapper.getAllProducts();
		return this.productRepository.findByActive("active");
	}
	
	public boolean addProductAmountAndCreatePublishProductEvent(OrderEvent orderEvent) {
		boolean result = false;
		List<OrderProduct> orderProducts = orderEvent.getPayload().getProductsInfo();
		String txId = orderEvent.getTxId();
		try {
			this.productService.addProductAmountAndCreatePublishProductAmountAddedEvent(txId, orderProducts);
			result = true;
		} catch(Exception e) {
			try {
				result = false;
				e.printStackTrace();
				this.productService.createPublishProductAmountAddFailedEvent(txId, orderProducts);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean subtractProductAmountAndCreatePublishProductEvent(OrderEvent orderEvent) {
		boolean result = false;
		List<OrderProduct> orderProducts = orderEvent.getPayload().getProductsInfo();
		String txId = orderEvent.getTxId();
		try {
			this.productService.subtractProductAmountAndCreatePublishProductAmountSubtractedEvent(txId, orderProducts);
			result = true;
		} catch(Exception e) {
			try {
				result = false;
				e.printStackTrace();
				this.productService.createPublishProductAmountSubtractFailedEvent(txId, orderProducts);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean setProductinActiveAndCreatePublishProductEvent(String txId, long id) {
		boolean result = false;
		try {
			this.productService.setProductinActiveAndCreatePublishProductSoldOutEvent(txId, id);
			result = true;
		} catch(Exception e) {
			try {
				result = false;
				e.printStackTrace();
				this.productService.createPublishProductSoldOutFailedEvent(txId, id);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}
	
	@Transactional
	public void addProductAmountAndCreatePublishProductAmountAddedEvent(String txId, List<OrderProduct> orderProducts) throws Exception {
		for(OrderProduct orderProduct : orderProducts) {
			this.addProductAmountValidationCheck(txId, orderProduct, ProductEventType.ProductAmountSubtracted.toString());
			this.addProductAmount(orderProduct);
			this.createPublishProductEvent(txId, orderProduct.getId(), ProductEventType.ProductAmountAdded);
		}
	}
	
	@Transactional
	public void createPublishProductAmountAddFailedEvent(String txId, List<OrderProduct> orderProducts) throws Exception{
		for(OrderProduct orderProduct : orderProducts) {
			this.createPublishProductEvent(txId, orderProduct.getId(), ProductEventType.ProductAmountAddFailed);
		}
	}
	
	@Transactional
	public void subtractProductAmountAndCreatePublishProductAmountSubtractedEvent(String txId, List<OrderProduct> orderProducts) throws Exception {
		// ProductEvent 생성,발행 쪼개  
		for(OrderProduct orderProduct : orderProducts) {
			Product product = this.findById(orderProduct.getId());
			this.subtractProductAmount(orderProduct, product);
			this.createPublishProductEvent(txId, orderProduct.getId(), ProductEventType.ProductAmountSubtracted);
			if(product.getAmount() == orderProduct.getQuantity())
				this.setProductinActiveAndCreatePublishProductEvent(txId, orderProduct.getId());
		}
	}
	
	@Transactional
	public void createPublishProductAmountSubtractFailedEvent(String txId, List<OrderProduct> orderProducts) throws Exception{
		// 복수 건을 처리하는 경우 실패시 이벤트 로그는 모든 제품에 대해서 남기고 실패 메세지 발송은 단건만 한다. 
		// 오더에서 txId로 식별이 가능하기 때문. 다른 도메인에서 제품과 일대다, 일대일 매칭에 관계에 따라 해법이 다를 수 있음.
		ProductEvent productEvent = null;
		for(OrderProduct orderProduct : orderProducts) {
			productEvent = this.convertProductToProductEvent(txId, orderProduct.getId(), ProductEventType.ProductAmountSubtractFailed);
			this.createProductEvent(productEvent);
		}
		this.publishProductEvent(productEvent);
		
	}
	
	@Transactional
	public void setProductinActiveAndCreatePublishProductSoldOutEvent(String txId, long id) throws Exception{
		this.setProductinActive(id);
		this.createPublishProductEvent(txId, id, ProductEventType.ProductSoldOut);
	}
	
	@Transactional
	public void createPublishProductSoldOutFailedEvent(String txId, long id) throws Exception{
		this.createPublishProductEvent(txId, id, ProductEventType.ProductSoldOutFailed);
	}
	
	public void subtractProductAmount(OrderProduct orderProduct, Product product) throws Exception{
		this.subtractProductAmountValidationCheck(orderProduct, product);
		// this.productMapper.subtractProductAmount(orderProduct.getId(), orderProduct.getQuantity());
		product.setAmount(product.getAmount() - orderProduct.getQuantity());
		this.productRepository.save(product);
	}
	
	public void addProductAmount(OrderProduct orderProduct) throws Exception{
		// this.productMapper.addProductAmount(orderProduct.getId(), orderProduct.getQuantity());

		// Product product = new Product();
		// product.setId(orderProduct.getId());
		// product.setName(orderProduct.getName());
		// product.setCategoryId(orderProduct.getCategoryId());
		// product.setActive(orderProduct.getActive());
		// product.setAmount(orderProduct.getAmount() + orderProduct.getQuantity());
		// product.setOriginalPrice(orderProduct.getOriginalPrice());
		// product.setSalePercentage(orderProduct.getSalePercentage());
		// product.setSalePrice(orderProduct.getSalePrice());
		// product.setResultPrice(orderProduct.getResultPrice());
		// product.setImg(orderProduct.getImg());
		Product product = productRepository.findById(orderProduct.getId());
		product.setAmount(orderProduct.getAmount() + orderProduct.getQuantity());
		product.setActive("active");

		this.productRepository.save(product);
	} 
	
	public void setProductinActive(long id) throws Exception {
		this.setProductinActiveValidationCheck();
		// this.productMapper.setProductInactive(id);

		Product product = productRepository.findById(id);
		product.setActive("inactive");
		this.productRepository.save(product);
	}
	
	public void createPublishProductEvent(String txId, long id, ProductEventType productEventType) {
		ProductEvent productEvent = this.productService.convertProductToProductEvent(txId, id, productEventType);
		this.createProductEvent(productEvent);
		this.publishProductEvent(productEvent);
	}
	
	public void createProductEvent(ProductEvent productEvent) {
		// this.productMapper.createProductEvent(productEvent);
		this.productEventRepository.save(productEvent);
	}
	
	public void publishProductEvent(ProductEvent productEvent) {
		// this.productPublish.send(productEvent);
		this.productProducer.send(productEvent);
	}
	
	public ProductEvent convertProductToProductEvent(String txId, long id, ProductEventType productEventType) {
		log.info("in service txId : {}", txId);
		
		// Product product = this.productMapper.findById(id);
		Product product = this.productRepository.findById(id);
		product.setCategoryName("Temporary");
		
		ProductEvent productEvent = new ProductEvent();
		// productEvent.setId(this.productMapper.getProductEventId());
		productEvent.setDomain(domain);
		productEvent.setProductId(product.getId());
		productEvent.setEventType(productEventType);
		productEvent.setPayload(new ProductPayload(product.getId(), product.getName()
				, product.getCategoryName(), product.getActive(), product.getOriginalPrice(), product.getSalePercentage()
				, product.getSalePrice(), product.getResultPrice(), product.getAmount(), product.getImg()));
		productEvent.setTxId(txId);
		productEvent.setCreatedAt(LocalDateTime.now());
		
		log.info("in service productEvent : {}", productEvent.toString());
		
		return productEvent;
	}
	
	public void addProductAmountValidationCheck(String txId, OrderProduct orderProduct, String eventType) throws Exception {
		if(orderProduct.getName() == null || "".equals(orderProduct.getName()))
			throw new Exception();
		if(orderProduct.getQuantity() == 0)
			throw new Exception();
		// 앞에 productAmountSubtract가 있어야 처리
		// if(this.productMapper.findProductEvent(orderProduct.getId(), txId, eventType) == null)
		if(this.findProductEvent(orderProduct.getId(), txId, eventType) == null)
			throw new Exception();
	}
	
	public void setProductinActiveValidationCheck() throws Exception {}
	
	public void subtractProductAmountValidationCheck(OrderProduct orderProduct, Product product) throws Exception {
		if(product.getAmount() < orderProduct.getQuantity()) 
			throw new Exception();
	}
	
	public List<ProductEvent> getProductEvent(){
		// return this.productMapper.getProductEvent();
		return this.productEventRepository.findAll();
	}
	
	public ProductEvent findProductEvent(long productId, String txId, String eventType) {
		// return this.productMapper.findProductEvent(id, txId, eventType);
		return this.productEventRepository.findProductEventByProductIdAndTxIdAndEventType(productId, txId, eventType);
	}
	
}
