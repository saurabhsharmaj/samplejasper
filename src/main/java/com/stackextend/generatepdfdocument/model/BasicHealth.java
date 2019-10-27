package com.stackextend.generatepdfdocument.model;

import lombok.Getter;
import lombok.Setter;


public class BasicHealth {
	@Getter 
	@Setter 
	private String pulse;
	
	@Getter 
	@Setter 
	private Integer bp;
	@Getter 
	@Setter 
	private String temp;
	@Getter 
	@Setter 
	private String weight;
	
}
