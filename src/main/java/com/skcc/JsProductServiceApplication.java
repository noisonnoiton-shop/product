package com.skcc;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class JsProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsProductServiceApplication.class, args);
	}
	
	@Bean 
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	private ApplicationContext applicationContext;
	
	@Value("${mybatis.config-location}")
	String mybatisConfig;

	private static final Logger log = LoggerFactory.getLogger("main");
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);

		sessionFactory.setConfigLocation(applicationContext.getResource("classpath:" + mybatisConfig));
		
		Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*Mapper.xml");
		
		sessionFactory.setMapperLocations(res);
		
		return sessionFactory.getObject();
	}
	
}
