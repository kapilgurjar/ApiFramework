package com.qa.livebarn.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVodMedia {
	
	
	private Integer duration;
	private String beginDate ;
	private Integer renditionId ;
	private Integer feedModeId ;
	
	

}




//{}--Create class
//[]--List 