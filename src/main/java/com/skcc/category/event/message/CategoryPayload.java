package com.skcc.category.event.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryPayload {
	private long id;
	private String name;
	private long priority;
	private String active;
}
