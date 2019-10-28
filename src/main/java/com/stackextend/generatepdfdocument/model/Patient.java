package com.stackextend.generatepdfdocument.model;

import lombok.Getter;
import lombok.Setter;


public class Patient {
	 
	private String name;
	
	private Integer age;
	
	
	private String gender;
	
	private String information;
	
	private BasicHealth basicHealth;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public BasicHealth getBasicHealth() {
		return basicHealth;
	}

	public void setBasicHealth(BasicHealth basicHealth) {
		this.basicHealth = basicHealth;
	}

}
