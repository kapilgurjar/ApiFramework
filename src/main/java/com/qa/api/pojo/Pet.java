package com.qa.api.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
	
	private int id;
	private String name;
	private String status;
	private List <String> photoUrls;
	private Category category;
	private List <Tags> tags;
	
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Category{
		private int id;
		private String name;
		
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Tags{
		private int id;
		private String name;
		
	}
	
	
	

}
