package com.skcc.category.event.message;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.skcc.category.config.CategoryPayloadConverter;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CategoryEvent {

	@Id
	@SequenceGenerator( name = "event_seq_gen", sequenceName = "event_seq", allocationSize = 1 )

	@GeneratedValue(generator="event_seq_gen")
	private Long id;

  @Column(length = 255)
  private String domain;

  @Column
  private Long categoryId;

	@Column
	@Enumerated(EnumType.STRING)
	private CategoryEventType eventType;

	@Column(columnDefinition = "TEXT")
	@Convert(converter = CategoryPayloadConverter.class)
	private CategoryPayload payload;

	@Column(length = 255)
	private String txId;

  @Column
  @CreationTimestamp
  private LocalDateTime createdAt;
}
