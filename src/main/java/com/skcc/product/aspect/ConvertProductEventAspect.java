package com.skcc.product.aspect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.skcc.product.event.message.ProductEventType;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class ConvertProductEventAspect {
	
	private static final Logger log = LoggerFactory.getLogger(ConvertProductEventAspect.class);

	@Pointcut("execution(* com.skcc.*.service.*.convertProductToProductEvent(..))")
	public void convertProductToProductEvent() {}
	
	@Around("convertProductToProductEvent() && args(txId, id, productEventType)")
	public Object setTxId(ProceedingJoinPoint pjp, String txId, long id, ProductEventType productEventType) throws Throwable {
		//request에 의한 호출시 txId == null
		//subsribe에 의한 호출시 txId != null
		if(txId == null) {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

			// zuul prefilter 제거하여 수동 생성
			// txId = attr.getRequest().getHeader("X-TXID");
			UUID uuid = UUID.randomUUID();
			txId = String.format("%s-%s", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), uuid.toString());
		}
		
		return pjp.proceed(new Object[] {txId, id, productEventType});
	}
}
