package com.skcc.product.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 255)
	private String name;

	@Column
	private Long categoryId;

	@Column(length = 255)
	private String active;

	@Column
	private Long originalPrice;

	@Column
	private Long salePercentage;

	@Column
	private Long salePrice;

	@Column
	private Long resultPrice;

	@Column
	private Long amount;

	@Column(length = 255)
	private String img;
	
	@Column
	@CreationTimestamp
	private LocalDateTime createdAt;

	private String categoryName;
}
