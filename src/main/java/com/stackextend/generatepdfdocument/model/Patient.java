package com.stackextend.generatepdfdocument.model;

import lombok.Getter;
import lombok.Setter;

public class Patient {
	@Getter 
	@Setter 
	private String name;
	
	@Getter 
	@Setter 
	private Integer age;
	
	@Getter 
	@Setter 
	private String gender;
	
	@Getter 
	@Setter 
	private String information;
	
	@Getter 
	@Setter 
	private BasicHealth basicHealth;

	
}
